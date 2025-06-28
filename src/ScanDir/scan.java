package ScanDir;

import java.io.File;

public class scan {
    public static void main(String[] args) {
        userDir dir = new userDir();
        File localDir = dir.userDirConf();
        File sortDir = dir.changeSortDir();
        viewFile files = new viewFile(localDir);
        files.scanFolder();
        scanFilesAndSort sort = new scanFilesAndSort(localDir, sortDir);
        sort.sortDir();
    }
}
