package dk.catnip.android.android_app.model;

import dk.catnip.android.android_app.utils.Constants;

public class Entry implements Comparable<Entry> {

    private String name;
    private int score;

    public Entry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Entry(Player player){
        this.name = player.getName();
        this.score = player.getScore();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public static Entry fromString(String rawEntry) {
        String[] pair = rawEntry.split(Constants.ENTRY_SEPARATOR);

        return new Entry(pair[0], Integer.parseInt(pair[1]));
    }

    @Override
    public int compareTo(Entry another) {
        if (score > another.getScore()) {
            return -1;
        } else if (score < another.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", name, Constants.ENTRY_SEPARATOR, score);
    }
}
