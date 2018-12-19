package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstName("Fedor").withLastName("Ivanov").withNickName("Vanilla").withTitle("Dev")
              .withCompany("My company").withAddress("Ekaterinburg").withHomePhone("+7 919-234-76-45").withMobilePhone("+79192347641")
              .withWorkPhone("8 (911) 123 45 67").withEmail("fedor@gmail.com").withGroup("[none]"), true);
    }
  }

  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Fedor_2").withLastName("Petrov")
            .withNickName("Fedora_mod").withTitle("Tester_mod").withCompany("Test company_mod").withAddress("Ekaterinburg_mod")
            .withHomePhone("+7 919-234-76-88").withMobilePhone("+79192347685").withWorkPhone("8 (911) 123 45 99")
            .withEmail("fedor_mod@gmail.com");
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();

    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }


}
