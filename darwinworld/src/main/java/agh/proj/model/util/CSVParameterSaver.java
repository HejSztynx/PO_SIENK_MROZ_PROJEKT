package agh.proj.model.util;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class CSVParameterSaver {
    public void save(ArrayList<String> values, String url) {
        if (url != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(url, true))) {
                StringBuilder sb = new StringBuilder();
                sb.append("preset ").append(nextPresentNumber(url)).append(",");
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

    public void delete(String url, String toDelete) {
        List<String> lines;
        Path path = Paths.get(url);
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> newLines = new ArrayList<>();
        for (String line : lines) {
            String preset = line.split(",")[0];
            if (!preset.equals(toDelete)) {
                newLines.add(line);
            }
        }

        try {
            Files.write(path, newLines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int nextPresentNumber(String url) {
        try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
            String line;
            reader.readLine();
            String res = "0";
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String lastPreset = columns[0];
                if (lastPreset.isEmpty()) continue;
                res = lastPreset.split(" ")[1];
            }

            return Integer.parseInt(res) + 1;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
