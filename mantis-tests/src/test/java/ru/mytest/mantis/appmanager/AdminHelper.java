package ru.mytest.mantis.appmanager;

import org.openqa.selenium.By;
import ru.mytest.mantis.model.UserData;

public class AdminHelper extends HelperBase {

  protected ApplicationManager app;

  public AdminHelper(ApplicationManager app) {
    super(app);
  }

  public void initChangePassword(UserData user) {
    goToManageUserPage();
    selectUserById(user.getId());
    resetPassword();
  }

  private void resetPassword() {
    click(By.cssSelector("input[value='Reset Password']"));
  }

  private void selectUserById(int id) {
    click(By.cssSelector("a[href='manage_user_edit_page.php?user_id=" + id + "']"));
  }

  private void goToManageUserPage() {
    login("administrator", "root");
    click(By.cssSelector("a[href='/mantisbt-2.18.0/manage_overview_page.php']"));
    click(By.cssSelector("a[href='/mantisbt-2.18.0/manage_user_page.php']"));
  }

  public void logout() {
    click(By.xpath("//*[@id='navbar-container']/div[2]/ul/li[3]/a/i[2]"));
    click(By.xpath("//*[@id='navbar-container']/div[2]/ul/li[3]/ul/li[4]/a"));
  }

  public void login(String user, String password) {
    type(By.name("username"), user);
    click(By.cssSelector("input[type='submit']"));
    type(By.name("password"), password);
    click(By.cssSelector("input[type='submit']"));
  }
}
