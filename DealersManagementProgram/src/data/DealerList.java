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
    
    public DealerList(){}
    
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
        String inputID;
        inputID = MyTool.readPattern("Enter ID: ", Dealer.ID_FORMAT);
        int pos;
        pos = searchDealer(inputID);
        if (pos < 0) {
            System.out.println("Not found!");
        } else {
            System.out.print("Dealer: " + this.get(pos));
        }
    }

    public void addDealer() {
        String ID;
        String name;
        String addr;
        String phone;
        boolean continuing;
        int pos;
        do {
            ID = MyTool.readPattern("ID of new dealer: ", Dealer.ID_FORMAT);
            ID = ID.toUpperCase();
            pos = searchDealer(ID);
            if (pos >= 0) {
                System.out.println("ID is duplicated!");
            }
        } while (pos >= 0);
        name = MyTool.readNonBlank("Name of new dealer: ").toUpperCase();
        addr = MyTool.readNonBlank("Address of new dealer: ");
        phone = MyTool.readPattern("Phone number: ", Dealer.PHONE_FORMAT);
        continuing = true;
        Dealer d = new Dealer(ID, name, addr, phone, continuing);
        this.add(d);
        System.out.println("New Dealer has been added.");
        changed = true;
    }

    public void removeDealer() {
        String inputID;
        inputID = MyTool.readPattern("Enter ID: ", Dealer.ID_FORMAT);
        int pos;
        pos = searchDealer(inputID);
        if (pos < 0) {
            System.out.println("Not found!");
        } else {
            this.get(pos).setContinuing(false);
            System.out.println("Removed");
            changed = true;
        }
    }

    public void updateDealer() {
        String inputID;
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
            newPhone = MyTool.readPattern("new phone, ENTER for omitting: ", Dealer.PHONE_FORMAT);
            if (!newPhone.isEmpty()) {
                d.setPhone(newPhone);
                changed = true;
            }
        }
    }

    public void printAllDealers() {
        if (this.isEmpty()) {
            System.out.println("Empty list!");
        } else {
            System.out.println(this);
        }
    }

    public void printContinuingDealers() {
        this.getContinuingList().printAllDealers();
    }

    public void printUnContinuingDealers() {
        this.getUnContinuingList().printAllDealers();
    }

    public void writeDealerToFile() {
        if (changed) {
            MyTool.writeFile(dataFile, this);
            changed = false;
        }
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
