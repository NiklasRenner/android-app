package dk.catnip.android.android_app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Entry implements Comparable<Entry> {

    private static final String ENTRY_SEPARATOR = "::";
    private static final String LINE_SEPARATOR = "|";

    private String name;
    private int score;

    public Entry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(Entry another) {
        if (score > another.getScore()) {
            return -1;
        } else if (another.getScore() > score) {
            return 1;
        } else {
            return 0;
        }
    }

    public static List<Entry> createDefault() {
        List<Entry> entries = new ArrayList<>();

        String[] names = new String[]{"Rolf", "Torsten", "Gunnar", "Mify", "Jorge"};
        for (int i = 0; i < 10; i++) {
            Entry entry = new Entry(names[(int) (Math.random() * 5)], (int) (Math.random() * 250));
            entries.add(entry);
        }

        return entries;
    }

    public static List<Entry> fromCacheFormat(String data) {
        if (data == null) {
            return null;
        }

        List<Entry> entries = new ArrayList<>();

        String[] lines = data.split("\\|");
        for (String line : lines) {
            String[] rawEntry = line.split("::");
            Entry entry = new Entry(rawEntry[0], Integer.parseInt(rawEntry[1]));
            entries.add(entry);
        }

        Collections.sort(entries);
        return entries;
    }

    public static String toCacheFormat(List<Entry> entries) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            Entry entry = entries.get(i);
            builder.append(LINE_SEPARATOR);
            builder.append(entry.getName());
            builder.append(ENTRY_SEPARATOR);
            builder.append(entry.getScore());
        }

        return builder.toString().substring(1);
    }

}
