import java.util.Scanner;

public class LogIn {
    //Attributes
    private String username = "client";
    private String password = "123";
    private Scanner sc;

    //Constructor
    public LogIn(){}

    //Function
    public void Run(){
        //Username prompt
        sc = new Scanner(System.in);
        System.out.println("Enter Username : ");
        String inputUsername = sc.next();

        //Password prompt
        sc = new Scanner(System.in);
        System.out.println("Enter Password : ");
        String inputPassword = sc.next();
        
        //Check if input username and password are valid or not
        if (inputUsername.equals(this.username) && inputPassword.equals(this.password)) {
            System.out.println("Access Granted! Welcome!");
        }
        else if (inputUsername.equals(this.username)) {
            System.out.println("Invalid Password!");
        }
        else if (inputPassword.equals(this.password)) {
            System.out.println("Invalid Username!");
        }
        else {
            System.out.println("Invalid Username & Password!");
        }
    }

}
