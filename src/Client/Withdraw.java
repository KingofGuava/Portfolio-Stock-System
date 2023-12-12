package Client;

import java.util.Scanner;

public class Withdraw extends Transfer{
    private Scanner sc;

    public Withdraw(){}

    @Override
    public void execute(){
        sc = new Scanner(System.in);
        System.out.println("Please input the amount of money you would like to withdraw");
        double money = sc.nextDouble();
        Withdraw(money);
    }

    public void Withdraw(double amount){
        System.out.println("Your current balance is: " + this.balance);
        System.out.println("How much money do you want to withdraw?");
        this.balance -= amount;                         //deduct money from wallet
        System.out.println("Your balance after withdraw is: " + this.balance);
    }
}
