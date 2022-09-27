/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import tools.MyTool;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author VU HONG ANH
 */
public class ProductList extends ArrayList<Product> {

    private String dataFile = "";

    public void initWithFile() {
        Config c = new Config();
        dataFile = c.getProductFile();
        loadProductFromFile();
    }

    public void loadProductFromFile() {
        List<String> str = MyTool.readLinesFromFile(dataFile);
        for (String s : str) {
            Product p = new Product(s);
            this.add(p);
        }
    }

    public void printAllProduct() {
        int choice = 0;
        MyTool.SC = new Scanner(System.in);
        do {
            System.out.println("-------------------------------------[PRINT MENU]--------------------------------------");
            System.out.println("   1-Print list ascendingly");
            System.out.println("   2-Print list descendingly");
            System.out.println("   Others-Return");
            System.out.print("Choose [1..2]: ");
            choice = MyTool.SC.nextInt();

            switch (choice) {
                case 1:
                    sub1();
                    break;
                case 2:
                    sub2();
                    break;
            }
        } while (choice > 0 && choice <= 2);
        /*if (this.isEmpty()) {
            System.out.println("Empty list!");
        } else {
            System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
            System.out.println("|    ID    |            NAME            |   PRICE   |  QUANTITY  |       STATUS       |");
            System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
            for (Product p : this) {
                System.out.format("|%-10s|%-28s|%-11.2f|%-12d|%-20s|\n", p.getID(), p.getName(), p.getPrice(), p.getQuantity(), p.getStatus());
            }
            System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
        }*/
    }

    public void addProduct() {
        String ID, name, status;
        double price;
        int quantity;
        boolean valid = false;
        do {
            ID = MyTool.readPattern("Enter product ID: ", Product.ID_FORMAT);
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getID().equals(ID)) {
                    System.out.println("Duplicated ID, try again");
                    valid = false;
                } else {
                    valid = true;
                }
            }
        } while (valid = false);
        MyTool.SC = new Scanner(System.in);
        do {
            valid = false;
            boolean spaceDetect = false;
            System.out.print("Enter product name: ");
            name = MyTool.SC.nextLine().toUpperCase();
            char[] ch = name.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                if (ch[i] == ' ') {
                    spaceDetect = true;
                }
            }
            if (name.length() < 5 || spaceDetect == true || (name.length() < 5 && spaceDetect == true)) {
                System.out.println("Input must be at least 5 character and must not contain space(s)");
                valid = false;
            } else {
                valid = true;
            }
        } while (valid = false);
        do {
            valid = false;
            System.out.print("Enter price of product: ");
            price = MyTool.SC.nextDouble();
            if (!(price >= 0 && price <= 10000)) {
                System.out.println("Invalid value, try again");
                valid = false;
            } else {
                valid = true;
            }
        } while (valid = false);
        do {
            valid = false;
            System.out.print("Enter quantity of product: ");
            quantity = MyTool.SC.nextInt();
            if (!(quantity >= 0 && quantity <= 10000)) {
                System.out.println("Invalid value, try again");
                valid = false;
            } else {
                valid = true;
            }
        } while (valid = false);
        do {
            valid = false;
            status = MyTool.readNonBlank("Enter availability: ").toUpperCase();
            if (!(status.equals("AVAILABLE") || status.equals("NOT-AVAILABLE"))) {
                System.out.println("Invalid input, try again");
                valid = false;
            } else {
                valid = true;
            }
        } while (valid = false);
        Product p = new Product(ID, name, price, quantity, status);
        this.add(p);
        System.out.println("New product has been added");
    }

    public void checkExistProd() {
        int choice = 0;
        MyTool.SC = new Scanner(System.in);
        System.out.println("------------------------------------[CHECK EXIST MENU]---------------------------------");
        System.out.println("   1-Check by ID");
        System.out.println("   2-Check by name");
        System.out.println("   Others-Return");
        System.out.print("Choose [1..2]");
        choice = MyTool.SC.nextInt();
        switch (choice) {
            case 1:
                checkID();
                break;
            case 2:
                checkName();
                break;
        }
    }

    public void checkID() {
        List<Product> tempList = new ArrayList<Product>();
        String inputID = MyTool.readNonBlank("Enter ID: ").toUpperCase();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID().equals(inputID)) {
                tempList.add(this.get(i));
            }
        }
        if (tempList.size() == 0) {
            System.out.println("Please enter a valid product ID.");
            checkID();
        } else {
            System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
            System.out.println("|    ID    |            NAME            |   PRICE   |  QUANTITY  |       STATUS       |");
            System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
            for (Product p : tempList) {
                System.out.format("|%-10s|%-28s|%-11.2f|%-12d|%-20s|\n", p.getID(), p.getName(), p.getPrice(), p.getQuantity(), p.getStatus());
            }
            System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
        }
    }

    public void checkName() {
        List<Product> tempList = new ArrayList<Product>();
        MyTool.SC = new Scanner(System.in);
        System.out.print("Enter name: ");
        String inputName = MyTool.SC.nextLine().toUpperCase();
        boolean spaceDetect = false;
        char[] ch = inputName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == ' ') {
                spaceDetect = true;
            }
        }
        if (inputName.length() < 5 || spaceDetect == true || (inputName.length() < 5 && spaceDetect == true)) {
            System.out.println("Input must be at least 5 character and must not contain space(s). Please enter a valid product name.");
            checkName();
        } else {
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getName().equals(inputName)) {
                    tempList.add(this.get(i));
                }
            }
            if (tempList.size() == 0) {
                System.out.println("Not found!");
            } else {
                System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
                System.out.println("|    ID    |            NAME            |   PRICE   |  QUANTITY  |       STATUS       |");
                System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
                for (Product p : tempList) {
                    System.out.format("|%-10s|%-28s|%-11.2f|%-12d|%-20s|\n", p.getID(), p.getName(), p.getPrice(), p.getQuantity(), p.getStatus());
                }
                System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
            }
        }
    }

    public void searchProduct() {
        int choice = 0;
        MyTool.SC = new Scanner(System.in);
        do {
            System.out.println("--------------------------------------[SEARCH MENU]------------------------------------");
            System.out.println("   1-Search by ID");
            System.out.println("   2-Search by name");
            System.out.println("   3-Search by price");
            System.out.println("   4-Search by quantity");
            System.out.println("   5-Search by availability");
            System.out.println("   Others-Return");
            System.out.print("Choose [1..5]: ");
            choice = MyTool.SC.nextInt();
            switch (choice) {
                case 1:
                    search(1);
                    break;
                case 2:
                    search(2);
                    break;
                case 3:
                    search(3);
                    break;
                case 4:
                    search(4);
                    break;
                case 5:
                    search(5);
                    break;
            }
        } while (choice > 0 && choice <= 5);
    }

    public void search(int swap) {
        List<Product> tempList = new ArrayList<Product>();
        MyTool.SC = new Scanner(System.in);
        if (swap == 1) {
            String inputID = MyTool.readNonBlank("Enter ID: ").toUpperCase();
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getID().contains(inputID)) {
                    tempList.add(this.get(i));
                }
            }
            printResult(tempList);
        }
        if (swap == 2) {
            String inputName = MyTool.readNonBlank("Enter name: ").toUpperCase();
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getName().contains(inputName)) {
                    tempList.add(this.get(i));
                }
            }
            printResult(tempList);
        }
        if (swap == 3) {
            System.out.print("Enter price: ");
            double inputPrice = MyTool.SC.nextDouble();
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getPrice() == inputPrice) {
                    tempList.add(this.get(i));
                }
            }
            printResult(tempList);
        }
        if (swap == 4) {
            System.out.print("Enter quantity: ");
            int inputQuantity = MyTool.SC.nextInt();
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getQuantity() == inputQuantity) {
                    tempList.add(this.get(i));
                }
            }
            printResult(tempList);
        }
        if (swap == 5) {
            String inputAvailability = MyTool.readNonBlank("Enter availability: ").toUpperCase();
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).getStatus().equals(inputAvailability)) {
                    tempList.add(this.get(i));
                }
            }
            printResult(tempList);
        }
    }

    public void printResult(List<Product> tempList) {
        if (tempList.size() == 0) {
            System.out.println("Not found!");
        } else {
            System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
            System.out.println("|    ID    |            NAME            |   PRICE   |  QUANTITY  |       STATUS       |");
            System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
            for (Product p : tempList) {
                System.out.format("|%-10s|%-28s|%-11.2f|%-12d|%-20s|\n", p.getID(), p.getName(), p.getPrice(), p.getQuantity(), p.getStatus());
            }
            System.out.println("+----------+----------------------------+-----------+------------+--------------------+");
        }
    }

    public void sub1() {
        int subChoice = 0;
        do {
            System.out.println("-----------------------------------[PRINT ASCENDINGLY]---------------------------------");
            System.out.println("   1-ID");
            System.out.println("   2-Name");
            System.out.println("   3-Price");
            System.out.println("   Other-Return");
            System.out.print("Choice [1..4]: ");
            subChoice = MyTool.SC.nextInt();

            switch (subChoice) {
                case 1:
                    ascending(1);
                    break;
                case 2:
                    ascending(2);
                    break;
                case 3:
                    ascending(3);
                    break;

            }
        } while (subChoice >= 1 && subChoice <= 3);
    }

    public void sub2() {
        int subChoice2 = 0;
        do {
            System.out.println("-----------------------------------[PRINT DESCENDINGLY]--------------------------------");
            System.out.println("   1-ID");
            System.out.println("   2-Name");
            System.out.println("   3-Price");
            System.out.println("   Other-Return");
            System.out.print("Choice [1..3]: ");
            subChoice2 = MyTool.SC.nextInt();
            switch (subChoice2) {
                case 1:
                    descending(1);
                    break;
                case 2:
                    descending(2);
                    break;
                case 3:
                    descending(3);
                    break;
            }
        } while (subChoice2 >= 1 && subChoice2 <= 3);
    }

    public void ascending(int swap) {
        List<String> tempList = new ArrayList<String>();
        List<Double> tempListDouble = new ArrayList<Double>();
        if (swap == 1) {
            for (int i = 0; i < this.size(); i++) {
                tempList.add(this.get(i).getID());
            }
            Collections.sort(tempList, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return extractInt(o1) - extractInt(o2);
                }

                int extractInt(String s) {
                    String num = s.replaceAll("\\D", "");
                    // return 0 if no digits found
                    return num.isEmpty() ? 0 : Integer.parseInt(num);
                }
            });
            List<Product> returnList = new ArrayList<Product>();
            for (int i = 0; i < tempList.size(); i++) {
                for (int j = 0; j < this.size(); j++) {
                    if (this.get(j).getID().equals(tempList.get(i))) {
                        returnList.add(this.get(j));
                    }
                }
            }
            printResult(returnList);
            this.clear();
            this.addAll(returnList);
        }
        if (swap == 2) {
            for (int i = 0; i < this.size(); i++) {
                tempList.add(this.get(i).getName());
            }
            Collections.sort(tempList);
            List<Product> returnList = new ArrayList<Product>();
            for (int i = 0; i < tempList.size(); i++) {
                for (int j = 0; j < this.size(); j++) {
                    if (this.get(j).getName().equals(tempList.get(i))) {
                        returnList.add(this.get(j));
                    }
                }
            }
            printResult(returnList);
            this.clear();
            this.addAll(returnList);
        }
        if (swap == 3) {
            for (int i = 0; i < this.size(); i++) {
                tempListDouble.add(this.get(i).getPrice());
            }
            Collections.sort(tempListDouble);
            List<Product> returnList = new ArrayList<Product>();
            for (int i = 0; i < tempListDouble.size(); i++) {
                for (int j = 0; j < this.size(); j++) {
                    if (this.get(j).getPrice() == tempListDouble.get(i)) {
                        returnList.add(this.get(j));
                    }
                }
            }
            printResult(returnList);
            this.clear();
            this.addAll(returnList);
        }
    }

    public void descending(int swap) {
        List<String> tempList = new ArrayList<String>();
        List<Double> tempListDouble = new ArrayList<Double>();
        if (swap == 1) {
            for (int i = 0; i < this.size(); i++) {
                tempList.add(this.get(i).getID());
            }
            Collections.sort(tempList, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return extractInt(o2) - extractInt(o1);
                }

                int extractInt(String s) {
                    String num = s.replaceAll("\\D", "");
                    // return 0 if no digits found
                    return num.isEmpty() ? 0 : Integer.parseInt(num);
                }
            });
            List<Product> returnList = new ArrayList<Product>();
            for (int i = 0; i < tempList.size(); i++) {
                for (int j = 0; j < this.size(); j++) {
                    if (this.get(j).getID().equals(tempList.get(i))) {
                        returnList.add(this.get(j));
                    }
                }
            }
            printResult(returnList);
            this.clear();
            this.addAll(returnList);
        }
        if (swap == 2) {
            for (int i = 0; i < this.size(); i++) {
                tempList.add(this.get(i).getName());
            }
            Collections.sort(tempList, Collections.reverseOrder());
            List<Product> returnList = new ArrayList<Product>();
            for (int i = 0; i < tempList.size(); i++) {
                for (int j = 0; j < this.size(); j++) {
                    if (this.get(j).getName().equals(tempList.get(i))) {
                        returnList.add(this.get(j));
                    }
                }
            }
            printResult(returnList);
            this.clear();
            this.addAll(returnList);
        }
        if (swap == 3) {
            for (int i = 0; i < this.size(); i++) {
                tempListDouble.add(this.get(i).getPrice());
            }
            Collections.sort(tempListDouble, Collections.reverseOrder());
            List<Product> returnList = new ArrayList<Product>();
            for (int i = 0; i < tempListDouble.size(); i++) {
                for (int j = 0; j < this.size(); j++) {
                    if (this.get(j).getPrice() == tempListDouble.get(i)) {
                        returnList.add(this.get(j));
                    }
                }
            }
            printResult(returnList);
            this.clear();
            this.addAll(returnList);
        }
    }

    public void updateProduct() {

    }
}
