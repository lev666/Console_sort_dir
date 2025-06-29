package scanDir;

import java.io.File;
import java.util.Scanner;


public class userDirSel {
    private int exmCount = 0;
    DirectoryPaths defFolder = new DirectoryPaths();

    private File getValidatedPathFromUser(Scanner sc) {
        while (true) {
            System.out.println("Введите путь к директории: ");
            String pathName = sc.nextLine();
            File dirName = new File(pathName);

            if (dirName.isDirectory() && dirName.exists()) {
                defFolder.setLocalDir(dirName);
                System.out.println();
                System.out.println("Ваш путь до директории: " + defFolder.getLocalDir());
                System.out.println();
                break;
            } else {
                System.err.println("Путь неверный!");
                exmCount++;
                if (exmCount == 3) {
                    System.err.println("Исчерпан лимит попыток! Уточните директорию и попробуйте позднее!");
                    return null;
                }
            }
        }

        return defFolder.getLocalDir();
    }

    public File userDirConf(Scanner sc) {
        return getValidatedPathFromUser(sc);
    }

    public File changeSortDir(Scanner sc) {
        System.out.println("Требуется ли отсортировать в другую директорию?");
        System.out.println("1 - Да");
        System.out.println("2 - Нет. Сделать в " + defFolder.getLocalDir());
        System.out.print("Введите значение: ");
        if  (sc.hasNextInt()) {
            byte sortDirChange = sc.nextByte();
            sc.nextLine();
            if (sortDirChange == 1) {
                return userDirConf(sc);
            } else if (sortDirChange == 2) {
                defFolder.setSortDir(defFolder.getLocalDir());
            }
        } else {
            System.out.println("Некорректное значение!");
            return defFolder.getLocalDir();
        }

        return defFolder.getSortDir();
    }
}
