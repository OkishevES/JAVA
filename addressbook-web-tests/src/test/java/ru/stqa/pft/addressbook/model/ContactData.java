package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.Objects;

@XStreamAlias("contact")
@Entity
@Table(name = "addressbook")
public class ContactData {
    @XStreamOmitField
    @Id
    @Column (name = "id")
    private int id = Integer.MAX_VALUE;

    @Expose
    @Column (name = "firstname")
    private String contactFirstName;

    @Expose
    @Column (name = "lastname")
    private String contactLastName;

    @Expose
    @Column (name = "nickname")
    private String contactNickName;

    @Column (name = "title")
    private String contactTitle;

    @Expose
    @Column (name = "company")
    private String contactCompany;

    @Expose
    @Column (name = "address")
    @Type(type = "text")
    private String contactAddress;

    @Column (name = "home")
    @Type(type = "text")
    private String contactHomePhone;

    @Expose
    @Column (name = "mobile")
    @Type(type = "text")
    private String contactMobilePhone;

    @Column (name = "work")
    @Type(type = "text")
    private String contactWorkPhone;

    @Transient
    private String allPhones;

    @Expose
    @Column (name = "email")
    @Type(type = "text")
    private String contactEmail;

    @Column (name = "email2")
    @Type(type = "text")
    private String contactEmail2;

    @Column (name = "email3")
    @Type(type = "text")
    private String contactEmail3;

    @Transient
    private String allEmails;

    @Expose
    @Transient
    private String group;

    @Column (name = "photo")
    @Type(type = "text")
    private String photo;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return contactFirstName;
    }

    public String getLastName() {
        return contactLastName;
    }

    public String getNickName() {
        return contactNickName;
    }

    public String getTitle() {
        return contactTitle;
    }

    public String getCompany() {
        return contactCompany;
    }

    public String getAddress() {
        return contactAddress;
    }

    public String getHomePhone() {
        return contactHomePhone;
    }

    public String getMobilePhone() {
        return contactMobilePhone;
    }

    public String getWorkPhone() {
        return contactWorkPhone;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public String getEmail() {
        return contactEmail;
    }

    public String getEmail2() {
        return contactEmail2;
    }

    public String getEmail3() {
        return contactEmail3;
    }

    public String getAllEmails() {
        return allEmails;
    }

    public String getGroup() {
        return group;
    }

    public File getPhoto() {
        return new File(photo);
    }

    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public ContactData withFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
        return this;
    }

    public ContactData withLastName(String contactLastName) {
        this.contactLastName = contactLastName;
        return this;
    }

    public ContactData withNickName(String contactNickName) {
        this.contactNickName = contactNickName;
        return this;
    }

    public ContactData withTitle(String contactTitle) {
        this.contactTitle = contactTitle;
        return this;
    }

    public ContactData withCompany(String contactCompany) {
        this.contactCompany = contactCompany;
        return this;
    }

    public ContactData withAddress(String contactAddress) {
        this.contactAddress = contactAddress;
        return this;
    }

    public ContactData withHomePhone(String contactHomePhone) {
        this.contactHomePhone = contactHomePhone;
        return this;
    }

    public ContactData withMobilePhone(String contactMobilePhone) {
        this.contactMobilePhone = contactMobilePhone;
        return this;
    }

    public ContactData withWorkPhone(String contactWorkPhone) {
        this.contactWorkPhone = contactWorkPhone;
        return this;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public ContactData withEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public ContactData withEmail2(String contactEmail2) {
        this.contactEmail2 = contactEmail2;
        return this;
    }

    public ContactData withEmail3(String contactEmail3) {
        this.contactEmail3 = contactEmail3;
        return this;
    }

    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public ContactData withGroup(String group) {
        this.group = group;
        return this;
    }

    public ContactData withPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", contactFirstName='" + contactFirstName + '\'' +
                ", contactLastName='" + contactLastName + '\'' +
                ", contactNickName='" + contactNickName + '\'' +
                ", contactTitle='" + contactTitle + '\'' +
                ", contactCompany='" + contactCompany + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", contactHomePhone='" + contactHomePhone + '\'' +
                ", contactMobilePhone='" + contactMobilePhone + '\'' +
                ", contactWorkPhone='" + contactWorkPhone + '\'' +
                ", allPhones='" + allPhones + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactEmail2='" + contactEmail2 + '\'' +
                ", contactEmail3='" + contactEmail3 + '\'' +
                ", allEmails='" + allEmails + '\'' +
                ", group='" + group + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return id == that.id &&
                Objects.equals(contactFirstName, that.contactFirstName) &&
                Objects.equals(contactLastName, that.contactLastName) &&
                Objects.equals(contactNickName, that.contactNickName) &&
                Objects.equals(contactTitle, that.contactTitle) &&
                Objects.equals(contactCompany, that.contactCompany) &&
                Objects.equals(contactAddress, that.contactAddress) &&
                Objects.equals(contactHomePhone, that.contactHomePhone) &&
                Objects.equals(contactMobilePhone, that.contactMobilePhone) &&
                Objects.equals(contactWorkPhone, that.contactWorkPhone) &&
                Objects.equals(allPhones, that.allPhones) &&
                Objects.equals(contactEmail, that.contactEmail) &&
                Objects.equals(contactEmail2, that.contactEmail2) &&
                Objects.equals(contactEmail3, that.contactEmail3) &&
                Objects.equals(allEmails, that.allEmails);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, contactFirstName, contactLastName, contactNickName, contactTitle, contactCompany, contactAddress, contactHomePhone, contactMobilePhone, contactWorkPhone, allPhones, contactEmail, contactEmail2, contactEmail3, allEmails);
    }
}
