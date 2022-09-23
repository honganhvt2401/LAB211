/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mng;

import data.Account;
import data.AccountChecker;
import data.DealerList;
import java.util.Arrays;
import java.lang.String;
import tools.MyTool;
import java.io.Console;

/**
 *
 * @author VU HONG ANH
 */
public class LogIn {

    private Account acc = null;

    public LogIn(Account acc) {
        this.acc = acc;
    }

    public static Account inputAccount() {
        Console c = System.console();
        String inputAccName, inputPass, inputRole;
        System.out.println("Please Login to System.");
        do {
            System.out.print("Your account name: ");
            inputAccName = MyTool.SC.nextLine().trim();
            if (inputAccName.isEmpty()) {
                System.out.println("Invalid input, try again");
            }
        } while (inputAccName.isEmpty());
        do {
            System.out.print("Your password: ");
            inputPass = MyTool.SC.nextLine().trim();
            if (inputPass.isEmpty()) {
                System.out.println("Invalid input, try again");
            }
        } while (inputPass.isEmpty());
        do {
            System.out.print("Your role: ");
            inputRole = MyTool.SC.nextLine().trim();
            if (inputRole.isEmpty()) {
                System.out.println("Invalid input, try again");
            }
        } while (inputRole.isEmpty());
        Account inputAcc = new Account(inputAccName, inputPass, inputRole);
        return inputAcc;
    }

    public Account getAcc() {
        return acc;
    }

    public static void main(String[] args) {
        Account acc = null;
        boolean cont = false;
        boolean valid = false;
        do {
            AccountChecker accChk = new AccountChecker();
            acc = inputAccount();
            valid = accChk.check(acc);
            if (!valid) {
                cont = MyTool.readBool("Incorrect password or account name - Try again?");
            }
            if (!valid && !cont) {
                System.exit(0);
            }
            if (valid == true) {
                break;
            }
        } while (cont);
        LogIn loginObj = new LogIn(acc);
        if (acc.getRole().equalsIgnoreCase("ACC-1")) {
            String[] options = {"Add new dealer", "Search a dealer",
                "Remove a dealer", "Update a dealer",
                "Print all dealers", "Print continuing dealers",
                "Print UN-continuing dealers", "Write to file"};
            Menu menu = new Menu(options);
            DealerList dList = new DealerList(loginObj);
            dList.initWithFile();
            int choice = 0;
            do {
                System.out.println("-----------------------------------------------------------------------------");
                choice = menu.getChoice("-----------------------------[MANAGING DEALERS]------------------------------");
                switch (choice) {
                    case 1:
                        System.out.println("-----------------------------------------------------------------------------");
                        dList.addDealer();
                        break;
                    case 2:
                        System.out.println("-----------------------------------------------------------------------------");
                        dList.searchDealer();
                        break;
                    case 3:
                        System.out.println("-----------------------------------------------------------------------------");
                        dList.removeDealer();
                        break;
                    case 4:
                        System.out.println("-----------------------------------------------------------------------------");
                        dList.updateDealer();
                        break;
                    case 5:
                        System.out.println("-----------------------------------------------------------------------------");
                        dList.printAllDealers();
                        break;
                    case 6:
                        System.out.println("-----------------------------------------------------------------------------");
                        dList.printContinuingDealers();
                        break;
                    case 7:
                        System.out.println("-----------------------------------------------------------------------------");
                        dList.printUnContinuingDealers();
                        break;
                    case 8:
                        System.out.println("-----------------------------------------------------------------------------");
                        dList.writeDealerToFile();
                        System.out.println("Done!");
                        break;
                }
            } while (choice > 0 && choice <= menu.size());
            MyTool.SC.nextLine();
            boolean res = MyTool.readBool("Data changed. Write to file? ");
            if (res = true) {
                dList.writeDealerToFile();
            }
            System.out.println("Bye.");
        }
    }
}
