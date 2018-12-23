package ru.stqa.pft.addressbook.appmamager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {
    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void fillContactForm(boolean creation, ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("mobile"), contactData.getMobilePhone());
        if (creation) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        }
        type(By.name("email"), contactData.getEMail());
        attach(By.name("photo"), contactData.getPhoto());
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void initContactModification(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
    }

    public void submitContactModification() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void submitContactDeletion() {
        wd.switchTo().alert().accept();
    }

    public void retunToHomePage() {
        click(By.linkText("home"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void selectById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void add(ContactData contact) {
        retunToHomePage();
        initContactCreation();
        fillContactForm(true, contact);
        submitContactCreation();
        retunToHomePage();
        contactCache = null;
    }

    public void delete(ContactData contact) {
        selectById(contact.getId());
        deleteContact();
        submitContactDeletion();
        contactCache = null;
    }

    public void modify(ContactData contact, ContactData modifiedData) {
        initContactModification(contact.getId());
        fillContactForm(false, modifiedData);
        submitContactModification();
        contactCache = null;
    }

    public int size() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<ContactData>();

        List<WebElement> elements = wd.findElements(By.cssSelector("tr"));

        for (int i = 0; i < elements.size(); i++) {
            if (i != 0) {
                List<WebElement> tableRow = elements.get(i).findElements(By.cssSelector("td"));
                int id = Integer.parseInt(tableRow.get(0).findElement(By.tagName("input")).getAttribute("value"));
                String name = tableRow.get(2).getText();
                ContactData contact = new ContactData().withId(id).withFirstName(name);
                contacts.add(contact);
            }
        }
        return contacts;
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }

        contactCache = new Contacts();

        List<WebElement> elements = wd.findElements(By.cssSelector("tr"));

        for (int i = 0; i < elements.size(); i++) {
            if (i != 0) {
                List<WebElement> tableRow = elements.get(i).findElements(By.cssSelector("td"));
                int id = Integer.parseInt(tableRow.get(0).findElement(By.tagName("input")).getAttribute("value"));
                String name = tableRow.get(2).getText();
                String address = tableRow.get(3).getText();
                String eMails = tableRow.get(4).getText();
                String phones = tableRow.get(5).getText();
                ContactData contact = new ContactData()
                        .withId(id)
                        .withFirstName(name)
                        .withAddress(address)
                        .withAllEMails(eMails)
                        .withAllPhones(phones);
                contactCache.add(contact);
            }
        }
        return new Contacts(contactCache);
    }

    public ContactData phonesFromEditForm(int id) {
        initContactModification(id);
        ContactData contact = new ContactData()
                .withHomePhone(wd.findElement(By.name("home")).getAttribute("value"))
                .withMobilePhone(wd.findElement(By.name("mobile")).getAttribute("value"))
                .withWorkPhone(wd.findElement(By.name("work")).getAttribute("value"));
        retunToHomePage();
        return contact;
    }

    public ContactData emailsFromEditForm(int id) {
        initContactModification(id);
        ContactData contact = new ContactData()
                .withEMail(wd.findElement(By.name("email")).getAttribute("value"))
                .withEMailTwo(wd.findElement(By.name("email2")).getAttribute("value"))
                .withEMailThree(wd.findElement(By.name("email3")).getAttribute("value"));
        retunToHomePage();
        return contact;
    }

    public ContactData addressFromEditForm(int id) {
        initContactModification(id);
        ContactData contact = new ContactData().withAddress(wd.findElement(By.name("address")).getAttribute("value"));
        retunToHomePage();
        return contact;
    }

    public void addContactToGroup(ContactData contact, GroupData group){
        retunToHomePage();
        selectById(contact.getId());
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName());
        click(By.name("add"));
        retunToHomePage();
    }

    public void removeContactFromGroup(ContactData contact, GroupData group) {
        retunToHomePage();
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(group.getName());
        selectById(contact.getId());
        click(By.name("remove"));
        retunToHomePage();
    }
}
