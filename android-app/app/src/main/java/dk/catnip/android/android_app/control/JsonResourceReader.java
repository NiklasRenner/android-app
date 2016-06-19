package dk.catnip.android.android_app.control;

import android.app.Activity;

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
import java.util.List;

import dk.catnip.android.android_app.model.Question;

public class JsonResourceReader {

    private JsonResourceReader() {
        //don't
    }

    public static List<Question> loadQuestions(Activity activity, int resourceId) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Type listType = new TypeToken<ArrayList<Question>>() {}.getType();
        return gson.fromJson(readFile(activity, resourceId), listType);
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
