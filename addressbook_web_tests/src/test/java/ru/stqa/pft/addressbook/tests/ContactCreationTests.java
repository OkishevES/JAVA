package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        File photo = new File("src/test/resources/photo.png");

        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json = json + line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
            }.getType());
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @BeforeTest
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().add(new GroupData()
                    .withName("testgroup")
                    .withHeader("testheader")
                    .withFooter("testfooter"));
        }
    }

    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) {
        app.goTo().HomePage();

        Groups groups = app.db().groups();

        Contacts before = app.db().contacts();

        contact.inGroup(groups.iterator().next());

        app.contact().add(contact);
        app.goTo().HomePage();

        assertThat(app.contact().size(), equalTo(before.size() + 1));

        Contacts after = app.db().contacts();

        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt(o -> o.getId()).max().getAsInt()))));
    }

    @Test(enabled = false)
    public void currentDirectory() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/photo.png");
        System.out.println(photo.exists());
    }
}
