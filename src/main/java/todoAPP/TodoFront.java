package todoAPP;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.*;
import org.apache.log4j.Logger;
import java.util.Arrays;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.IllegalStateException;
import java.security.SecureRandom;

public class TodoFront{

  Scanner keyboard = new Scanner(System.in);

  final static Logger logger = Logger.getLogger(TodoFront.class);

  static DatabaseConnection connection;

  private String currentUser = null;

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

    String[] todoMenuString = {"List Todo items", "List Todo items (not complete only)", "Make Todo items", "Remove Todo Item", "Back"};
    todoMenu = new MenuFactory(todoMenuString);

    String[] messageMenuString = {"See All Messages", "View Unread Messages", "Make Message", "Remove Message", "Back"};
    messageMenu = new MenuFactory(messageMenuString);

    login();

  }

  private void login(){

    loginMenu.display();

    switch(loginMenu.choose()){

      case "Login" :

        try{

          currentUser = loginDB();

          if(this.currentUser != null){
            mainMenu();
          }else{

            System.out.println("not logged in try again.");
            login();

          }
        }catch (SQLException e){

          System.out.println("Error while logging in.");
          logger.error("TodoFront.login", e);
          login();

        }

      break;

      case "Create Login" :

        try{

          crateUserDB();

          if(this.currentUser != null){
            mainMenu();
          }else{

            System.out.println("not logged in try again.");
            login();

          }

        }catch (SQLException e){

          System.out.println("Error while creating new user.");
          logger.error("TodoFront.login", e);
          login();

        }

      break;

      case "Quit" :
        //by do nothing program exits.
      break;

      default :
        System.out.println("Not an option : Try Again");
      break;
    }
  }

  private void mainMenu(){

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

  private void todo(){

    todoMenu.display();

    switch(todoMenu.choose()){

      case "List Todo items" :

        try{

          displayTodoDB(false);

        }catch (SQLException e){

          System.out.println("Error while displaying todo.");
          logger.error("TodoFront.todo", e);

        }finally{
          todo();
        }

      break;

      case "List Todo items (not complete only)" :

        try{
          displayTodoDB(true);
        }catch (SQLException e){

          System.out.println("Error while displaying todo.");
          logger.error("TodoFront.todo", e);

        }finally{
          todo();
        }

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

  private void message(){

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

  private String loginDB() throws SQLException{

    System.out.println("Enter user name");
    String userName = keyboard.nextLine();

    System.out.println("Enter password");
    String passwordPlainTxt = keyboard.nextLine();

    PreparedStatement loginPS = this.connection.getConnection().prepareStatement("SELECT userName, passwordHash " +
      " FROM user " +
      " WHERE userName = ?");

    loginPS.setString(1, userName);
    ResultSet result = loginPS.executeQuery();

    //checkse that both the user exists and the password matches the stored passwordHash.
    if(result.next() && BCrypt.checkpw(passwordPlainTxt, result.getString("passwordHash"))){
      System.out.println("logged in as " + userName);
      return userName;
    }
    else{
      System.out.println("invalid username or password!");
      return null;
    }
  }

  private void crateUserDB() throws SQLException{

    System.out.println("Enter user name");
    String userName = keyboard.nextLine();

    System.out.println("Enter password");
    String passwordPlainTxt = keyboard.nextLine();

    System.out.println("Enter first name");
    String firstName = keyboard.nextLine();

    System.out.println("Enter lastname");
    String lastName = keyboard.nextLine();

    PreparedStatement createUserPS = connection.getConnection().prepareStatement("INSERT INTO user(firstName,lastName,userName,passwordHash)" +
      "VALUES(?,?,?,?)");

    createUserPS.setString(1, userName);
    createUserPS.setString(2, lastName);
    createUserPS.setString(3, firstName);
    createUserPS.setString(4, BCrypt.hashpw(passwordPlainTxt, BCrypt.gensalt(13)));

    createUserPS.executeUpdate();

    this.currentUser = userName;
  }

  private void displayTodoDB(Boolean status)throws SQLException{

    String todoListString = "SELECT todo.status, todo.priority, todo.contents " +
      "FROM todo " +
      "JOIN user ON user.userID = todo.userID " +
      "WHERE user.userName = ? ";

    //to select only todos that are not complete.
    if(status){
      todoListString += "AND todo.status = 0";
    }

    PreparedStatement todoListPS = connection.getConnection().prepareStatement(todoListString);

    todoListPS.setString(1, currentUser);
    ResultSet result = todoListPS.executeQuery();

    // to get total ammount of todos.
    result.last();
    int total = result.getRow();
    System.out.println(total + " Todos.");

    //checks to see if there are any todos to display.
    if(total > 0){

      //reset pointer back to the first row of result.
      result.beforeFirst();

      //column names.
      System.out.printf("%-8s %-8s %-7s \n", "Complete", "Priority", "Conents");

      //printing each todo
      while(result.next()){

        String todoStatus = (result.getBoolean("todo.status")) ? "Yes" : "No";
        System.out.printf("%-8s %-8d %-255s \n", todoStatus, result.getInt("todo.priority"), result.getString("todo.contents"));

      }
    }
    else{
      System.out.println("No todos to display");
    }
  }

  private void makeTodoItemDB()throws SQLException{

    String makeTodoString = "INSERT INTO todo (userID,contents,status,priority)
    VALUES ()";

  }

}
