package com.fplstats.repositories.database;

import com.fplstats.common.dto.fplstats.Result;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.File;  // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.Scanner;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;

public class FileProvider {

    public static Result<String> WriteFileContent(String data, String filename) {
        String basePath = "C:\\Users\\krijoh3\\IdeaProjects\\fplstats\\src\\main\\resources\\data\\";
        String path = basePath + filename;
        Result<String> result = new Result<>();
        result.Success = true;

        FileWriter dataFile = null;

        try {
            File file = new File(path);
            file.getParentFile().mkdirs();

            dataFile = new FileWriter(path);

            dataFile.write(data);
            result.Data = dataFile.toString();

            dataFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            result.Success = false;
            return result;
        }

        return result;
    }

    public static Result<String> ReadFileContent(String filename){
        return ReadFileContent(filename, false);
    }

    public static Result<String> ReadFileContent(String filename, boolean absolutePath) {

        String basePath = "C:\\Users\\krijoh3\\IdeaProjects\\fplstats\\src\\main\\resources\\data\\";
        String path;

        if (absolutePath){
            path = filename;
        }
        else{
            path = basePath + filename;
        }
        Result<String> result = new Result<>();
        result.Success = true;
        result.Data = "";

        try {
            File file = new File(path);

            Scanner myReader = new Scanner(file);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                result.Data += data;
            }

            myReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            result.Success = false;
            return result;
        }

        return result;
    }

    public static boolean DeleteItem(String path) throws IOException {

        boolean result = true;

        FileUtils.forceDelete(new File(path));

        return result;
    }
}
