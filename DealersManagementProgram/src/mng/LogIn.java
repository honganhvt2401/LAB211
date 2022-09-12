/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mng;

import data.Account;
import data.AccountChecker;
import data.DealerList;
import tools.MyTool;

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
        String inputAccName,inputPass, inputRole;
        System.out.println("Please Login to System.");
        System.out.print("Your account name: ");
        inputAccName = MyTool.SC.nextLine().trim();
        System.out.print("Your password: ");
        inputPass = MyTool.SC.nextLine().trim();
        System.out.print("Your role: ");
        inputRole = MyTool.SC.nextLine().trim();
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
                cont = MyTool.readBool("Invalid account - Try again?");
            }
            if (!valid && !cont) {
                System.exit(0);
            }
            if(valid == true){
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
                choice = menu.getChoice("Managing dealers");
                switch (choice) {
                    case 1:
                        dList.addDealer();
                        break;
                    case 2:
                        dList.searchDealer();
                        break;
                    case 3:
                        dList.removeDealer();
                        break;
                    case 4:
                        dList.updateDealer();
                        break;
                    case 5:
                        dList.printAllDealers();
                        break;
                    case 6: dList.printContinuingDealers(); break;
                    case 7: dList.printUnContinuingDealers(); break;
                    case 8:
                        dList.writeDealerToFile();
                        break;
                    default:
                        if (dList.isChanged()) {
                            boolean res = MyTool.readBool("Data changed. Write to file?");
                            if (res == true) {
                                dList.writeDealerToFile();
                            }
                        }
                }
            } while (choice > 0 && choice <= menu.size());
            System.out.println("Bye.");
        }
    }
}
