package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

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
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")))) {
      String line = reader.readLine();
      while (line != null) {
        String[] split = line.split(";");
        list.add(new Object[]{new ContactData().withFirstName(split[0]).withLastName(split[1]).withNickName(split[2])
                .withCompany(split[3]).withAddress(split[4]).withMobilePhone(split[5]).withEmail(split[6]).withGroup(split[7])});
        line = reader.readLine();
      }
      return list.iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xStream = new XStream();
      xStream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml);
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());
      return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validContactsJson")
  public void testContactCreation(ContactData contact) throws Exception {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    app.contact().create(contact, true);
    MatcherAssert.assertThat(app.contact().count(), equalTo(before.size() + 1));
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
