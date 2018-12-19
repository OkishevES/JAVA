package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@XStreamAlias("group")
@Entity
@Table (name = "group_list")
public class GroupData {
    @XStreamOmitField
    @Id
    @Column (name = "group_id")
    private int grid = Integer.MAX_VALUE;

    @XStreamAlias("name")
    @Expose
    @Column (name = "group_name")
    private String grname;

    @Expose
    @XStreamAlias("header")
    @Column (name = "group_header")
    @Type(type = "text")
    private String grheader;

    @Expose
    @XStreamAlias("footer")
    @Column (name = "group_footer")
    @Type(type = "text")
    private String grfooter;

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
