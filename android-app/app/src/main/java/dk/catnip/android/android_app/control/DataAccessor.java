package dk.catnip.android.android_app.control;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dk.catnip.android.android_app.model.Entry;
import dk.catnip.android.android_app.model.Player;
import dk.catnip.android.android_app.model.Question;
import dk.catnip.android.android_app.utils.Constants;

public class DataAccessor {

    private final Gson gson = createGson();
    private final Context context;

    public DataAccessor(Context context) {
        this.context = context;
    }

    public List<Entry> loadHighScores() {
        String data = getSharedPreferences().getString(Constants.SCORE_DATA, null);

        Type listType = new TypeToken<ArrayList<Entry>>() {
        }.getType();
        List<Entry> highscores = gson.fromJson(data, listType);

        if (highscores == null) {
            highscores = generateHighscores();
            saveHighscores(highscores);
        }

        return highscores;
    }

    public List<Entry> updateHighscores(Player player) {
        List<Entry> entries = loadHighScores();

        //create new from input
        Entry entry = new Entry(player);
        //add to highscores
        entries.add(entry);
        //create new highscore from current
        entries = sortAndGetBest(entries);
        //save to preferences
        saveHighscores(entries);

        return entries;
    }

    public void saveHighscores(List<Entry> entries) {
        String data = gson.toJson(entries);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(Constants.SCORE_DATA, data);
        editor.apply();
    }

    public List<Entry> resetHighscores() {
        List<Entry> entries = generateHighscores();
        saveHighscores(entries);

        return entries;
    }

    private List<Entry> generateHighscores() {
        List<Entry> entries = new ArrayList<>();

        //TODO refac: make more dynamic
        String[] names = new String[]{"Dunse Daniel", "Torsten Tissemyre", "Gunnar", "Mify", "Jorge", "Mutant Martin", "Randi"};
        for (int i = 0; i < 10; i++) {
            Entry entry = new Entry(names[(int) (Math.random() * names.length)], (int) (Math.random() * 250));
            entries.add(entry);
        }

        return sortAndGetBest(entries);
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

    public Iterator<Question> loadQuestions(Activity activity, int resourceId) {
        Type listType = new TypeToken<ArrayList<Question>>() {
        }.getType();

        List<Question> questions = gson.fromJson(readFile(activity, resourceId), listType);

        return questions.iterator();
    }

    private List<Entry> sortAndGetBest(List<Entry> entries) {
        Collections.sort(entries);
        return entries.subList(0, 10);
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(Constants.PREFERENCES, 0);
    }

    private static String readFile(Activity activity, int resourceId) {
        InputStream is = activity.getResources().openRawResource(resourceId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String readLine;
        StringBuilder builder = new StringBuilder();
        try {
            while ((readLine = br.readLine()) != null) {
                builder.append(readLine).append('\n');
            }
            is.close();
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }

    private static Gson createGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
