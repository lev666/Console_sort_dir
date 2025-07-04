package scanDir;

import java.io.File;
import java.util.Scanner;


public class userDirSel {
    private boolean NestedScan = false;
    DirectoryPaths defFolder = new DirectoryPaths();

    private File getValidatedPathFromUser(Scanner sc) {
        int exmCount = 0;
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
        System.out.println("Требуется ли отсортировать в другую директорию? (по умолчанию \"нет\")");
        System.out.println("1 - Да");
        System.out.println("2 - Нет. Сделать в " + defFolder.getLocalDir());
        System.out.print("Введите значение: ");
        while (true) {
            if (sc.hasNextLine()) {
                String str = sc.nextLine();
                if (str.equals("1")) {
                    System.out.println("Выбрано: " + str);
                    return userDirConf(sc);
                } else if (str.equals("2") || str.isEmpty()) {
                    System.out.println("Выбрано: " + 2);
                    defFolder.setSortDir(defFolder.getLocalDir());
                    break;
                }
            }
        }
        System.out.println("");
        return defFolder.getSortDir();
    }

    public void NestedScanUser(Scanner sc) {
        System.out.println("Включить сортировку для вложенных папок? (по умолчанию \"да\")?");
        System.out.println("1 - да");
        System.out.println("2 - нет");
        System.out.print("Введите значение: ");
        while (true) {
            if (sc.hasNextLine()) {
                String str = sc.nextLine();
                if (str.equals("1") || str.isEmpty()) {
                    System.out.println("Выбрано: " + 1);
                    NestedScan = true;
                    break;
                } else if (str.equals("2")) {
                    System.out.println("Выбрано: " + str);
                    NestedScan = false;
                    break;
                }
            }
        }
        System.out.println("");
    }

    public boolean isNestedScan() {
        return NestedScan;
    }
}
