import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.IllegalStateException;
import org.apache.log4j.Logger;

public class menuFactory{

final static Logger logger = Logger.getLogger(menuFactory.class);
private Set<String> menuSet = new HashSet<String>();

  public menuFactory(ArrayList<String> menuSet){
    this.menuSet = new HashSet<String>(menuSet);
  }

  public menuFactory(HashSet<String> menuSet){
    this.menuSet = menuSet;
  }

  public void add(String option){
    try{
      if(menuSet.contains(option)){
        throw new IllegalStateException("illigal menu addition.");
      }
      else{
        menuSet.add(option);
      }
    }catch(IllegalStateException e){
      logger.error("menuFactory.add", e);
    }
  }

  public void remove(String option){
    try{
      if(menuSet.contains(option)){
            menuSet.remove(option);
      }
      else{
        throw new IllegalStateException("illegal menu removal.");
      }
    }catch(IllegalStateException e){
      logger.error("menuFactory.remove", e);
    }
  }

  public void display(){
    try{
      if(menuSet.isEmpty()){
        throw new IllegalStateException("nothing to display menu empty.");
      }
      else{
        Iterator menuIterator = menuSet.iterator();
        int optionNum = 1;
        while(menuIterator.hasNext()){
          System.out.println(optionNum + " : " + menuIterator.next());
          optionNum++;
        }
      }
    }catch(IllegalStateException e){
      logger.error("menuFactory.display", e);
    }
  }

  public String choose() throws IllegalStateException{
    Scanner keyboard = new Scanner(System.in);
    if(menuSet.isEmpty()){
      throw new IllegalStateException("nothing to choose menu empty");
    }
    while(true){
      String choice = keyboard.nextLine();
      keyboard.nextLine();
      
      if(menuSet.contains(choice)){
        return choice;
      }else{
        System.out.println("Selection " + choice + " not an option." );
      }
    }
  }
}
