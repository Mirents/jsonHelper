/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package countwords;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class CountWords {
    Map<String, Integer> map = new TreeMap<>();
    
    CountWords(String fileName) {
        String s = "";
        
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file, "utf-8");
            
            // \. - Точка
            // \t - Табулятор
            // \s - Пробел
            // [\\.\\t\\s]
            // [\\W+]
            // [\\.\\t\\s\\,\\/\\+\\*\\-]
            sc.useDelimiter("[\\."
                    + "\\t"
                    + "\\s"
                    + "\\,"
                    + "\\/"
                    + "\\+"
                    + "\\*"
                    + "\\-]");

            while(sc.hasNext()) {
                s = sc.next().toLowerCase();

                if(s.length() > 0){
                    if(map.containsKey(s)) {
                        map.put(s, map.get(s)+1);
                    } else {
                        map.put(s, 1);
                    }

                    if(s == null)
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        int max = 0;
        System.out.println("Statistic words:");
        System.out.println("  Words           Count");
        System.out.println("-------------------------");
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            System.out.printf("%-15s | %3d%n", entry.getKey(), entry.getValue());
            if(entry.getValue() > max)
                max = entry.getValue();
        }
        System.out.println("-------------------------\n");
        
        System.out.println("Maximum repetition of words:");
        System.out.println("  Words           Count");
        System.out.println("-------------------------");
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            if(entry.getValue() == max)
                System.out.printf("%-15s | %3d%n", entry.getKey(), entry.getValue());
        }
        System.out.println("-------------------------\n");
    }
}
