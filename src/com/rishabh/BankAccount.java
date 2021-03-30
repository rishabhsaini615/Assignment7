package com.rishabh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BankAccount {

    private String accountId;

    public BankAccount(){
        this.accountId = "";
    }


    public BankAccount(String accountId){
        this();
        this.accountId = accountId;
    }

    public String getAccountId(){
        return this.accountId;
    }


    public static BankAccount open(String name) throws IOException {
        String randomId = (int) (Math.random() * 10000)+"";
        while(true){
            if(!accountExists(randomId)){
                File file = new File(randomId+".txt");
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write(name+"-"+0);
                writer.close();

                return new BankAccount(randomId);
            }
            randomId = (int) (Math.random() * 10000)+"";
        }
    }


    public String[] getDetails() throws FileNotFoundException {
        String[] details = {};
        if(accountExists(this.accountId)){
            File file = new File(this.accountId+".txt");
            Scanner reader = new Scanner(file);
            String data = "";
            while (reader.hasNextLine()) {
                data = reader.nextLine().trim();
                if(!data.equals(""))
                    break;
            }
            details = data.split("-");
        }

        return details;
    }


    public void processTransaction(Transaction transaction) throws IOException {
        String[] details = getDetails();
        String name = details[0];
        double balance = Double.parseDouble(details[1]);

        double updatedBalance = balance + transaction.getAmount();

        if(updatedBalance < 0){
            System.out.println("Insufficient Balance!");
            return ;
        }

        File file = new File(this.accountId+".txt");
        FileWriter writer = new FileWriter(file, false);
        writer.write(name+"-"+updatedBalance);
        writer.close();
    }


    public static boolean accountExists(String accountId){
        return new File(accountId + ".txt").exists();
    }

}

class Transaction{
    private double amount;

    public Transaction(double amount){
        this.amount = amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }
    public double getAmount(){
        return this.amount;
    }
}
