package ru.mytest.mantis.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity//declare that UserData linked to database
@Table(name = "mantis_user_table") //set needed table name
public class UserData {
    @Id //because this parameter is identifier
    @Column(name = "id")
    private int id = Integer.MAX_VALUE;

    @Column (name = "username")
    private String userName;

    @Column (name = "email")
    private String userEmail;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return userEmail;
    }

    public UserData withId(int id) {
        this.id = id;
        return this;
    }

    public UserData withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserData withEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return id == userData.id &&
                Objects.equals(userName, userData.userName) &&
                Objects.equals(userEmail, userData.userEmail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, userEmail);
    }
}
