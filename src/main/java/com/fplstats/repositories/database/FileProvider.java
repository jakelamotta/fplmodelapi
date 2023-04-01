package com.fplstats.repositories.database;

import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.services.understat.UnderstatsProvider;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;

public class FileProvider {

    private static String BASE_PATH = "data" + File.separator;

    public static void WriteFileContent(String data, String dir, String filename) throws IOException {

        if (!Files.exists(Path.of(BASE_PATH + dir))){
            Files.createDirectories(Path.of(BASE_PATH + dir));
        }
        Path path = Path.of(BASE_PATH,dir,filename);
        if (!Files.exists(path)){
            Files.createFile(path);
        }


        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()))){


            bufferedWriter.write(data);
        }
    }

    public static String ReadFileContent(String fileName){
        return ReadFileContent(fileName, false);
    }

    public static String ReadFileContent(String filename, boolean absolutePath) {
        String path;
        if (absolutePath){
            path = filename;
        }
        else{
            path = BASE_PATH + filename;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            return bufferedReader.lines().reduce("", (s,t) -> s+t);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean DeleteItem(String path) throws IOException {

        boolean result = true;

        FileUtils.forceDelete(new File(path));

        return result;
    }
}
