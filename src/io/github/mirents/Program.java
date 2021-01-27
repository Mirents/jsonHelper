/*
 * Путь к файлу передается в параметрах в консоли
 */
package io.github.mirents;

import io.github.mirents.counters.CountWords;

public class Program {

    public static void main(String[] args) {
        CountWords countWords;
        if(args.length > 0) {
            countWords = new CountWords(args[0]);
        }
        else {
            // С пустым параметром осуществляется проверка работы
            // программы с файлами из рабочего каталога
            System.out.println("_________Test 1_________");
            // Открытие файла в корне приложения
            countWords = new CountWords("test.txt");
            System.out.println("_________Test 2_________");
            // Открытие пустого файла
            countWords = new CountWords("test1.txt");
            System.out.println("\n_________Test 3_________");
            // Попытка открытия несуществующего файла
            countWords = new CountWords("test2.txt");
        }
    }
}
