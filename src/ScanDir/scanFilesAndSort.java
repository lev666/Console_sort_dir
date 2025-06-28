package ScanDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class scanFilesAndSort {

    private File folderDir;
    private File sortDir;
    private static final Map<String, String> formatFiles = new HashMap<>();
    private int[] Report = new int[4];

    static {
        formatFiles.put("jpg", "image");
        formatFiles.put("jpeg", "image");
        formatFiles.put("png", "image");
        formatFiles.put("gif", "image");
        formatFiles.put("png", "image");
        formatFiles.put("webp", "image");

        formatFiles.put("mp4", "video");
        formatFiles.put("avi", "video");
        formatFiles.put("mkv", "video");
        formatFiles.put("mov", "video");
        formatFiles.put("wmv", "video");
        formatFiles.put("flv", "video");
        formatFiles.put("webm", "video");
        formatFiles.put("avchd", "video");
        formatFiles.put("obb", "video");

        formatFiles.put("mp3", "audio");
        formatFiles.put("wav", "audio");
        formatFiles.put("flac", "audio");
        formatFiles.put("wma", "audio");
        formatFiles.put("aif", "audio");
    }

    public void report() {
        System.out.printf("Сортировка завершена. Перемещено: %d Изображений, %d Видео, %d аудио. Не удалось перместить: %d файла", Report[0], Report[1], Report[2], Report[3]);
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
                        File distFold = new File(sortDir, formatFiles.get(ext));
                        distFold.mkdirs();
                        Path distDir = new File(distFold, file.getName()).toPath();
                        Files.move(file.toPath(), distDir, StandardCopyOption.REPLACE_EXISTING);
                        if (formatFiles.get(ext).equals("image")) {
                            Report[0] += 1;
                        }  else if (formatFiles.get(ext).equals("video")) {
                            Report[1] += 1;
                        } else if (formatFiles.get(ext).equals("audio")) {
                            Report[2] += 1;
                        }
                    } catch (IOException e) {
                            Report[3] += 1;
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
