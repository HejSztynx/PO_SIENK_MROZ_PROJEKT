package agh.proj.model.util;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class CSVParameterSaver {
    public void save(ArrayList<String> values) {
        URL url = getClass().getClassLoader().getResource("presets.csv");

        if (url != null) {
            int lineCount = 0;

            try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(url.getFile()))) {
                lineNumberReader.skip(Long.MAX_VALUE);
                lineCount = lineNumberReader.getLineNumber();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("LINIE " + lineCount);
            System.out.println(url);

//            try {
//                // Zapis do folderu użytkownika (zmień ścieżkę zgodnie z własnymi potrzebami)
//                Path userHomePath = Paths.get(System.getProperty("user.home"));
//                Path filePath = userHomePath.resolve("presets.csv");
//
//                try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("preset ").append(lineCount).append(",");
//                    for (String value : values) {
//                        sb.append(value).append(",");
//                    }
//                    sb.deleteCharAt(sb.length() - 1);
//                    System.out.println("ZAPISUJE");
//                    writer.write(sb.toString());
//                    writer.newLine();
//                    System.out.println("ZAPISALEM");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.err.println("File not found");
//        }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(url.getFile(), true))) {
                StringBuilder sb = new StringBuilder();
                sb.append("preset ").append(lineCount).append(",");
                for (String value : values) {
                    sb.append(value).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                System.out.println("ZAPISUJE");
                writer.newLine();
                writer.write(sb.toString());
                System.out.println("ZAPISALEM");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("File not found");
        }
    }
}
