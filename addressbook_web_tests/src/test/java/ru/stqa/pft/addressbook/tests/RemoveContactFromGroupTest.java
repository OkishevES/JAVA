package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroupTest extends TestBase{

    private ContactData contactToRemove;
    private GroupData groupRemoveFrom;

    @BeforeTest
    public void unsurePreconditions() {
        contactToRemove = new ContactData()
                .withFirstName("contactToAdd")
                .withMobilePhone("123")
                .withEMail("qwe@qwe.su");
        groupRemoveFrom = new GroupData()
                .withName("groupAddTo")
                .withFooter("footer")
                .withHeader("header");

        app.goTo().HomePage();
        app.contact().add(contactToRemove);
        Contacts contacts = app.db().contacts();
        contactToRemove.withId(contacts.stream().mapToInt(o -> o.getId()).max().getAsInt());

        app.goTo().GroupPage();
        app.group().add(groupRemoveFrom);
        Groups groups = app.db().groups();
        groupRemoveFrom.withId(groups.stream().mapToInt(o -> o.getId()).max().getAsInt());

        app.goTo().HomePage();
        app.contact().addContactToGroup(contactToRemove, groupRemoveFrom);
    }

    @Test
    public void removeContactFromGrouTest(){
        app.goTo().HomePage();
        app.contact().removeContactFromGroup(contactToRemove, groupRemoveFrom);

        ContactData beforeAfterAdding = contactToRemove.withGroups(contactToRemove.getGroups().without(groupRemoveFrom));
        ContactData after = app.db().findContactById(contactToRemove.getId());

        assertThat(beforeAfterAdding, equalTo(after));
    }
}
