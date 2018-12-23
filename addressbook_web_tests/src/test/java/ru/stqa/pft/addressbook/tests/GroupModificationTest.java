package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTest extends TestBase {

    @BeforeTest
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0)
        {
            app.goTo().GroupPage();
            app.group().add(new GroupData()
                    .withName("testgroup")
                    .withHeader("testheader")
                    .withFooter("testfooter"));
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.db().groups();

        app.goTo().GroupPage();

        GroupData groupToModify = before.iterator().next();

        GroupData modifiedData = new GroupData()
                .withId(groupToModify.getId())
                .withName("newgroup")
                .withHeader("newheader")
                .withFooter("newfooter");

        app.group().modify(groupToModify, modifiedData);

        assertThat(app.group().size(), equalTo(before.size()));

        Groups after = app.db().groups();

        assertThat(after, equalTo(
                after.without(groupToModify)
                        .withAdded(modifiedData.withId(after.stream().mapToInt(o -> o.getId()).max().getAsInt()))));

        verifyGroupListInUi();
    }


}
