package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTest extends TestBase {
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
    public void testContactPhones() {
        app.goTo().HomePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().phonesFromEditForm(contact.getId());

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }

    private static String cleaned(String phone) {

        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream()
                .filter((s) -> !s.equals(""))
                .map(ContactPhoneTest::cleaned)
                .collect(Collectors.joining("\n"));
    }
}
