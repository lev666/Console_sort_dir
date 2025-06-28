package ScanDir;

import java.io.File;
import java.util.Scanner;


public class userDir {
    private final Scanner sc = new Scanner(System.in);
    private File folderDir = null;
    private int exmCount = 0;
    private File changeSOrtDIr = null;

    private File getValidatedPathFromUser() {
        while (true) {
            System.out.print("Введите путь к директории: ");
            String pathName = sc.nextLine();
            File dirName = new File(pathName);

            if (dirName.isDirectory() && dirName.exists()) {
                folderDir = dirName;
                System.out.println();
                System.out.println("Ваш путь до директории: " + folderDir);
                System.out.println();
                break;
            } else {
                System.err.println("Путь неверный!");
                exmCount++;
                if (exmCount == 3) {
                    System.err.println("Исчерпан лимит попыток! Уточните директорию и попробуйте позднее!");
                    break;
                }
            }
        }

        return folderDir;
    }

    public File userDirConf() {
        return getValidatedPathFromUser();
    }

    public File changeSortDir() {
        System.out.println("Требуется ли отсортировать в другую директорию?");
        System.out.println("1 - Да");
        System.out.println("2 - Нет");
        System.out.print("Введите значение: ");
        if  (sc.hasNextInt()) {
            byte sortDirChange = sc.nextByte();
            if (sortDirChange == 1) {
                return getValidatedPathFromUser();
            } else if (sortDirChange == 2) {
                changeSOrtDIr = folderDir;
            }
        } else {
            System.out.println("Некорректное значение!");
            return folderDir;
        }

        return changeSOrtDIr;
    }
}
