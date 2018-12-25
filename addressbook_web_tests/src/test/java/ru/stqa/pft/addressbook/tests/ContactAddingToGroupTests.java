package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddingToGroupTests extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        Groups groups = app.db().groups();
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        if (app.db().contacts().size() == 0) {
            app.goTo().HomePage();
            app.contact().add(new ContactData()
                    .withFirstName("Leon").withAddress("Paris").withEMail("killer@gmail.com")
                    .withHomePhone("+123456789"));
        }
    }

    @Test
    public void testContactAddingToGroup() {
        ContactData contact = app.db().contacts().iterator().next();
        Groups before = contact.getGroups();
        System.out.println("Contact in group " + before);
        app.goTo().HomePage();
        app.contact().addContactToGroup(app.db().contacts().iterator().next());
        Groups after = app.db().selectContactById(contact.getId()).getGroups();
        System.out.println("Contact in group " + after);
        assertThat(after.size(), equalTo(before.size() + 1));
    }

}
