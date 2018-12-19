package ru.mytest.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.mytest.addressbook.appmanager.ApplicationManager;
import ru.mytest.addressbook.model.ContactData;
import ru.mytest.addressbook.model.Contacts;
import ru.mytest.addressbook.model.GroupData;
import ru.mytest.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(TestBase.class); //organize logs

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }

  @BeforeMethod (alwaysRun = true)
  public void logTestStart(Method m, Object[] p) {
    //Method m will have information about method, which run it current moment, Object[] p - information about parameters
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p)); //Arrays.asList(p) to get information in convenient view
  }

  @AfterMethod (alwaysRun = true)
  public void logTestStop(Method m) {  //Method m will have information about method, which run it current moment
    logger.info("Stop test " + m.getName());
  }

  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroups.stream().map((g) -> new GroupData().withId(g.getGrid()).withName(g.getGrname()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyContactListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().all();

      assertThat(uiContacts, equalTo(app.contact().mergeDbContacts(dbContacts)));
    }
  }
}
