/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import tools.MyTool;

/**
 *
 * @author VU HONG ANH
 */
public class Dealer implements Comparable<Dealer> {

    public static final char SEPERATOR = ',';
    public static final String ID_FORMAT = "D|D\\d{3}";
    public static final String PHONE_FORMAT = "\\d{9}|\\d{11}";
    private String ID;
    private String name;
    private String addr;
    private String phone;
    private boolean continuing;

    public Dealer(String ID, String name, String addr, String phone, boolean continuing) {
        this.ID = ID;
        this.name = name.toUpperCase();
        this.addr = addr.toUpperCase();
        this.phone = phone;
        this.continuing = continuing;
    }

    public Dealer(String line) {
        String[] parts = line.split("" + this.SEPERATOR);
        ID = parts[0].trim();
        name = parts[1].trim().toUpperCase();
        addr = parts[2].trim().toUpperCase();
        phone = parts[3].trim();
        continuing = MyTool.parseBool(parts[4]);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isContinuing() {
        return continuing;
    }

    public void setContinuing(boolean continuing) {
        this.continuing = continuing;
    }

    @Override
    public String toString() {
        return ID + SEPERATOR + name + SEPERATOR + addr + SEPERATOR + phone + SEPERATOR + continuing + "\n";
    }

    @Override
    public int compareTo(Dealer o) {
        return this.getID().compareToIgnoreCase(o.getID());
    }

}
