/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;
import java.util.List;
import tools.MyTool;
/**
 *
 * @author VU HONG ANH
 */
public class Config {
    private static final String CONFIG_FILE = "ProductData/config.txt";
    private String productFile;
    
    public Config(){
        readData();
    }
    
    private void readData(){
        List<String> lines = MyTool.readLinesFromFile(CONFIG_FILE);
        for(String line: lines){
            line = line.toUpperCase();
            String[] parts = line.split(":");
            if(line.indexOf("PRODUC") >= 0){
                productFile = "ProductData/" + parts[1].trim();
            }
        }
    }
    
    public String getProductFile(){
        return productFile;
    }
}
