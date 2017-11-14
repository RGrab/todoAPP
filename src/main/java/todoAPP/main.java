package todoAPP;

import java.sql.Connection;

public class main {

  static DatabaseConnection connection;

  public static void main(String[] args){

    connection = new DatabaseConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/todoAPP?useSSL=false", "user", "password");


  }
}
