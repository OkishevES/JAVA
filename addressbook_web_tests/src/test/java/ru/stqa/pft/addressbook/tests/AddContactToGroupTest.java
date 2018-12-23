package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactToGroupTest extends TestBase {

    private ContactData contactToAdd;
    private GroupData groupAddTo;

    @BeforeTest
    public void unsurePreconditions() {
        contactToAdd = new ContactData()
                .withFirstName("contactToAdd")
                .withMobilePhone("123")
                .withEMail("qwe@qwe.su");
        groupAddTo = new GroupData()
                .withName("groupAddTo")
                .withFooter("footer")
                .withHeader("header");

        app.goTo().HomePage();
        app.contact().add(contactToAdd);
        Contacts contacts = app.db().contacts();
        contactToAdd.withId(contacts.stream().mapToInt(o -> o.getId()).max().getAsInt());

        app.goTo().GroupPage();
        app.group().add(groupAddTo);
        Groups groups = app.db().groups();
        groupAddTo.withId(groups.stream().mapToInt(o -> o.getId()).max().getAsInt());
    }

    @Test
    public void testContactAddToGroup() {
        app.goTo().HomePage();
        app.contact().addContactToGroup(contactToAdd, groupAddTo);

        ContactData beforeAfterAdding = contactToAdd.withGroups(contactToAdd.getGroups().withAdded(groupAddTo));
        ContactData after = app.db().findContactById(contactToAdd.getId());

        assertThat(beforeAfterAdding, equalTo(after));
    }
}
