/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;
import java.sql.Date;
import java.text.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author VU HONG ANH
 */
public class MyTool {
    public static Scanner SC = new Scanner(System.in);
    
    public static boolean validStr(String str, String regEx){
        if(str.matches(regEx)){
            return true;
        } else return false;
    }
    
    public static boolean validPassword(String str, int minLen){
        if(str.length() < minLen){
            return false;
        }
        return str.matches(".*[a-zA-Z]+.*") && str.matches(".*[\\d]+.*") && str.matches(".*[\\W]+.*");
    }
    
    public static Date parseDate(String dateStr, String dateFormat){
        SimpleDateFormat dF = (SimpleDateFormat)SimpleDateFormat.getInstance();
        dF.applyPattern(dateFormat);
        try{
            long t = dF.parse(dateStr).getTime();
            return new Date(t);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    
    public static String dataToStr(Date date, String dateFormat){
        SimpleDateFormat df = (SimpleDateFormat)SimpleDateFormat.getInstance();
        df.applyPattern(dateFormat);
        String d = df.format(date);
        return d;
    }
    
    public static boolean parseBool(String boolStr){
        char c = boolStr.trim().toUpperCase().charAt(0);
        return (c == '1'|| c == 'Y' || c == 'T');
    }
    
    public static String readNonBlank(String message){
        String input = "";
        do{
            System.out.print(message);
            input = SC.nextLine().trim();
        }
        while (input.isEmpty());
        return input;
    }
    
    public static String readPattern(String message, String pattern){
        String input;
        boolean valid;
        Scanner sc = new Scanner(System.in);
        System.out.print(message);
        input = sc.nextLine().trim();
        if(input.isEmpty()){
            System.out.println("Invalid input, try again");
            readPattern(message, pattern);
        } else valid = validStr(input, pattern);
        if(valid = false){
            readPattern(message, pattern);
        }
        return input;
        /*do{
            System.out.print(message);
            input = SC.nextLine().trim();
            if(input.isEmpty()){
                System.out.println("Invalid input. Try again.");
                readPattern(message, pattern);
            }
            valid = validStr(input, pattern);
        } while(!valid);
        return input;*/
    }
    
    public static boolean readBool(String message){
        String input;
        System.out.print(message + "[1/0-Y/N-T/F]: ");
        input = SC.nextLine().trim();
        if(input.isEmpty()) return false;
        char c = Character.toUpperCase(input.charAt(0));
        return (c == '1' || c == 'Y' || c == 'T');
    }
     
    public static List<String> readLinesFromFile(String filename){
        List<String> list = new ArrayList<String>();
        try{
            File f = new File(filename);
            if(!f.exists()){
                return list;
            }
            FileReader fr = new FileReader(f);
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
        }
        catch(Exception e){
            System.out.println(e);
        }
        return list;
    }
    
    public static void writeFile(String filename, List list){
        if(list.size() == 0){
            System.out.println("Empty list.");
            return;
        }
        try{
            File f = new File(filename);
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            for(int i = 0; i < list.size(); i++){
                pw.println(list.get(i));
            }
            pw.close();
            fw.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    /*public static void main(String[]args){
        System.out.println("Tests with phone numbers:");
        System.out.println(validStr("012345678", "\\d{9}|\\d{11}"));
        System.out.println(validStr("01234567891", "\\d{9}|\\d{11}"));
        System.out.println(validStr("12345678", "\\d{9}|\\d{11}"));
        
        System.out.println(validPassword("qwerty", 8));
        System.out.println(validPassword("qwertyABC", 8));
        System.out.println(validPassword("12345678", 8));
        System.out.println(validPassword("qbc123456", 8));
        System.out.println(validPassword("qbc!123456", 8));
        
        System.out.println("Tests with IDs:");
        System.out.println(validStr("A0001", "D\\d{3}"));
        System.out.println(validStr("10001", "D\\d{3}"));
        System.out.println(validStr("D0001", "D\\d{3}"));
        System.out.println(validStr("D101", "D\\d{3}"));
        
        Date d = parseDate("2022:12:07", "yyyy:MM:dd");
        System.out.println(d);
        System.out.println(dataToStr(d, "dd/MM/yyyy"));
        d = parseDate("12/07/2022", "MM/dd/yyyy");
        System.out.println(d);
        d = parseDate("2022/07/12", "yyyy/dd/MM");
        System.out.println(d);
        d = parseDate("2000/29/02", "yyyy/dd/MM");
        System.out.println(d);
        d = parseDate("2000/30/02", "yyyy/dd/MM");
        System.out.println(d);
        d = parseDate("2000/40/16", "yyyy/dd/MM");
        System.out.println(d);
        
        String input = readNonBlank("Input a non-blank string");
        System.out.println(input);
        input = readPattern("Phone 9/11 digits", "\\d{9}|\\d{11}");
        System.out.println(input);
        input = readPattern("ID- format X00000", "X\\d{5}");
        System.out.println(input);
        boolean b = readBool("Input boolean");
        System.out.println(b);
    }*/
}
