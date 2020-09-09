package com.fplstats.repositories.services.understat;

import com.fplstats.common.dto.fplstats.Result;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.lang.ProcessBuilder.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PythonUtility {

    public static Result<String> ExecutePythonScript(String path, String[] args) throws IOException, InterruptedException {

        String command = "C:\\Users\\krist\\AppData\\Local\\Programs\\Python\\Python38-32\\python.exe";


        ProcessBuilder processBuilder =
                new ProcessBuilder(command,path, args[0], args[1]);

        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        String results = readProcessOutput(process.getInputStream());

        int exitCode = process.waitFor();

        Result<String> result = new Result<>();
        result.Success = true;

        if (exitCode != 0){
            result.Success = false;
            result.ErrorMessage = "Error in python-script";
            return result;
        }

        result.Data = results;

        return result;
    }

    private static String readProcessOutput(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        int charsRead;
        while((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
            out.append(buffer, 0, charsRead);
        }
        return out.toString();
    }

}
