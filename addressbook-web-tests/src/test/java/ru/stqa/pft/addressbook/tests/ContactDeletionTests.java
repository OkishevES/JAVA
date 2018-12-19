package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstName("Fedor").withLastName("Ivanov").withNickName("Vanilla").withTitle("Dev")
              .withAddress("Ekaterinburg").withHomePhone("+7 919-234-76-35").withWorkPhone("+7 919 234 76 35")
              .withEmail("fedor@gmail.com").withGroup("[none]"), true);
    }
  }

  @Test
  public void testContactDeletion() throws Exception {
    Contacts before = app.db().contacts();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    assertThat(app.contact().count(), equalTo(before.size() - 1));
    Contacts after = app.db().contacts();

    assertThat(after, equalTo(before.without(deletedContact)));
  }

}
