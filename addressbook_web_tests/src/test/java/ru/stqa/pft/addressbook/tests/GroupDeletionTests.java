package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {
    @BeforeTest
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().add(new GroupData()
                    .withName("testgroup")
                    .withHeader("testheader")
                    .withFooter("testfooter"));
        }
    }

    @Test
    public void testGroupDeletion() throws Exception {
        Groups before = app.db().groups();

        app.goTo().GroupPage();

        GroupData groupToDelete = before.iterator().next();

        app.group().delete(groupToDelete);

        assertThat(app.group().size(), equalTo(before.size() - 1));

        Groups after = app.db().groups();

        assertThat(after, equalTo(after.without(groupToDelete)));

        verifyGroupListInUi();
    }
}
