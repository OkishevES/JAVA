package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeTest
    public void unsurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().HomePage();
            app.contact().add(new ContactData()
                    .withFirstName("firstName")
                    .withMobilePhone("123456789"));
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.db().contacts();

        app.goTo().HomePage();

        ContactData contactToModify = before.iterator().next();

        ContactData modifiedData = new ContactData()
                .withId(contactToModify.getId())
                .withFirstName("newFirstName")
                .withMobilePhone("321")
                .withEMail("new@qwe.su");

        app.contact().modify(contactToModify, modifiedData);
        app.goTo().HomePage();

        assertThat(app.contact().size(), equalTo(before.size()));

        Contacts after = app.db().contacts();

        assertThat(after, equalTo(before.witout(contactToModify).withAdded(modifiedData)));
    }


}
