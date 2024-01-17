package agh.proj.model.util;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class CSVParameterSaver {
    public void save(ArrayList<String> values, String url) {
        if (url != null) {
            int lineCount = 0;

            try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(url))) {
                lineNumberReader.skip(Long.MAX_VALUE);
                lineCount = lineNumberReader.getLineNumber();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(url, true))) {
                StringBuilder sb = new StringBuilder();
                sb.append("preset ").append(lineCount).append(",");
                for (String value : values) {
                    sb.append(value).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                writer.newLine();
                writer.write(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("File not found");
        }
    }
}
