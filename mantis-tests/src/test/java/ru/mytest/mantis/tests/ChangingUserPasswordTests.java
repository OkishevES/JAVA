package ru.mytest.mantis.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.mytest.mantis.appmanager.HttpSession;
import ru.mytest.mantis.model.MailMessage;
import ru.mytest.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangingUserPasswordTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testChangingUserPassword() throws IOException, MessagingException {
    UserData changedUser = app.db().users().iterator().next();
    System.out.println(changedUser);
    String new_password = "new_password";
    System.out.println("Size = " + app.db().users().size());
    if (app.db().users().size() > 1) {
      if (!changedUser.getUserName().equals("administrator")) {
        app.admin().initChangePassword(changedUser);
        app.admin().logout();
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 30000);
        String confirmationLink = app.registration().findConfirmationLink(mailMessages, changedUser.getEmail());
        app.registration().finish(confirmationLink, new_password);

        //app.admin().login(changedUser.getUserName(), new_password);

        HttpSession session = app.newSession();
        assertTrue(session.login(changedUser.getUserName(), new_password));
        assertTrue(session.isLoggedInAs(changedUser.getUserName()));
      }
    }
  }

}
