package todoAPP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.ClassNotFoundException;
import java.util.EmptyStackException;

public class DatabaseConnection {

  private static Connection connection;

  public DatabaseConnection(String driver, String url, String username, String password){
    try{
      makeConnection(driver, url, username, password);
    }catch(ClassNotFoundException e){
      e.printStackTrace();
    }catch(SQLException e){
      e.printStackTrace();
    }
  }

  public static Connection makeConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException{
    Class.forName(driver);
    if ((username == null) || (password == null) || (username.trim().length() == 0) || (password.trim().length() == 0))
    {
      return DriverManager.getConnection(url);
    }
    else
    {
      return DriverManager.getConnection(url, username, password);
    }
  }

  public static void close(Connection connection){
    try{
      if (connection != null){
        connection.close();
      }
    }catch (SQLException e){
      e.printStackTrace();
    }
  }

  public static void closeConnection(Statement statement){
    try{
      if (statement != null){
        statement.close();
      }
    }catch (SQLException e){
      e.printStackTrace();
    }
  }

  public static void close(ResultSet result){
    try{
        if (result != null)
        {
            result.close();
        }
    }
    catch (SQLException e){
        e.printStackTrace();
    }
  }

  public static void rollback(Connection connection){
    try{
      if (connection != null){
        connection.rollback();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static List<Map<String, Object>> map(ResultSet resultSet) throws SQLException{

    List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

    try{
      if (resultSet != null){
        ResultSetMetaData meta = resultSet.getMetaData();
        int numColumns = meta.getColumnCount();
        while (resultSet.next()){
          Map<String, Object> row = new HashMap<String, Object>();
          for (int i = 1; i <= numColumns; ++i){
            String name = meta.getColumnName(i);
            Object value = resultSet.getObject(i);
            row.put(name, value);
          }
          results.add(row);
        }
      }
    }finally{
      close(resultSet);
    }
    return results;
  }
}
