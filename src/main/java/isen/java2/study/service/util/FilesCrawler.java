package isen.java2.study.service.util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class util for crawling through files
 * Created by Matthieu on 11/01/2016.
 */
public class FilesCrawler {
    /**
     * Return a list of the Path of the different file within a specified path
     * @param path the path in which the files are located
     * @return the list of Path containing the different files
     */
    public static List<Path> getFiles(String path) {
        Path root = Paths.get(path);
        ArrayList<Path> paths = new ArrayList<>();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(root)) {
            for (Path p : files) {
                paths.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }
}
