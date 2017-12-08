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

  private String currentUserName;
  private Integer currentUserID;

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
          loginDB();

          if(this.currentUserID != null){
            mainMenu();
          }
          else{
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

          if(this.currentUserID != null){
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

        try{
          makeTodoItemDB();

        }catch (SQLException e){
          System.out.println("Error while making todo.");
          logger.error("TodoFront.todo", e);

        }finally{
          todo();
        }

      break;

      case "Remove Todo Item" :
        try{
          removeTodoItemDB();

        }catch (SQLException e){
          System.out.println("Error while removing todo.");
          logger.error("TodoFront.todo", e);

        }finally{
          todo();
        }
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

  private void loginDB() throws SQLException{

    System.out.println("Enter user name");
    String userName = keyboard.nextLine();

    System.out.println("Enter password");
    String passwordPlainTxt = keyboard.nextLine();

    PreparedStatement loginPS = this.connection.getConnection().prepareStatement("SELECT userID, userName, passwordHash " +
      " FROM user " +
      " WHERE userName = ?");

    loginPS.setString(1, userName);
    ResultSet result = loginPS.executeQuery();

    //checkse that both the user exists and the password matches the stored passwordHash.
    if(result.next() && BCrypt.checkpw(passwordPlainTxt, result.getString("passwordHash"))){
      System.out.println("logged in as " + userName);
      this.currentUserName = result.getString("userName");
      this.currentUserID = result.getInt("userID");
    }
    else{
      System.out.println("invalid username or password!");
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

    this.currentUserName = userName;
  }

  private void displayTodoDB(Boolean status)throws SQLException{

    String todoListString = "SELECT todo.ID, todo.status, todo.priority, todo.contents " +
      "FROM todo " +
      "JOIN user ON user.userID = todo.userID " +
      "WHERE user.userName = ? ";

    //to select only todos that are not complete.
    if(status){
      todoListString += "AND todo.status = 0";
    }

    PreparedStatement todoListPS = connection.getConnection().prepareStatement(todoListString);

    todoListPS.setString(1, currentUserName);
    ResultSet result = todoListPS.executeQuery();

    // to get total ammount of todos.
    result.last();
    int total = result.getRow();
    System.out.println(total + " Todos.");

    //checks to see if there are any todos to display.
    if(total > 0){

      //reset pointer back to the first row of result.
      result.beforeFirst();

      System.out.println("here");

      //column names.
      System.out.println( "ID\tComplete\tPriority\tConents");

      //printing each todo
      while(result.next()){

        String todoStatus = (result.getBoolean("todo.status")) ? "Yes" : "No";
        System.out.println(result.getInt("todo.ID") + "\t" + todoStatus  + "\t\t" + result.getInt("todo.priority") + "\t\t" + result.getString("todo.contents"));

      }
    }
    else{
      System.out.println("No todos to display");
    }
  }

  private void makeTodoItemDB()throws SQLException{

    String tempInput;

    String makeTodoString = "INSERT INTO todo (userID,contents,status,priority) VALUES (?,?,?,?)";
    PreparedStatement makeTodoPS = connection.getConnection().prepareStatement(makeTodoString);

    makeTodoPS.setInt(1, this.currentUserID);

    System.out.println("Enter todo contents (255 char)");
    tempInput = keyboard.nextLine();
    makeTodoPS.setString(2, tempInput);

    do{

      System.out.println("Enter status (N = not completed / C = completed)");
      tempInput = keyboard.nextLine();
      tempInput = tempInput.replaceAll("\\s+","");
      tempInput = tempInput.toUpperCase();

      if(tempInput.equals("N")){
        makeTodoPS.setBoolean(3, false);
      }
      else if(tempInput.equals("C")){
        makeTodoPS.setBoolean(3, true);
      }
      else{
        System.out.println("not a valid status!");
      }
    }while(!tempInput.equals("N") && !tempInput.equals("C"));

    System.out.println("Enter priority (0-9, 0 = most important)");

    int tmpInt = 0;
    // insist proper priority is entered.
    while(!keyboard.hasNextInt() || (tmpInt = keyboard.nextInt()) < 0 || tmpInt > 9){
        tempInput = keyboard.next();
        System.out.println("incorrect priority input!");
        System.out.println("Enter priority (0-9, 0 = most important)");
    }

    int priority = tmpInt;
    makeTodoPS.setInt(4, priority);

    // state > 0 = sucsessful INSERT.
    if(makeTodoPS.executeUpdate() > 0){
      System.out.println("making todo sucsessful");
    }
    else{
      System.out.println("making todo Unsucsessful");
    }
  }

  private void removeTodoItemDB()throws SQLException{
    String tempInput;

    System.out.println("Enter ID of todo to delete.");

    String deleteTodoStringPS = "DELETE FROM todo WHERE userID = ? and ID = ?";
    PreparedStatement makeTodoPS = connection.getConnection().prepareStatement(deleteTodoStringPS);

    makeTodoPS.setInt(1, this.currentUserID);

    while(!keyboard.hasNextInt()){
      tempInput = keyboard.next();
      System.out.println("not a valid todo ID");
      System.out.println("Enter ID of todo to delete.");
    }

    makeTodoPS.setInt(2, keyboard.nextInt());

    // state > 0 sucsessful delete.
    if(makeTodoPS.executeUpdate() > 0){
      System.out.println("delete todo sucsessful");
    }
    else{
      System.out.println("delete todo Unsucsessful");
    }
  }
}
