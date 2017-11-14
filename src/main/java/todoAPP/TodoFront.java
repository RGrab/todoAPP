package todoAPP;

import java.util.Arrays;
import java.lang.IllegalStateException;
import org.apache.log4j.Logger;
import java.sql.Connection;

public class TodoFront{

  static DatabaseConnection connection;

  private MenuFactory todoMenu;
  private MenuFactory mainMenu;
  private MenuFactory loginMenu;
  private MenuFactory messageMenu;

  public TodoFront(){

    String[] loginMenuString = {"Login", "Create Login", "Quit"};
    loginMenu = new MenuFactory(loginMenuString);

    String[] todoMenuString = {"List Todo items", "Make Todo items", "Remove Todo Item", "Back"};
    todoMenu = new MenuFactory(todoMenuString);

    String[] mainMenuString = {"Todo Menu","Messages Menu", "Logout"};
    mainMenu = new MenuFactory(mainMenuString);

    String[] messageMenuString = {"See All Messages", "View Unread Messages", "Make Message", "Remove Message", "Back"};
    messageMenu = new MenuFactory(messageMenuString);

    connection = new DatabaseConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/todoAPP?useSSL=false", "user", "password");

    loginMenu.display();
    System.out.println(loginMenu.choose());

    mainMenu.display();
    System.out.println(mainMenu.choose());

    todoMenu.display();
    System.out.println(todoMenu.choose());

    messageMenu.display();
    System.out.println(messageMenu.choose());


  }
}
