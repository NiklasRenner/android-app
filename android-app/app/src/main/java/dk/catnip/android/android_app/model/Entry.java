package dk.catnip.android.android_app.model;

public class Entry implements Comparable<Entry> {

    private String name;
    private int score;

    public Entry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Entry(Player player) {
        this.name = player.getName();
        this.score = player.getScore();
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
        } else if (score < another.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }

}
