package io.github.mirents.counters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class CountWords {
    Map<String, Integer> map = new TreeMap<>();
    
    public CountWords(String fileName) {
        if(openFileToMap(fileName)) {
            printTableStats("File: " + (new File(fileName)).getName()
                    + "\nStatistics:", 0);
            printTableStats("Maximum repetition of words:", getMaxValue());
        }
    }
    
    private boolean openFileToMap(String fileName) {
        try {
            String s = "";
            
            File file = new File(fileName);
            Scanner sc = new Scanner(file);

            // Разделение файла на слова с удалением лишних символов
            sc.useDelimiter("[\\p{Punct}\\s]+");

            while(sc.hasNext()) {
                s = sc.next().toLowerCase();

                if(map.containsKey(s)) {
                    map.put(s, map.get(s)+1);
                } else {
                    map.put(s, 1);
                }
            }
            
            if(!map.isEmpty())
                return true;
            else {
                System.out.println("File: " + file.getName() + " is Empty");
                return false;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File: " + fileName + " not found");
            return false;
        }
    }
    
    private int getMaxValue() {
        int max = 0;
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            if(entry.getValue() > max)
                max = entry.getValue();
        }
        return max;
    }
    
    private void printTableStats(String message, int max) {
        System.out.println(message);
        System.out.println("  Words           Count");
        System.out.println("-------------------------");
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            if(max > 0) {
                if(entry.getValue() == max)
                    printLine(entry.getKey(), entry.getValue());
            } else
                printLine(entry.getKey(), entry.getValue());
        }
        System.out.println("-------------------------\n");
    }
    
    private void printLine(String key, int value) {
        System.out.printf("%-15s | %3d%n", key, value);
    }
}
