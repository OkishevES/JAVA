package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ContactEmailTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData().withFirstName("Fedor").withLastName("Ivanov").withNickName("Vanilla").withTitle("Dev")
              .withAddress("Ekaterinburg").withHomePhone("+7 919-234-76-35").withWorkPhone("+7 919 234 76 35")
              .withEmail("fedor@gmail.com").withGroup("Group5"), true);
    }
  }

  @Test
  public void testContactEmails() {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    MatcherAssert.assertThat(contact.getAllEmails(), CoreMatchers.equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3()).stream()
            .filter((s) -> ! s.equals("")).collect(Collectors.joining("\n"));
  }
}
