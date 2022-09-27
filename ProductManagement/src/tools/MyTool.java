/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 *
 * @author VU HONG ANH
 */
public class MyTool {
    public static Scanner SC = new Scanner(System.in);
    
    public static List<String> readLinesFromFile(String s){
        List<String> list = new ArrayList<String>();
        try{
            File f = new File(s);
            if(!f.exists()){
                return list;
            }
            FileReader fr = new FileReader(s);
            BufferedReader bf = new BufferedReader(fr);
            String details;
            while((details = bf.readLine()) != null){
                details.trim();
                if(!details.isEmpty()){
                    list.add(details);
                }
            }
            bf.close();
            fr.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return list;
    }
    
    public static String readNonBlank(String s){
        String input = "";
        SC = new Scanner(System.in);
        do{
            System.out.print(s);
            input = SC.nextLine().trim().toUpperCase();
        } while (input.isEmpty());
        return input;
    }
    
    public static String readPattern(String message, String pattern){
        String input;
        boolean valid;
        SC = new Scanner(System.in);
        do{
            System.out.print(message);
            input = SC.nextLine().trim().toUpperCase();
            if(input.isEmpty()){
                System.out.println("Input must not be blank, try again");
                valid = false;
            } else {
                valid = validStr(input, pattern);
            }
        }while(!valid);
        return input;
    }
    
    public static boolean validStr(String str, String regEx){
        if(str.matches(regEx)){
            return true;
        } else {
            System.out.println("Invalid input, try again");
        }
        return false;
    }
    
    
}
