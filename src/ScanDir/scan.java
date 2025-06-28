package ScanDir;

import java.io.File;
import java.util.Scanner;

public class scan {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            userDir dir = new userDir();
            File localDir = dir.userDirConf();
            File sortDir = dir.changeSortDir();
            viewFile files = new viewFile(localDir);
            files.scanFolder();
            scanFilesAndSort sort = new scanFilesAndSort(localDir, sortDir);
            sort.sortDir();
            sort.report();
            System.out.println();
            System.out.println("Хотите отсортировать еще одну папку? (да/нет)");
            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equalsIgnoreCase("да")) {}     // Noncompliant: For a polling cycle
                else if (line.equalsIgnoreCase("нет")) {
                    break;
                }
            }
        }
    }
}
