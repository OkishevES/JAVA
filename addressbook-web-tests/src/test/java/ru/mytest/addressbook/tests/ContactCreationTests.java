package ru.mytest.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.mytest.addressbook.model.ContactData;
import ru.mytest.addressbook.model.Contacts;
import ru.mytest.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsCsv() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    //open file for reading
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")))) {
      //BufferedReader allows to read the whole row from file (method readLine)
      String line = reader.readLine();
      while (line != null) {
        String[] split = line.split(";"); //to cut row by ;-separator
        list.add(new Object[]{new ContactData().withFirstName(split[0]).withLastName(split[1]).withNickName(split[2]).withTitle(split[3])
                .withCompany(split[4]).withAddress(split[5]).withHomePhone(split[6]).withMobilePhone(split[7]).withWorkPhone(split[8])
                .withEmail(split[9]).withEmail2(split[10]).withEmail3(split[11])});
        line = reader.readLine();
      }
      return list.iterator(); //Test framework TestNG organizes cycle and give objects from list to test
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsXml() throws IOException {
    //open file for reading
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      //BufferedReader allows to read the whole row from file (method readLine)
      String xml = ""; //create empty row to which we will add all rows from file. It needs for method fromXML (it works with String format)
      String line = reader.readLine();
      while (line != null) {
        xml += line; //add row to xml while get end of file
        line = reader.readLine();
      }
      XStream xStream = new XStream();
      xStream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml); //we set needed type of object to convert result of fromXML method
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
      //list -> stream -> map applies to all elements some function (in this case - converts every element to Object[] type) ->
      // -> collect in list by Collectors -> return iterator (to provide next data for test)
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsJson() throws IOException {
    //open file for reading
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      //BufferedReader allows to read the whole row from file (method readLine)
      String json = ""; //create empty row to which we will add all rows from file. It needs for method fromJson (it works with String format)
      String line = reader.readLine();
      while (line != null) {
        json += line; //add row to xml while get end of file
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());
      //TypeToken<List<GroupData>>(){}.getType() - special construction to get type of data in which we need data from JSON file
      //for other types of data (without <>, usual objects, not lists) we can set GroupData.class, for example (to point needed type)
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
      //list -> stream -> map applies to all elements some function (in this case - converts every element to Object[] type) ->
      // -> collect in list by Collectors -> return iterator (to provide next data for test)
    }
  }

  @BeforeTest
  public void ensurePreconditions() {
    app.group().ensurePreconditions();
  }

  @Test(dataProvider = "validContactsJson")
  public void testContactCreation(ContactData contact) throws Exception {
    File photo = new File("src/test/resources/Avatar1.png");
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    app.contact().create(contact.inGroup(app.db().groups().iterator().next()).withPhoto(photo), true);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();

    int id = after.stream().mapToInt((c) -> c.getId()).max().getAsInt();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(id).withFirstName(app.db().selectContactById(id).getFirstName())
                    .withLastName(app.db().selectContactById(id).getLastName())
                    .withNickName(app.db().selectContactById(id).getNickName())
                    .withTitle(app.db().selectContactById(id).getTitle())
                    .withCompany(app.db().selectContactById(id).getCompany())
                    .withAddress(app.db().selectContactById(id).getAddress())
                    .withHomePhone(app.db().selectContactById(id).getHomePhone())
                    .withMobilePhone(app.db().selectContactById(id).getMobilePhone())
                    .withWorkPhone(app.db().selectContactById(id).getWorkPhone())
                    .withEmail(app.db().selectContactById(id).getEmail())
                    .withEmail2(app.db().selectContactById(id).getEmail2())
                    .withEmail3(app.db().selectContactById(id).getEmail3()))));
    verifyContactListInUI();
    }

    @Test (enabled = false)
    public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/stru.png");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
    }
}
