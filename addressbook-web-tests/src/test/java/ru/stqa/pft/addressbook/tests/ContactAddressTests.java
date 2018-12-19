package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

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
  public void testContactAddresses() {
    ContactData contact = app.contact().all().iterator().next(); //contact is chosen by random method
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
  }

}
