package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

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
    public void testContactDeletion() {
        Contacts before = app.db().contacts();

        ContactData contactToDelete = before.iterator().next();

        app.goTo().HomePage();
        app.contact().delete(contactToDelete);
        app.goTo().HomePage();

        assertThat(app.contact().size(), equalTo(before.size() - 1));

        Contacts after = app.db().contacts();

        assertThat(after, equalTo(before.witout(contactToDelete)));
        ;
    }


}
