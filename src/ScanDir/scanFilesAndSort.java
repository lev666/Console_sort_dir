package ScanDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class scanFilesAndSort {

    private File folderDir;
    private File sortDir;

    public scanFilesAndSort(File folderDir, File sortDir) {
        this.folderDir = folderDir;
        this.sortDir = sortDir;
    }

    public void sortDir() {
        try {
            File[] files = folderDir.listFiles();
            File image = new File(sortDir, "image");
            File video = new File(sortDir, "video");
            File audio = new File(sortDir, "audio");
            if (image.mkdirs() && video.mkdirs() && audio.mkdirs()) {
                if (files != null) {
                    for (File file : files) {
                        if (file.isDirectory()) {
                            continue;
                        }
                        String fileName = file.getName();
                        try {
                            if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
                                Path distDir = new File(image, file.getName()).toPath();
                                Files.move(file.toPath(), distDir, StandardCopyOption.REPLACE_EXISTING);
                            }
                            if (fileName.endsWith(".mp4")) {
                                Path distDir = new File(video, file.getName()).toPath();
                                Files.move(file.toPath(), distDir, StandardCopyOption.REPLACE_EXISTING);
                            }
                            if (fileName.endsWith(".mp3")) {
                                Path distDir = new File(audio, file.getName()).toPath();
                                Files.move(file.toPath(), distDir, StandardCopyOption.REPLACE_EXISTING);
                            }
                        } catch (IOException e) {
                            System.err.println("Не удалось переместить файл " + file.getName() + ": " + e.getMessage());
                        }
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
