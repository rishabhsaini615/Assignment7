package com.rishabh;

import javax.swing.*;
import java.io.IOException;

import static java.lang.Double.isNaN;

public class Main {

    public static void main(String[] args){

        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("thank you!");
        }
    }

    public static void start() throws IOException {
        BankAccount account;
        int input = JOptionPane.showConfirmDialog(null, "Do you have an account in the Bank? \n\nIf no, press no to create a new account.");
        if(input == JOptionPane.NO_OPTION){
            account = openNewAccount();
        }else if(input == JOptionPane.YES_OPTION){
            account = new BankAccount(getAccountId());
        }else{
            return;
        }

        if(account.getDetails().length < 2 ) {
            JOptionPane.showMessageDialog(null, "Invalid Account number! Try Again!");
            start();
        }


        while(input != JOptionPane.CANCEL_OPTION){
            String output = "Name - "+account.getDetails()[0]+"\nAccount number - "+account.getAccountId();
            output += "\nBalance - "+account.getDetails()[1];
            String option = JOptionPane.showInputDialog(output+"\nActions:-\n1 to to diposit\n2 for withdraw").trim();
            double amount = 0;

            try{
                amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount").trim());
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Wrong Input! Try Again!");
                continue;
            }

            if(isNaN(amount) || amount < 0){
                JOptionPane.showMessageDialog(null, "Wrong Input! Try Again!");
                continue;
            }

            if(option.equals("2")){
                amount = -amount;
            }

            account.processTransaction(new Transaction(amount));

        }

    }


    public static String getAccountId(){
        String str = JOptionPane.showInputDialog("Enter you account number");
        return str;
    }


    public static BankAccount openNewAccount() throws IOException {
        String name = JOptionPane.showInputDialog("Enter your name").trim();
        return BankAccount.open(name);
    }
}
