# todoAPP
### Getting Started
##### Prerequisites
* [Install Java](https://www.java.com/en/download/help/download_options.xml)
* mySql
  * [MySql Community Server](https://dev.mysql.com/downloads/mysql/)
  * [MySQL Connectors](https://dev.mysql.com/downloads/connector/j/) 
* [Install Maven](https://maven.apache.org/download.cgi)
* Install Packages.
  * [Install odbc7](http://www.oracle.com/technetwork/database/features/jdbc/jdbc-drivers-12c-download-1958347.html) 
    * download the ojdbc7.jar file and place it in the root of the project directory. 
  * [Install Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
    * download and unzip the archive and place the mysql-connector-java-5.1.45-bin.jar file in root
    of the project directory.
### Installing the app
1. Import the database (localhost port:3306).
   * log into mysql console on command line.
   * run `-u <username> -p <databasename> < <PATH TO DBtodoAPP.sql> `
      * this file is found in the database directory that is located in the projects root directory
2. compile and install app.
    * run "mvn insall" from root directory in command line.
3. From the project root navigate to the directory named "target" and run ` java -jar todoAPP-0.0.1.jar" `.
### Using the app
1. Login Menu:  
![1](https://imgur.com/ovtocuE.png)
    * Enter 1 to login.  
    ![2](https://imgur.com/Db8iuGJ.png)
        * Enter username.
        * Enter password.
    * Enter 2 to create new user.  
    ![3](https://imgur.com/z4z1OX3.png)
        * Enter in new username (less than 16 characters).
        * Enter in new password (less than __ characters).
        * Enter 3 to exit the app.
2. Main Menu:  
![4](https://imgur.com/uK77qjx.png)
    * Enter 1 to Navigate to Todo Menu.
    * Enter 2 to navigate to Messages Menu.
    * Enter 3 to logout and navigate back to login menu.
3. Todo Menu:  
![5](https://imgur.com/E8TH3NU.png)
    * Enter 1 to display all todos that belong to current user.  
    ![6](https://imgur.com/TmRNGEC.png)
    * Enter 2 to display only the incomplete todos that belong to the current user.  
    ![7](https://imgur.com/RM0Z3vs.png)
    * Enter 3 to make a todo item.  
    ![8](https://imgur.com/FAgnSTD.png)
    * Enter 4 to remove a todo item.  
    ![9](https://imgur.com/GjQlynZ.png)
    * Enter 5 to return to main menu.
4. Messages Menu:  
![10](https://imgur.com/n561JT8.png)
    * Enter 1 to see all of the messages that belong to current user.  
    ![11](https://imgur.com/3bHQe59.png)
    * Enter 2 to see all of the unread messages that belong to the current user.  
    ![12](https://imgur.com/L9WjrIn.png)
    * Enter 3 to make new message.  
    ![13](https://imgur.com/ODKbYRU.png)
    * Enter 4 to remove a message.  
    ![14](https://imgur.com/WaI2SCY.png)
    * Enter 5 to return to main menu.
### Tools Used
[Maven](https://maven.apache.org/)  
[Spring](https://spring.io/)  
[MySQL](https://www.mysql.com/)  
[JAVA](http://www.oracle.com/technetwork/java/index.html)

