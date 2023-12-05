import java.util.Scanner;

public class App {
    private static Scanner sc;
    public static void main(String[] args) throws Exception {
        System.out.println("Press 1 for Manager or 2 for Client");
        sc = new Scanner(System.in);
        int loginDecision = sc.nextInt();
        if(loginDecision==1) {
            new Manager().Run();
        }
        else if(loginDecision==2) {
            new Client().Run();
        }
    }
}
