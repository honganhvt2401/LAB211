/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.util.*;
import java.sql.Date;
import tools.MyTool;
import mng.LogIn;

/**
 *
 * @author VU HONG ANH
 */
public class DealerList extends ArrayList<Dealer> {

    LogIn loginObj = null;
    private static final String PHONEPATTERN = "\\d{9}|\\d{11}";
    private String dataFile = "";
    boolean changed = false;

    public DealerList() {
    }

    public DealerList(LogIn loginObj) {
        this.loginObj = loginObj;
    }

    private void loadDealerFromFile() {
        List<String> str = MyTool.readLinesFromFile(dataFile);
        for (String s : str) {
            Dealer d = new Dealer(s);
            this.add(d);
        }
    }

    public void initWithFile() {
        Config cR = new Config();
        dataFile = cR.getDealerFile();
        loadDealerFromFile();
    }

    public DealerList getContinuingList() {

        DealerList resultList = new DealerList();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).isContinuing()) {
                resultList.add(this.get(i));
            }
        }
        return resultList;

    }

    public DealerList getUnContinuingList() {
        DealerList resultList = new DealerList();
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).isContinuing()) {
                resultList.add(this.get(i));
            }
        }
        return resultList;
    }

    private int searchDealer(String ID) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID().equals(ID)) {
                return i;
            }
        }
        return -1;
    }

    public void searchDealer() {
        int searchChoice = 0;
        do {
            System.out.println("------------------------------[SEARCH MENU]----------------------------------");
            System.out.println("   1-Search by ID");
            System.out.println("   2-Search by name");
            System.out.println("   3-Search by address");
            System.out.println("   4-Search by phone number");
            System.out.println("   Others-Return to menu");
            System.out.print("Choose [1..4]: ");
            searchChoice = MyTool.SC.nextInt();
            switch (searchChoice) {
                case 1:
                    searchByID();
                    break;
                case 2:
                    searchByName();
                    break;
                case 3:
                    searchByAddress();
                    break;
                case 4:
                    searchByPhone();
                    break;
            }
        } while (searchChoice > 0 && searchChoice <= 4);
        /*String inputID;
        inputID = MyTool.readPattern("Enter ID: ", Dealer.ID_FORMAT);
        int pos;
        pos = searchDealer(inputID);
        if (pos < 0) {
            if(inputID.equals("D")){
                printAllDealers();
            } else System.out.println("Not found!");
        } else {
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING   |");
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.format("|%-10s|%-10s|%-20s|%-15s|%-16b|\n", this.get(pos).getID(), this.get(pos).getName(), this.get(pos).getAddr(), this.get(pos).getPhone(), this.get(pos).isContinuing());
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
        }*/
    }

    public void addDealer() {
        String ID;
        String name;
        String addr;
        String phone;
        boolean continuing;
        int pos;
        MyTool.SC.nextLine();
        do {
            ID = MyTool.readPattern("ID of new dealer: ", Dealer.ID_FORMAT).toUpperCase();
            if (ID.isEmpty()) {
                System.out.println("Invalid input (Format: D***, * is a number(0-9)), try again");
                addDealer();
            }

            pos = searchDealer(ID);
            if (pos >= 0) {
                System.out.println("ID is duplicated!");
            }
        } while (pos >= 0);
        do {
            name = MyTool.readNonBlank("Name of new dealer: ").toUpperCase();
            if (name.equals("\\n")) {
                System.out.println("Invalid input, try again");
            }
        } while (name.equals(""));
        do {
            addr = MyTool.readNonBlank("Address of new dealer: ");
            if (addr.equals("\\n")) {
                System.out.println("Invalid input, try again");
            }
        } while (addr.equals(""));
        do {
            phone = MyTool.readPattern("Phone number: ", Dealer.PHONE_FORMAT);
            if (phone.equals("\\n")) {
                System.out.println("Invalid input, try again");
            }
        } while (phone.equals(""));
        continuing = true;
        Dealer d = new Dealer(ID, name, addr, phone, continuing);
        this.add(d);
        System.out.println("New Dealer has been added.");
        changed = true;
    }

    public void removeDealer() {
        int removeChoice = 0;
        do {
            System.out.println("-------------------------------[REMOVE MENU]---------------------------------");
            System.out.println("   1-Remove continuing status");
            System.out.println("   2-Remove dealer permanently from the program");
            System.out.println("   Others-Return to menu");
            System.out.print("Choose [1..2]: ");
            removeChoice = MyTool.SC.nextInt();
            switch (removeChoice) {
                case 1:
                    remover(1);
                    break;
                case 2:
                    remover(2);
                    break;
            }
        } while (removeChoice > 0 && removeChoice <= 2);
        /*
         */
    }

    public void updateDealer() {
        String inputID;
        MyTool.SC.nextLine();
        inputID = MyTool.readPattern("Enter ID: ", Dealer.ID_FORMAT);
        int pos;
        pos = searchDealer(inputID);
        if (pos < 0) {
            System.out.println("Dealer " + inputID + " not found!");
        } else {
            Dealer d = this.get(pos);
            String newName = "";
            System.out.print("new name, ENTER for omitting: ");
            newName = MyTool.SC.nextLine().trim().toUpperCase();
            if (!newName.isEmpty()) {
                d.setName(newName);
                changed = true;
            }
            String newAddr = "";
            System.out.print("new address, ENTER for omitting: ");
            newAddr = MyTool.SC.nextLine();
            if (!newAddr.isEmpty()) {
                d.setAddr(newAddr);
                changed = true;
            }
            String newPhone = "";
            System.out.print("new phone, ENTER for omitting: ");
            newPhone = MyTool.SC.nextLine();
            if (!newPhone.isEmpty()) {
                if (MyTool.validStr(newPhone, Dealer.PHONE_FORMAT)) {
                    d.setPhone(newPhone);
                    changed = true;
                }
            }
            String newContinuing;
            System.out.print("update continuing, Enter for omitting: ");
            newContinuing = MyTool.SC.nextLine();
            if (!newContinuing.isEmpty()) {
                Boolean s = Boolean.parseBoolean(newContinuing);
                d.setContinuing(s);
                changed = true;
            }
        }
    }

    public void printAllDealers() {
        if (this.isEmpty()) {
            System.out.println("Empty list!");
        } else {
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING   |");
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            for (Dealer dealer : this) {
                System.out.format("|%-10s|%-10s|%-20s|%-15s|%-16b|\n", dealer.getID(), dealer.getName(), dealer.getAddr(), dealer.getPhone(), dealer.isContinuing());
            }
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
        }
    }

    public void printContinuingDealers() {
        this.getContinuingList().printAllDealers();
    }

    public void printUnContinuingDealers() {
        this.getUnContinuingList().printAllDealers();
    }

    public void writeDealerToFile() {
        MyTool.writeFile(dataFile, this);
        changed = false;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void searchByID() {
        List<Dealer> tempList = new ArrayList<Dealer>();
        String inputID;
        MyTool.SC.nextLine();
        inputID = MyTool.readNonBlank("Enter ID: ").toUpperCase();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID().contains(inputID)) {
                tempList.add(this.get(i));
            }
        }
        if (tempList.size() <= 0) {
            boolean b = MyTool.readBool("No dealers found! Would you like to try again? ");
            if (b = true) {
                searchByID();
            } else {
                searchDealer();
            }
        } else {
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING   |");
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            for (int i = 0; i < tempList.size(); i++) {
                System.out.format("|%-10s|%-10s|%-20s|%-15s|%-16b|\n", tempList.get(i).getID(), tempList.get(i).getName(), tempList.get(i).getAddr(), tempList.get(i).getPhone(), tempList.get(i).isContinuing());
            }
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
        }
        /*int pos;
        pos = searchDealer(inputID);
        if (pos < 0) {
            if (inputID.equals("D")) {
                printAllDealers();
            } else {
                System.out.println("Not found!");
            }
        } else {
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING   |");
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.format("|%-10s|%-10s|%-20s|%-15s|%-16b|\n", this.get(pos).getID(), this.get(pos).getName(), this.get(pos).getAddr(), this.get(pos).getPhone(), this.get(pos).isContinuing());
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
        }*/
    }

    public void searchByName() {
        System.out.println("-----------------------------------------------------------------------------");
        List<Dealer> tempList = new ArrayList<Dealer>();
        String inputName;
        MyTool.SC.nextLine();
        inputName = MyTool.readNonBlank("Enter name: ").toUpperCase();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getName().contains(inputName)) {
                tempList.add(this.get(i));
            }
        }
        if (tempList.size() != 0) {
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING   |");
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            for (int i = 0; i < tempList.size(); i++) {
                System.out.format("|%-10s|%-10s|%-20s|%-15s|%-16b|\n", tempList.get(i).getID(), tempList.get(i).getName(), tempList.get(i).getAddr(), tempList.get(i).getPhone(), tempList.get(i).isContinuing());
            }
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
        } else {
            boolean redo = MyTool.readBool("Dealer(s) not found! Would you like to try again? ");
            if (redo = true) {
                searchByName();
            } else {
                searchDealer();
            }
        }
    }

    public void searchByPhone() {
        System.out.println("-----------------------------------------------------------------------------");
        List<Dealer> tempList = new ArrayList<Dealer>();
        String inputPhone;
        MyTool.SC.nextLine();
        inputPhone = MyTool.readNonBlank("Enter phone number: ");
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getPhone().contains(inputPhone)) {
                tempList.add(this.get(i));
            }
        }
        if (tempList.size() != 0) {
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING   |");
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            for (int i = 0; i < tempList.size(); i++) {
                System.out.format("|%-10s|%-10s|%-20s|%-15s|%-16b|\n", tempList.get(i).getID(), tempList.get(i).getName(), tempList.get(i).getAddr(), tempList.get(i).getPhone(), tempList.get(i).isContinuing());
            }
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
        } else {
            boolean redo = MyTool.readBool("Dealer(s) not found! Would you like to try again? ");
            if (redo = true) {
                searchByPhone();
            } else {
                searchDealer();
            }
        }
    }

    public void searchByAddress() {
        System.out.println("-----------------------------------------------------------------------------");
        List<Dealer> tempList = new ArrayList<Dealer>();
        String inputAddr;
        MyTool.SC.nextLine();
        inputAddr = MyTool.readNonBlank("Enter address: ").toUpperCase();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getAddr().contains(inputAddr)) {
                tempList.add(this.get(i));
            }
        }
        if (tempList.size() != 0) {
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING   |");
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            for (int i = 0; i < tempList.size(); i++) {
                System.out.format("|%-10s|%-10s|%-20s|%-15s|%-16b|\n", tempList.get(i).getID(), tempList.get(i).getName(), tempList.get(i).getAddr(), tempList.get(i).getPhone(), tempList.get(i).isContinuing());
            }
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
        } else {
            boolean redo = MyTool.readBool("Dealer(s) not found! Would you like to try again? ");
            if (redo = true) {
                searchByAddress();
            } else {
                searchDealer();
            }
        }
    }

    //remover in progress
    public void remover(int change) {
        List<String> falseMenu = new ArrayList<String>();
        int tempChoice = 0;
        falseMenu.add("Remove by ID");
        falseMenu.add("Remove by name");
        falseMenu.add("Remove by address");
        falseMenu.add("Remove by phone number");
        do {
            System.out.println("----------------------------[METHODS OF REMOVING]----------------------------");
            for (int i = 0; i < falseMenu.size(); i++) {
                System.out.println("   " + (i + 1) + "-" + falseMenu.get(i));
            }
            System.out.println("   Others-Return to menu");
            System.out.print("Choose [1..4]: ");
            tempChoice = MyTool.SC.nextInt();
            switch (tempChoice) {
                case 1:
                    removeByID(change);
                    break;
                case 2:
                    removeByName(change);
                    break;
                case 3:
                    removeByAddress(change);
                    break;
                case 4:
                    removeByPhone(change);
                    break;
            }
        } while (tempChoice > 0 && tempChoice <= 4);
    }

    public void removeByID(int sign) {
        String inputID;
        MyTool.SC.nextLine();
        inputID = MyTool.readPattern("Enter ID: ", Dealer.ID_FORMAT);
        int pos;
        pos = searchDealer(inputID);
        if (sign == 1) {
            if (pos < 0) {
                boolean b = MyTool.parseBool("Dealer(s) not found. Do you want to try again?");
                if (b = true) {
                    removeByID(sign);
                } else {
                    removeDealer();
                }
            } else {
                this.get(pos).setContinuing(false);
                System.out.println("Removed dealer " + inputID + "'s continuing status");
            }
        }
        if (sign == 2) {
            if (pos < 0) {
                boolean b = MyTool.parseBool("Dealer(s) not found. Do you want to try again?");
                if (b = true) {
                    removeByID(sign);
                } else {
                    removeDealer();
                }
            } else {
                this.remove(this.get(pos));
                System.out.println("Permanently removed dealer " + inputID + ".");
            }
        } else {
            boolean b = MyTool.readBool("No dealer found! Would you like to try again? ");
            if(b = true){
                removeByAddress(sign);
            } else {
                removeDealer();
            }
        }
    }

    public void removeByName(int sign) {
        List<Dealer> tempList = new ArrayList<Dealer>();
        String inputName;
        MyTool.SC.nextLine();
        inputName = MyTool.readNonBlank("Enter name: ").toUpperCase();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getName().contains(inputName)) {
                tempList.add(this.get(i));
            }
        }
        if (tempList.size() != 0) {
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING   |");
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            for (int i = 0; i < tempList.size(); i++) {
                System.out.format("|%-10s|%-10s|%-20s|%-15s|%-16b|\n", tempList.get(i).getID(), tempList.get(i).getName(), tempList.get(i).getAddr(), tempList.get(i).getPhone(), tempList.get(i).isContinuing());
            }
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            int option;
            System.out.print("Choose dealer you want to delete [1.." + tempList.size() + "] (0 for all): ");
            option = MyTool.SC.nextInt();
            if (option < 0) {
                System.out.println("Invalid input, try again from the beginning");
                removeByName(sign);
            }
            if (option == 0) {
                if (sign == 1) {
                    for (int i = 0; i < this.size(); i++) {
                        for (int j = 0; j < tempList.size(); j++) {
                            if (this.get(i).getName().equals(tempList.get(j).getName())) {
                                this.get(i).setContinuing(false);
                            }
                        }
                    }
                } else if (sign == 2) {
                    for (int i = 0; i < this.size(); i++) {
                        for (int j = 0; j < tempList.size(); j++) {
                            if (this.get(i).getName().equals(tempList.get(j).getName())) {
                                this.remove(this.get(i));
                            }
                        }
                    }
                }
            }//
            if (option > 0) {
                if (sign == 1) {
                    for (int i = 0; i < this.size(); i++) {
                        if (this.get(i).getName().equals(tempList.get(option - 1).getName())) {
                            this.get(i).setContinuing(false);
                        }
                    }
                } else if (sign == 2) {
                    for (int i = 0; i < this.size(); i++) {
                        if (this.get(i).getName().equals(tempList.get(option - 1).getName())) {
                            this.remove(this.get(i));
                        }
                    }
                }
            }//
        } else {
            boolean b = MyTool.readBool("No dealer found! Would you like to try again? ");
            if(b = true){
                removeByAddress(sign);
            } else {
                removeDealer();
            }
        }
    }

    public void removeByAddress(int sign) {
        List<Dealer> tempList = new ArrayList<Dealer>();
        String inputAddr;
        MyTool.SC.nextLine();
        inputAddr = MyTool.readNonBlank("Enter address: ").toUpperCase();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getAddr().contains(inputAddr)) {
                tempList.add(this.get(i));
            }
        }
        if (tempList.size() != 0) {
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING   |");
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            for (int i = 0; i < tempList.size(); i++) {
                System.out.format("|%-10s|%-10s|%-20s|%-15s|%-16b|\n", tempList.get(i).getID(), tempList.get(i).getName(), tempList.get(i).getAddr(), tempList.get(i).getPhone(), tempList.get(i).isContinuing());
            }
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            int option;
            System.out.print("Choose dealer you want to delete [1.." + tempList.size() + "] (0 for all): ");
            option = MyTool.SC.nextInt();
            if (option < 0) {
                System.out.println("Invalid input, try again from the beginning");
                removeByName(sign);
            }
            if (option == 0) {
                if (sign == 1) {
                    for (int i = 0; i < this.size(); i++) {
                        for (int j = 0; j < tempList.size(); j++) {
                            if (this.get(i).getName().equals(tempList.get(j).getName())) {
                                this.get(i).setContinuing(false);
                            }
                        }
                    }
                } else if (sign == 2) {
                    for (int i = 0; i < this.size(); i++) {
                        for (int j = 0; j < tempList.size(); j++) {
                            if (this.get(i).getName().equals(tempList.get(j).getName())) {
                                this.remove(this.get(i));
                            }
                        }
                    }
                }
            }//
            if (option > 0) {
                if (sign == 1) {
                    for (int i = 0; i < this.size(); i++) {
                        if (this.get(i).getName().equals(tempList.get(option - 1).getName())) {
                            this.get(i).setContinuing(false);
                        }
                    }
                } else if (sign == 2) {
                    for (int i = 0; i < this.size(); i++) {
                        if (this.get(i).getName().equals(tempList.get(option - 1).getName())) {
                            this.remove(this.get(i));
                        }
                    }
                }
            }//
        } else {
            boolean b = MyTool.readBool("No dealer found! Would you like to try again? ");
            if(b = true){
                removeByAddress(sign);
            } else {
                removeDealer();
            }
        }
    }

    public void removeByPhone(int sign) {
        List<Dealer> tempList = new ArrayList<Dealer>();
        String inputPhone;
        MyTool.SC.nextLine();
        inputPhone = MyTool.readNonBlank("Enter phone number: ").toUpperCase();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getPhone().contains(inputPhone)) {
                tempList.add(this.get(i));
            }
        }
        if (tempList.size() != 0) {
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |   CONTINUING   |");
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            for (int i = 0; i < tempList.size(); i++) {
                System.out.format("|%-10s|%-10s|%-20s|%-15s|%-16b|\n", tempList.get(i).getID(), tempList.get(i).getName(), tempList.get(i).getAddr(), tempList.get(i).getPhone(), tempList.get(i).isContinuing());
            }
            System.out.println("+----------+----------+--------------------+---------------+----------------+");
            int option;
            System.out.print("Choose dealer you want to delete [1.." + tempList.size() + "] (0 for all): ");
            option = MyTool.SC.nextInt();
            if (option < 0) {
                System.out.println("Invalid input, try again from the beginning");
                removeByName(sign);
            }
            if (option == 0) {
                if (sign == 1) {
                    for (int i = 0; i < this.size(); i++) {
                        for (int j = 0; j < tempList.size(); j++) {
                            if (this.get(i).getName().equals(tempList.get(j).getName())) {
                                this.get(i).setContinuing(false);
                            }
                        }
                    }
                } else if (sign == 2) {
                    for (int i = 0; i < this.size(); i++) {
                        for (int j = 0; j < tempList.size(); j++) {
                            if (this.get(i).getName().equals(tempList.get(j).getName())) {
                                this.remove(this.get(i));
                            }
                        }
                    }
                }
            }//
            if (option > 0) {
                if (sign == 1) {
                    for (int i = 0; i < this.size(); i++) {
                        if (this.get(i).getName().equals(tempList.get(option - 1).getName())) {
                            this.get(i).setContinuing(false);
                        }
                    }
                } else if (sign == 2) {
                    for (int i = 0; i < this.size(); i++) {
                        if (this.get(i).getName().equals(tempList.get(option - 1).getName())) {
                            this.remove(this.get(i));
                        }
                    }
                }
            }//
        } else {
            boolean b = MyTool.readBool("No dealer found! Would you like to try again? ");
            if(b = true){
                removeByAddress(sign);
            } else {
                removeDealer();
            }
        }
    }
}
