package ru.mytest.mantis.tests;

import org.testng.annotations.Test;
import ru.mytest.mantis.model.UserData;
import ru.mytest.mantis.model.Users;

import java.sql.*;

public class DbConnectionTest {

  @Test
  public void testDbConnection() {

    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bugtracker?user=root&password=&serverTimezone=UTC");
      Statement st = conn.createStatement(); //create object to get data from database
      ResultSet rs = st.executeQuery("select id, username, email from mantis_user_table"); //result is collection
      Users users = new Users();
      while (rs.next()) {  //method to go on all elements of this collection
        users.add(new UserData().withId(rs.getInt("id")).withUserName(rs.getString("username"))
                .withEmail(rs.getString("email")));
      }
      rs.close();
      st.close();
      conn.close();

      System.out.println(users);

    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
