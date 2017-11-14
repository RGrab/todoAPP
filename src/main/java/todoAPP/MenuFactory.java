package todoAPP;
import java.util.Scanner;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Integer;
import java.lang.IllegalStateException;
import org.apache.log4j.Logger;

public class MenuFactory{

final static Logger logger = Logger.getLogger(MenuFactory.class);
private HashMap<String, String> menuMap = new HashMap<String, String>();

  public MenuFactory(ArrayList<String> menu){
    for(int i = 0; i < menu.size() ; i++){
      this.menuMap.put(Integer.toString(i + 1), menu.get(i));
    }
  }

  public MenuFactory(String[] menu){
    for(int i = 0; i < menu.length; i++){
      this.menuMap.put(Integer.toString(i + 1), menu[i]);
    }
  }

  public void add(String option){
    try{
      if(menuMap.containsKey(option)){
        throw new IllegalStateException("illigal menu addition.");
      }
      else{
        menuMap.put(Integer.toString(menuMap.size() + 1 ) , option);
      }
    }catch(IllegalStateException e){
      logger.error("MenuFactory.add", e);
    }
  }

  public void remove(String option){
    try{
      if(menuMap.containsKey(option)){
            menuMap.remove(option);
      }
      else{
        throw new IllegalStateException("illegal menu removal.");
      }
    }catch(IllegalStateException e){
      logger.error("MenuFactory.remove", e);
    }
  }

  public void display(){
    try{
      if(menuMap.isEmpty()){
        throw new IllegalStateException("nothing to display menu empty.");
      }
      else{
          for(int i = 0; i < menuMap.size(); i++){
          System.out.println((i + 1) + " : " + menuMap.get(Integer.toString(i + 1)));
        }
      }
    }catch(IllegalStateException e){
      logger.error("MenuFactory.display", e);
    }
  }

  public String choose() throws IllegalStateException{
    if(menuMap.isEmpty()){
      throw new IllegalStateException("nothing to choose menu empty");
    }

    Scanner keyboard = new Scanner(System.in);

    while(true){
      String choice = keyboard.nextLine();
      if(menuMap.containsKey(choice)){
        return menuMap.get(choice);
      }else{
        System.out.println("Selection " + choice + " not an option." );
      }
    }
  }
}
