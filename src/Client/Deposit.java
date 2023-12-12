package Client;
import java.util.Scanner;

public class Deposit extends Transfer{
    private Scanner sc;
    public Deposit(){}

    @Override
    public void execute(){
        sc = new Scanner(System.in);
        System.out.println("Please input the amount of money you would like to deposit");
        double money = sc.nextDouble();
        Deposit(money);
    }

    public void Deposit(double amount){
        System.out.println("Your current balance is: " + this.balance);
        System.out.println("How much money do you want to deposit?");
        this.balance += amount;                         //add money to wallet
        System.out.println("Your balance after deposit is: " + this.balance);
    }
}
