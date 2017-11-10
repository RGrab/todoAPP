package todoAPP;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

  private static Connection connection;

  public DatabaseConnection(){
    try{
      String driver = "com.mysql.jdbc.Driver";
      String url = "jdbc:mysql://localhost:3306/todoAPP?useSSL=false";
      String username = "user";
      String password = "password";
      Class.forName(driver);

      connection = DriverManager.getConnection(url, username, password);
      System.out.println("..Connected..");
    } catch(Exception e){
      System.out.println(e);
    }
  }

  public Connection getConnection(){
    return connection;
  }
}
