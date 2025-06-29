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
            files.scanFolder(localDir, sc);
            sort.sortDir(localDir, sortDir);
            sort.report();
            System.out.println();
            System.out.println("Хотите отсортировать еще одну папку? (да/нет)");
            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equalsIgnoreCase("нет")) {
                    break;
                }
            }
        }
    }
}
