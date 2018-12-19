package ru.mytest.addressbook.tests;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mytest.addressbook.model.ContactData;
import ru.mytest.addressbook.model.Contacts;
import ru.mytest.addressbook.model.GroupData;
import ru.mytest.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactFromGroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.contact().ensurePreconditions();
  }

  @Test
  public void testContactFromGroupDeletion() throws Exception {
    app.goTo().homePage();
    app.contact().chooseAllGroup();
    ContactData deletedContact = app.db().contacts().iterator().next();
    GroupData chosenGroup = app.db().groups().iterator().next();
    app.contact().chooseGroupByName(chosenGroup);
    System.out.println("1. " + chosenGroup);
    Groups groupsOfContactBefore = deletedContact.getGroups();
    if (app.contact().isContactInAGroup(deletedContact, chosenGroup)) {
      app.contact().deleteFromGroup(deletedContact, chosenGroup);
    }
    Groups groupsOfContactAfter = deletedContact.getGroups();
    System.out.println("2. " + groupsOfContactBefore.without(chosenGroup));
    System.out.println("3. " + groupsOfContactAfter);
    assertThat(groupsOfContactAfter, equalTo(groupsOfContactBefore.without(chosenGroup)));
  }
}
