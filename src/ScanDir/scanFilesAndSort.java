package ScanDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class scanFilesAndSort {

    private final File folderDir;
    private final File sortDir;
    private static final Map<String, String> formatFiles = new HashMap<>();
    private final int[] reportArray = new int[5];

    static {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("conf/config.properties"));

            for (String key : prop.stringPropertyNames()) {
                formatFiles.put(key, prop.getProperty(key));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void report() {
        System.out.printf("Сортировка завершена. Перемещено: %d Изображений, %d Видео, %d Аудио, %d прочее. Не удалось перместить: %d файла", reportArray[0], reportArray[1], reportArray[2], reportArray[4], reportArray[3]);
    }

    public scanFilesAndSort(File folderDir, File sortDir) {
        this.folderDir = folderDir;
        this.sortDir = sortDir;
    }

    public void sortDir() {
        try {
            File[] files = folderDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        continue;
                    }
                    String fileName = file.getName();
                    try {
                        int firstIndex = fileName.lastIndexOf(".");
                        String ext = fileName.substring(firstIndex + 1);
                        if (formatFiles.get(ext) != null) {
                            File distFold = new File(sortDir, formatFiles.get(ext));
                            distFold.mkdirs();
                            Path distDir = new File(distFold, file.getName()).toPath();
                            Files.move(file.toPath(), distDir, StandardCopyOption.REPLACE_EXISTING);
                            if (formatFiles.get(ext).equals("image")) {
                                reportArray[0] += 1;
                            } else if (formatFiles.get(ext).equals("video")) {
                                reportArray[1] += 1;
                            } else if (formatFiles.get(ext).equals("audio")) {
                                reportArray[2] += 1;
                            }
                        } else {
                            File distFold = new File(sortDir, "other");
                            distFold.mkdirs();
                            Path distDir = new File(distFold, file.getName()).toPath();
                            Files.move(file.toPath(), distDir, StandardCopyOption.REPLACE_EXISTING);
                            reportArray[4] += 1;
                        }
                    } catch (IOException e) {
                            reportArray[3] += 1;
                            System.err.println("Не удалось переместить файл " + file.getName() + ": " + e.getMessage());
                        }
                    }
                } else {
                    System.err.println("Ошибка доступа!");
                }
            } catch (NullPointerException e) {
                System.err.println("Директория пуста!");
        }
    }
}
