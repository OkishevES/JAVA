package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTest extends TestBase {

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
    public void testContactEmails() {
        app.goTo().HomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData emailsInfoFromEditForm = app.contact().emailsFromEditForm(contact.getId());

        assertThat(contact.getAllEMails(), equalTo(mergeEmails(emailsInfoFromEditForm)));
    }


    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEMail(), contact.getEMailTwo(), contact.getEMailThree())
                .stream()
                .filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }
}