package dk.catnip.android.android_app.control;


import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dk.catnip.android.android_app.model.Question;

public class JsonResourceReader {

    private JsonResourceReader() {
        //don't
    }

    public static List<Question> loadQuestions(Activity activity, int resourceId) {
        List<Question> questions = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(readFile(activity, resourceId));

            JSONArray questionsJson = obj.getJSONArray("questions");
            int length = questionsJson.length();
            for (int i = 0; i < length; i++) {
                JSONObject questionJson = questionsJson.getJSONObject(0);
                questions.add(Question.fromJsonObject(questionJson));
            }
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }

        return questions;
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
}
