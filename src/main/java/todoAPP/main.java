package todoAPP;

import java.sql.Connection;

public class main {

  static DatabaseConnection connection;

  public static void main(String[] args){
    connection = new DatabaseConnection();
    System.out.println("DONE!");
  }
}
