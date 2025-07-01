package scanDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class scanFilesAndSort {

    DirectoryPaths dir = new DirectoryPaths();
    userDirSel dirSelCheckNest = new userDirSel();
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

    public static void CheckingNestedFolders() {

    }

    public void sortDir(File folderDir, File sortDir) {
        try {
            File[] files = folderDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory() && (dirSelCheckNest.isNestedScan())) {
//                        try (Stream<Path> paths = Files.walk(Paths.get(file.toURI()))) {
//                            System.out.println("paths: " + paths);
//                        }
                        continue;
                    }
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
                } else {
                    System.err.println("Ошибка доступа!");
                }
            } catch (NullPointerException | IOException | InterruptedException e) {
                System.err.println("Директория пуста!");
        }
    }
}
