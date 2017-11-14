package todoAPP;

import java.util.LinkedHashSet;
import java.util.Arrays;
import java.lang.IllegalStateException;
import org.apache.log4j.Logger;
import java.sql.Connection;

public class TodoFront{

  static DatabaseConnection connection;

  private static final LinkedHashSet<String> loginMenuOptions = new LinkedHashSet<String>(Arrays.asList("Login", "Create Login", "Quit"));
  private MenuFactory loginMenuFactory = new MenuFactory(loginMenuOptions);

  private static final LinkedHashSet<String> mainMenuOptions = new LinkedHashSet<String>(Arrays.asList("Todo Menu","Messages Menu", "Logout"));
  private MenuFactory mainMenuFactory = new MenuFactory(mainMenuOptions);

  private static final LinkedHashSet<String> todoMenuOptions = new LinkedHashSet<String>(Arrays.asList("List Todo items", "Make Todo items", "Remove Todo Item", "Back"));
  private MenuFactory todoMenuFactory = new MenuFactory(todoMenuOptions);

  private static final LinkedHashSet<String> messageMenuOptions = new LinkedHashSet<String>(Arrays.asList("See All Messages", "View Unread Messages", "Make Message", "Remove Message", "Back"));
  private MenuFactory messageMenuFactory = new MenuFactory(messageMenuOptions);

  public TodoFront(){
    connection = new DatabaseConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/todoAPP?useSSL=false", "user", "password");
    loginMenuFactory.display();
  }
}
