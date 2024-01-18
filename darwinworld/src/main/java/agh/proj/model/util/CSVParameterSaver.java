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
            System.out.println(lines);
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

        String a = newLines.get(newLines.size() - 1).replaceAll("([\\n\\r]+\\s*)*$", "");
        newLines.remove(newLines.size() - 1);
        newLines.add(a);
//        int lastIndex = newLines.size() - 1;

        // Usuń puste linie z końca listy
//        while (lastIndex >= 0 && newLines.get(lastIndex).trim().isEmpty()) {
//            newLines.remove(lastIndex);
//            lastIndex--;
//        }
//
        try {
            Files.write(path, newLines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        try (RandomAccessFile file = new RandomAccessFile(url, "rw")) {
//            long length = file.length();
//
//            if (length == 0) {
//                // Plik jest pusty, nie ma linii do usunięcia
//                return;
//            }
//
//            // Znajdź pozycję ostatniej nowej linii
//            long position = length - 1;
//            while (position > 0 && file.readByte() != '\n') {
//                position--;
//                file.seek(position);
//            }
//
//            // Ustaw pozycję na koniec pliku
//            file.setLength(position);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private int nextPresentNumber(String url) {
        try (BufferedReader reader = new BufferedReader(new FileReader(url))) {
            String line1;
            String line2;
            line1 = reader.readLine();
            while ((line2 = reader.readLine()) != null) {
                line1 = line2;
            }
            String[] columns = line1.split(",");
            String lastPreset = columns[0];
            if (lastPreset.equals("preset")) return 1;
            lastPreset = lastPreset.split(" ")[1];
            return Integer.parseInt(lastPreset) + 1;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private boolean isLastLineEmpty(String url) {
        try {
            Path path = Path.of(url);

            // Odczytaj wszystkie linie z pliku
            String content = Files.readString(path, StandardCharsets.UTF_8);

            // Podziel treść pliku na linie
            String[] lines = content.split("\\r?\\n");

            // Sprawdź, czy ostatnia linia jest pusta
            if (lines.length > 0) {
                String lastLine = lines[lines.length - 1];
                return true;
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
