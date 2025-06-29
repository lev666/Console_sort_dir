package scanDir;

import java.io.File;
import java.util.Scanner;

public class viewFile {
    public void scanFolder(File folderDir, Scanner sc) {
        System.out.println("Хотите просмотреть содержимое директории до сортировки?");
        System.out.println("1 - Да");
        System.out.println("2 - Нет");
        System.out.print("Введите значение: ");
        while (true) {
            if (sc.hasNextByte()) {
                int n = sc.nextByte();
                if (n == 1) {
                    try {
                        File[] filesDir = folderDir.listFiles();
                        System.out.println("Содержимое директории: ");
                        for (File file : filesDir) {
                            System.out.println(file.getName());
                        }
                    } catch (NullPointerException e) {
                        System.err.println("Директория пуста!");
                    }
                } else if (n == 2) {
                    break;
                } else if (n > 2 || n < 0) {
                    System.err.println("Некорректное значение!");
                }
            } else {
                System.err.println("Некорректное значение!");
            }
        }
    }
}
