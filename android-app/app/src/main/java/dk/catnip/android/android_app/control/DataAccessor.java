package dk.catnip.android.android_app.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dk.catnip.android.android_app.model.Entry;
import dk.catnip.android.android_app.utils.Constants;

public class DataAccessor {

    private final Context context;

    public DataAccessor(Context context) {
        this.context = context;
    }

    public List<Entry> loadHighScore() {
        String data = getSharedPreferences().getString(Constants.SCORE_DATA, null);

        List<Entry> highscores = fromCacheFormat(data);

        if (highscores == null) {
            highscores = createDefault();
            highscores = fix(highscores);
            saveHighScores(highscores);
        }

        return highscores;
    }

    public List<Entry> saveHighScore(String name, int score) {
        List<Entry> entries = loadHighScore();

        //create new from input
        Entry entry = new Entry(name, score);
        //add to highscore
        entries.add(entry);
        //create new highscore from current
        entries = fix(entries);
        //save to preferences
        saveHighScores(entries);

        return entries;
    }

    public void saveHighScores(List<Entry> entries) {
        String data = toCacheFormat(entries);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(Constants.SCORE_DATA, data);
        editor.apply();
    }

    public void saveName(String name) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(Constants.NAME_DATA, name);
        editor.apply();
    }

    public String loadName() {
        String name = getSharedPreferences().getString(Constants.NAME_DATA, null);

        if (name == null) {
            name = Constants.DEFAULT_NAME;
        }

        return name;
    }

    private List<Entry> fromCacheFormat(String data) {
        if (data == null) {
            return null;
        }

        List<Entry> entries = new ArrayList<>();
        String[] lines = data.split(Constants.LINE_SEPARATOR);
        for (String line : lines) {
            entries.add(Entry.fromString(line));
        }

        return entries;
    }

    private String toCacheFormat(List<Entry> entries) {
        List<String> rawEntries = new ArrayList<>();

        for (Entry entry : entries) {
            rawEntries.add(entry.toString());
        }

        return TextUtils.join(Constants.LINE_SEPARATOR, rawEntries);
    }

    private List<Entry> createDefault() {
        List<Entry> entries = new ArrayList<>();

        String[] names = new String[]{"Rolf", "Torsten", "Gunnar", "Mify", "Jorge"};
        for (int i = 0; i < 10; i++) {
            Entry entry = new Entry(names[(int) (Math.random() * 5)], (int) (Math.random() * 250));
            entries.add(entry);
        }

        return entries;
    }

    private List<Entry> fix(List<Entry> entries) {
        Collections.sort(entries);
        return entries.subList(0, 10);
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(Constants.PREFERENCES, 0);
    }
}
