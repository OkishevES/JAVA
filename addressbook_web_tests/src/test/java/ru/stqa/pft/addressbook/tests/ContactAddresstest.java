package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddresstest extends TestBase {

    @BeforeTest
    public void unsurePreconditions() {
        app.goTo().HomePage();

        if (!app.contact().isThereAContact()) {
            app.contact().add(new ContactData()
                    .withFirstName("firstName")
                    .withMobilePhone("123456789"));
        }
    }

    @Test
    public void testContactAddress() {
        app.goTo().HomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData addressInfoFromEditForm = app.contact().addressFromEditForm(contact.getId());

        assertThat(contact.getAddress(), equalTo(addressInfoFromEditForm.getAddress()));
    }

}
