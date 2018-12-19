package ru.mytest.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mytest.addressbook.model.ContactData;
import ru.mytest.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.contact().ensurePreconditions();
  }

  @Test
  public void testContactModification() {
    File photo = new File("src/test/resources/Avatar2.png");
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next(); //contact is chosen by random method
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Fedor_2").withLastName("Petrov")
            .withNickName("Fedora_mod").withTitle("Tester_mod").withCompany("Test company_mod").withAddress("Ekaterinburg_mod")
            .withHomePhone("+7 919-234-76-88").withMobilePhone("+79192347685").withWorkPhone("8 (911) 123 45 99")
            .withEmail("fedor_mod@gmail.com").withEmail2("fedor_mod2@gmail.com").withEmail3("fedor_mod3@gmail.com").withPhoto(photo);
    app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();

    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();
  }
}
