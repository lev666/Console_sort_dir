package scanDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class scanFilesAndSort {

    private static final Map<String, String> formatFiles = new HashMap<>();
    private static final Map<String, Integer> reportArray = new HashMap<>();
    private static final String otherStr = "other";
    private static final String errorStr = "error";

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
        int image = reportArray.get("image") == null ? 0 : reportArray.get("image");
        int video = reportArray.get("video") == null ? 0 : reportArray.get("video");
        int audio = reportArray.get("audio") == null ? 0 : reportArray.get("audio");
        int other = reportArray.get(otherStr) == null ? 0 : reportArray.get(otherStr);
        int error = reportArray.get(errorStr) == null ? 0 : reportArray.get(errorStr);
        System.out.printf("Сортировка завершена. Перемещено: %d Изображений, %d Видео, %d Аудио, %d прочее. Не удалось перместить: %d файла", image, video, audio, other, error);
    }

    private static void otherSort(File sortDir, String fileName, File file) throws IOException {
        File distFold = new File(sortDir, otherStr);
        distFold.mkdirs();
        Path distDir = new File(distFold, fileName).toPath();
        Files.move(file.toPath(), distDir, StandardCopyOption.REPLACE_EXISTING);
        reportArray.put(otherStr, reportArray.getOrDefault(otherStr, 0) + 1);
    }

    public void sort(File folderDir, File sortDir, boolean shouldScanRecursively) {
        List<File> allFilesToProcess = new ArrayList<>();

        collectFilesRecursively(folderDir, allFilesToProcess, shouldScanRecursively);

        sortCollectedFiles(allFilesToProcess, sortDir);
    }

    public void collectFilesRecursively(File folderDir,List<File> allFilesToProcess, boolean shouldScanRecursively) {
        File[] files = folderDir.listFiles();

        if (files == null) { System.err.println("Ошибка доступа!"); return; }

        for (File file : files) {
            if (file.isDirectory() && shouldScanRecursively) {
                collectFilesRecursively(file, allFilesToProcess, shouldScanRecursively);
            } else if (file.isFile()) {
                allFilesToProcess.add(file.getAbsoluteFile());
            }
        }
    }

    public void sortCollectedFiles(List<File> filesToProcess, File sortDir) {
        try {
                for (File file : filesToProcess) {
                    String fileName = file.getName();
                    try {
                        int firstIndex = fileName.lastIndexOf(".");
                        String ext = fileName.substring(firstIndex + 1);
                        String mapRule = formatFiles.get(ext);
                        if (firstIndex == -1) {
                            otherSort(sortDir, fileName, file);
                            continue;
                        }
                        if (mapRule != null && firstIndex > 0) {
                            File distFold = new File(sortDir, mapRule);
                            distFold.mkdirs();
                            Path distDir = new File(distFold, fileName).toPath();
                            Files.move(file.toPath(), distDir, StandardCopyOption.REPLACE_EXISTING);
                            reportArray.put(mapRule, reportArray.getOrDefault(mapRule, 0) + 1);
                        } else {
                            otherSort(sortDir, fileName, file);
                        }
                    } catch (IOException e) {
                            reportArray.put(errorStr, reportArray.getOrDefault(errorStr, 0) + 1);
                            Thread.sleep(100);
                            System.err.print("Не удалось переместить файл " + file.getName() + ": " + e.getMessage());
                        }
                    }
            } catch (NullPointerException | InterruptedException e) {
                System.err.println("Директория пуста!");
        }
    }
}
