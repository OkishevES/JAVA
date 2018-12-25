package ru.stqa.pft.addressbook.tests;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationWithGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testContactCreationWithGroup() {
    Groups groups = app.db().groups();
    ContactData contact = new ContactData();
    Groups before = contact.getGroups();
    ContactData contactNew = new ContactData()
            .withFirstName("Leon").withAddress("Berlin")
            .withEMail("leon@gmail.com").withHomePhone("+123456789").inGroup(groups.iterator().next());
    app.goTo().HomePage();
    app.contact().add(contactNew);
    Groups after = contactNew.getGroups();
    System.out.println(contactNew.getGroups());
    assertThat(after.size(), equalTo(before.size() + 1));
  }
}