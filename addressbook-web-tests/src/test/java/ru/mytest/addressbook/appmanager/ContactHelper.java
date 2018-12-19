package ru.mytest.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.mytest.addressbook.model.*;
import ru.mytest.addressbook.tests.ContactPhoneTests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    attach(By.name("photo"), contactData.getPhoto());
    if (creation) {
      //if (isThereAGroupInList(contactData)) {
      if (contactData.getGroups().size() > 0) {
        //verify that we have only one group in input data of contact for creation
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getGrname());
      } else {
        new Select(driver.findElement(By.name("new_group"))).selectByVisibleText("[none]");
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void ensurePreconditions() {
    File photo = new File("src/test/resources/stru.png");
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.group().ensurePreconditions();
      app.contact().create(new ContactData().withFirstName("Fedor").withLastName("Ivanov").withNickName("Vanilla").withTitle("Dev")
              .withCompany("My company").withAddress("Ekaterinburg").withHomePhone("+7 919-234-76-45").withMobilePhone("+79192347641")
              .withWorkPhone("8 (911) 123 45 67").withEmail("fedor@gmail.com").withEmail2("fedor2@gmail.com")
              .withEmail3("fedor3@gmail.com").inGroup(app.db().groups().iterator().next()).withPhoto(photo), true);
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
    String nickName = driver.findElement(By.name("nickname")).getAttribute("value");
    String title = driver.findElement(By.name("title")).getAttribute("value");
    String company = driver.findElement(By.name("company")).getAttribute("value");
    String address = driver.findElement(By.name("address")).getText();
    String email = driver.findElement(By.name("email")).getAttribute("value");
    String email2 = driver.findElement(By.name("email2")).getAttribute("value");
    String email3 = driver.findElement(By.name("email3")).getAttribute("value");
    String home = driver.findElement(By.name("home")).getAttribute("value");
    String mobile = driver.findElement(By.name("mobile")).getAttribute("value");
    String work = driver.findElement(By.name("work")).getAttribute("value");
    driver.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName)
            .withNickName(nickName).withTitle(title).withCompany(company).withAddress(address)
            .withEmail(email).withEmail2(email2).withEmail3(email3)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }

  private void initContactModificationById(int id) {
    driver.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    // other methods to find this element "pencil":
    // 1--
    // WebElement checkbox = driver.findElement(By.cssSelector(String.format("input[value='%s']", id))); //find checkbox
    // WebElement row = checkbox.findElement(By.xpath("./../..")); //goto up to parent row
    // List<WebElement> cells = row.findElements(By.tagName("td")); //read all cells in row
    // cells.get(7).findElement(By.tagName("a")).click(); //click on link
    // 2--
    //driver.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a", id))).click();
    // 3--
    //driver.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id))).click();
    // 4--
    //driver.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
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
      new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getGrname());
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
      //System.out.println("UI data: " + id + ", " + firstName + ", " + lastName + ", " + address +", " + allEmails +", " + allPhones);
    }
    return new Contacts(contactCache);
  }

  public String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone()).stream()
            .filter((s) -> !s.equals(""))
            .map(ContactPhoneTests::cleaned)      //map apply cleaned function to all data
            .collect(Collectors.joining("\n"));
    // list -> stream -> filter off null rows -> map for cleaning -> collect by Collector (will join rows with /n delimiter)
  }

  public String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3()).stream()
            .filter((s) -> !s.equals("")).collect(Collectors.joining("\n"));
  }

  Contacts mergeDbContacts = null;

  public Contacts mergeDbContacts(Contacts contacts) {
    mergeDbContacts = new Contacts();
    for (ContactData contact : contacts) {
      mergeDbContacts.add(new ContactData().withId(contact.getId()).withFirstName(contact.getFirstName())
              .withLastName(contact.getLastName()).withAddress(contact.getAddress())
              .withAllPhones(app.contact().mergePhones(contact)).withAllEmails(app.contact().mergeEmails(contact)));
      //System.out.println("DB data: " + contact.getId() + ", " + contact.getFirstName() + ", " + contact.getLastName() + ", "
      //        + contact.getAddress() + ", " + app.contact().mergeEmails(contact) + ", " + app.contact().mergePhones(contact));
    }
    return new Contacts(mergeDbContacts);
  }

  public void addToGroup(ContactData contact) {
    selectContactById(contact.getId());
    new Select(driver.findElement(By.name("to_group"))).selectByVisibleText(contact.getGroups().iterator().next().getGrname());
    click(By.xpath("//input[@value='Add to']"));
    driver.findElement(By.cssSelector("a[href='./?group=" + contact.getGroups().iterator().next().getGrid() + "']")).click();
  }

  public void deleteFromGroup(ContactData contact, GroupData group) {
    chooseGroupByName(group);
    selectContactById(contact.getId());
    driver.findElement(By.name("remove")).click();
  }

  public boolean isContactInAGroup(ContactData contact, GroupData group) {
    boolean isHave = true;
    try {
      Contacts contactsInAGroup = group.getContacts();
    } catch (NoSuchElementException e) {
      isHave = false;
    }
    if (isHave) {
      Contacts contactsInAGroup = group.getContacts();
      if (contactsInAGroup.contains(contact)) {
        isHave = true;
      } else {
        isHave = false;
      }
    }
    System.out.println(isHave);
    return isHave;
  }

  public void chooseAllGroup() {
    new Select(driver.findElement(By.name("group"))).selectByVisibleText("[all]");
  }

  public void chooseGroupByName(GroupData group) {
    new Select(driver.findElement(By.name("group"))).selectByVisibleText(group.getGrname());
  }

}

