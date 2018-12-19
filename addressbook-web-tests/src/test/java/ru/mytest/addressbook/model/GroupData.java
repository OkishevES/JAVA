package ru.mytest.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("group") //set name for tag, more convenient name
@Entity //declare that GroupData linked to database
@Table (name = "group_list") //set needed table name
public class GroupData {
    @XStreamOmitField //miss this field in xml file
    @Id //because this parameter is identifier
    @Column (name = "group_id")
    private int grid = Integer.MAX_VALUE;

    @XStreamAlias("name") //set name for tag
    @Expose //include this field in json file
    @Column (name = "group_name")
    private String grname;

    @Expose //include this field in json file
    @XStreamAlias("header") //set name for tag
    @Column (name = "group_header")
    @Type(type = "text") //convert type of data to text
    private String grheader;

    @Expose //include this field in json file
    @XStreamAlias("footer") //set name for tag
    @Column (name = "group_footer")
    @Type(type = "text") //convert type of data to text
    private String grfooter;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER) //LAZY is a min info from db, EAGER is max info from db
    private Set<ContactData> contacts = new HashSet<ContactData>();

    public int getGrid() {
        return grid;
    }

    public String getGrname() {
        return grname;
    }

    public String getGrheader() {
        return grheader;
    }

    public String getGrfooter() {
        return grfooter;
    }

    public Contacts getContacts() {
        return new Contacts(contacts);
    }

    public GroupData withId(int grid) {
        this.grid = grid;
        return this;
    }

    public GroupData withName(String grname) {
        this.grname = grname;
        return this;
    }

    public GroupData withHeader(String grheader) {
        this.grheader = grheader;
        return this;
    }

    public GroupData withFooter(String grfooter) {
        this.grfooter = grfooter;
        return this;
    }

    @Override
    public String toString() {
        return "GroupData{" +
                "grid=" + grid +
                ", grname='" + grname + '\'' +
                ", grheader='" + grheader + '\'' +
                ", grfooter='" + grfooter + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupData groupData = (GroupData) o;
        return grid == groupData.grid &&
                Objects.equals(grname, groupData.grname) &&
                Objects.equals(grheader, groupData.grheader) &&
                Objects.equals(grfooter, groupData.grfooter);
    }

    @Override
    public int hashCode() {

        return Objects.hash(grid, grname, grheader, grfooter);
    }
}
