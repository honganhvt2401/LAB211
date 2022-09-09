/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mng;

import java.util.ArrayList;
import tools.MyTool;

/**
 *
 * @author VU HONG ANH
 */
public class Menu extends ArrayList<String> {

    public Menu() {
        super();
    }

    public Menu(String[] items) {
        super();
        for (String item : items) {
            this.add(item);
        }
    }

    public int getChoice(String title) {
        System.out.println(title);
        int choice;
        for (int i = 0; i < this.size(); i++) {
            System.out.println("   " + (i + 1) + "-"+ this.get(i));
        }
        System.out.println("   Other for quit.");
        System.out.print("Choose [1..8]: ");
        choice = MyTool.SC.nextInt();
        return choice;
    }
}
