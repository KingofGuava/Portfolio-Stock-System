import java.util.Scanner;
public class LogRegPage {
    //Instance variables
    Scanner input = new Scanner(System.in);
    String regOrLogInput;
    String name, username, password, email;
    
    //Methods
    public void run() {
        while(true) {
            System.out.println("Do you wish to (l)ogin or (r)egister");
            regOrLogInput = input.nextLine();
            
            if(regOrLogInput.equalsIgnoreCase("l")) {
                System.out.println("Enter username:\n");
                username = input.nextLine();
                System.out.println("Enter password:\n");
                password = input.nextLine();
                LogIn login = new LogIn(username, password);
                login.runLogReg();
            }else if(regOrLogInput.equalsIgnoreCase("r")) {
                System.out.println("Enter name:\n");
                name = input.nextLine();
                System.out.println("Enter password:\n");
                password = input.nextLine();
                System.out.println("Enter email:\n");
                email = input.nextLine();
                Register register = new Register(name, email, password);
                register.runLogReg();
            }
            System.out.println("Do you wish to shutdown the system, (y)es or (n)o?");
            String inputText = input.nextLine();
            if(inputText.contentEquals("y")) {
                break;
            }
        }
    }
}

