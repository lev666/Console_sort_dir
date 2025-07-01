package scanDir;

import java.io.File;
import java.util.Scanner;

public class viewFile {
    public void scanFolder(File folderDir, Scanner sc) {
        System.out.println("Хотите просмотреть содержимое директории до сортировки? (по умолчанию \"нет\")");
        System.out.println("1 - Да");
        System.out.println("2 - Нет");
        System.out.print("Введите значение: ");
        while (true) {
            if (sc.hasNextLine()) {
                String str = sc.nextLine();
                if (str.equals("1")) {
                    System.out.println("Выбрано: " + str);
                        File[] filesDir = folderDir.listFiles();
                        System.out.println("Содержимое директории: ");
                        assert filesDir != null;
                        for (File file : filesDir) {
                            System.out.println(file.getName());
                        }
                        break;
                } else if (str.equals("2") || str.isEmpty()) {
                    System.out.print("");
                    System.out.println("Выбрано: " + 2);
                    break;
                }
            }
        }
        System.out.println("");
    }
}
