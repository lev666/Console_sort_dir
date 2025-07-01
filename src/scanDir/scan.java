package scanDir;

import java.io.File;
import java.util.Scanner;

public class scan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        viewFile files = new viewFile();
        scanFilesAndSort sort = new scanFilesAndSort();
        userDirSel dir = new userDirSel();


        while (true) {
            File localDir = dir.userDirConf(sc);
            if (localDir == null) {
                break;
            }
            File sortDir = dir.changeSortDir(sc);
            dir.NestedScanUser(sc);
            files.scanFolder(localDir, sc);
            sort.sortDir(localDir, sortDir);
            sort.report();
            System.out.println();
            System.out.println("");
            System.out.println("Хотите отсортировать еще одну папку? (По умолчанию \"нет\")");
            System.out.println("1 - Да");
            System.out.println("2 - Нет");
            System.out.print("Введите значение: ");
            String str = sc.nextLine();
            if (str.equals("2") || str.isEmpty()) {
                System.out.print("");
                System.out.println("Выбрано: " + 2);
                break;
            }
        }
    }
}
