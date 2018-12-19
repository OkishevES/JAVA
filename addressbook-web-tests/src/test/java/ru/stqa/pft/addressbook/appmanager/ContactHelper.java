package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class ContactHelper extends HelperBase {

  public ContactHelper(ApplicationManager app) {
    super(app);
  }

  public void submitContactCreation() {
    click(By.xpath("//input[@value='Enter']"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("nickname"), contactData.getNickName());
    type(By.name("title"), contactData.getTitle());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
    //attach(By.name("photo"), contactData.getPhoto());
    if (creation) {
      if (isThereAGroupInList(contactData)) {
        new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      } else {
        new Select(driver.findElement(By.name("new_group"))).selectByVisibleText("[none]");

      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void gotoNewContact() {
    click(By.linkText("add new"));
  }

  public void selectContactById(int id) {
    driver.findElement(By.xpath("//table[@id='maintable']//input[@value='" + id + "']")).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCache = null;
    app.goTo().homePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    app.acceptNextAlert = true;
    deleteSelectedContacts();
    closeDialogWindow();
    contactCache = null;
    app.goTo().homePage();
  }

  public void initContactModification(int index) {
    driver.findElements(By.xpath("//img[@title='Edit']")).get(index).click();
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstName = driver.findElement(By.name("firstname")).getAttribute("value");
    String lastName = driver.findElement(By.name("lastname")).getAttribute("value");
    String address = driver.findElement(By.name("address")).getText();
    String email = driver.findElement(By.name("email")).getAttribute("value");
    String email2 = driver.findElement(By.name("email2")).getAttribute("value");
    String email3 = driver.findElement(By.name("email3")).getAttribute("value");
    String home = driver.findElement(By.name("home")).getAttribute("value");
    String mobile = driver.findElement(By.name("mobile")).getAttribute("value");
    String work = driver.findElement(By.name("work")).getAttribute("value");
    driver.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName)
            .withAddress(address).withEmail(email).withEmail2(email2).withEmail3(email3)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }

  private void initContactModificationById(int id) {
    driver.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();

  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void closeDialogWindow() {
    assertTrue(closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
    driver.findElement(By.cssSelector("div.msgbox"));
  }

  public void create(ContactData contactData, boolean creation) {
    gotoNewContact();
    fillContactForm(contactData, creation);
    submitContactCreation();
    contactCache = null;
    app.goTo().homePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public boolean isThereAGroupInList(ContactData contactData) {
    try {
      new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public int count() {
    return driver.findElements(By.xpath("//table[@id='maintable']//input[@name='selected[]']")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache); //return copy of contactCache
    }
    contactCache = new Contacts();
    List<WebElement> elements = driver.findElements(By.cssSelector("tr[name='entry']"));
    for (WebElement element : elements) {
      List<WebElement> columns = element.findElements(By.cssSelector("td"));
      String lastName = columns.get(1).getText();
      String firstName = columns.get(2).getText();
      String address = columns.get(3).getText();
      String allEmails = columns.get(4).getText();
      String allPhones = columns.get(5).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contactCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName).withAddress(address)
              .withAllEmails(allEmails).withAllPhones(allPhones));

    }
    return new Contacts(contactCache);
  }

}
