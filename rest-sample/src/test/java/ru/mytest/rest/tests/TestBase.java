package ru.mytest.rest.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import ru.mytest.rest.appmanager.ApplicationManager;

public class TestBase {

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

  public boolean isIssueOpen(int issueId) {
    String status = app.rest().getStatusIssue(issueId);
    if (status.equals("not fixed")) {
      return true;
    } else {
      return false;
    }
  }

  public void skipIfNotFixed(int issueId) {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

}
