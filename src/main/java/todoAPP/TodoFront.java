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

    connection = new DatabaseConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/todoAPP?useSSL=false", "user", "password");

    String[] loginMenuString = {"Login", "Create Login", "Quit"};
    loginMenu = new MenuFactory(loginMenuString);

    String[] mainMenuString = {"Todo Menu","Messages Menu", "Logout"};
    mainMenu = new MenuFactory(mainMenuString);

    String[] todoMenuString = {"List Todo items", "Make Todo items", "Remove Todo Item", "Back"};
    todoMenu = new MenuFactory(todoMenuString);

    String[] messageMenuString = {"See All Messages", "View Unread Messages", "Make Message", "Remove Message", "Back"};
    messageMenu = new MenuFactory(messageMenuString);

    login();

  }

  public void login(){

    loginMenu.display();

    switch(loginMenu.choose()){
      case "Login" :
        mainMenu();
        break;
      case "Create Login" :
        mainMenu();
        break;
      case "Quit" :
        break;
      default :
        System.out.println("Not an option : Try Again");
        break;
    }
  }

  public void mainMenu(){

    mainMenu.display();

    switch(mainMenu.choose()){
      case "Todo Menu" :
        todo();
        break;
      case "Messages Menu" :
        message();
        break;
      case "Logout" :
        login();
        break;
      default :
        System.out.println("Not an option : Try Again");
        break;

    }
  }

  public void todo(){

    todoMenu.display();

    switch(todoMenu.choose()){
      case "List Todo items" :
        todo();
        break;
      case "Make Todo items" :
        todo();
        break;
      case "Remove Todo Item" :
        todo();
        break;
      case "Back" :
        mainMenu();
        break;
      default :
        System.out.println("Not an option : Try Again");
        break;

    }
  }

  public void message(){

    messageMenu.display();

    switch(messageMenu.choose()){
      case "See All Messages" :
        message();
        break;
      case "View Unread Messages" :
        message();
        break;
      case "Make Message" :
        message();
        break;
      case "Remove Message" :
        message();
        break;
      case "Back" :
        mainMenu();
        break;
      default :
        System.out.println("Not an option : Try Again");
        break;

    }
  }

}
