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

public class ContactToGroupAdditionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.contact().ensurePreconditions();
  }

  @Test
  public void testContactToGroupAddition() throws Exception {
    app.goTo().homePage();
    app.contact().chooseAllGroup();
    ContactData addedContact = app.db().contacts().iterator().next();
    GroupData chosenGroup = app.db().groups().iterator().next();
    Groups groupsOfContactBefore = addedContact.getGroups();
    if (!app.contact().isContactInAGroup(addedContact, chosenGroup)) {
      app.contact().addToGroup(addedContact.inGroup(chosenGroup));
    }
    System.out.println(chosenGroup);
    Groups groupsOfContactAfter = addedContact.getGroups();
    assertThat(groupsOfContactAfter, equalTo(groupsOfContactBefore.withAdded(chosenGroup)));
  }
}
