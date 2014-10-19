package bmstu.translator;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ConnectionService extends IntentService {

    private static final String ACTION_DETECT_LANGUAGE = "bmstu.translator.action.ACTION_DETECT_LANGUAGE";
    private static final String ACTION_TRANSLATE_TEXT = "bmstu.translator.action.ACTION_TRANSLATE_TEXT";
    private static final String ACTION_GET_ALL_LANGUAGES = "bmstu.translator.action.ACTION_GET_ALL_LANGUAGES";

    private static final String ACTION_RETURN_LANGUAGES = "bmstu.translator.action.ACTION_RETURN_LANGUAGES";
    private static final String ACTION_RETURN_TRANSLATION = "bmstu.translator.action.ACTION_RETURN_TRANSLATION";

    public static final String EXTRA_TEXT = "bmstu.translator.extra.TEXT";
    public static final String EXTRA_LANGUAGE = "bmstu.translator.extra.LANGUAGE";
    public static final String EXTRA_TRANSLATION = "bmstu.translator.extra.TRANSLATION";
    public static final String EXTRA_LANGUAGES = "bmstu.translator.extra.LANGUAGES";
    public static final String EXTRA_SOURCE_LANGUAGE = "bmstu.translator.extra.SOURCE_LANGUAGE";

    public static void startActionGetAllLanguages(Context context) {
        Intent intent = new Intent(context, ConnectionService.class);
        intent.setAction(ACTION_GET_ALL_LANGUAGES);
        context.startService(intent);
    }

    public static void startActionTranslateText(Context context, String text, Language lang) {
        Intent intent = new Intent(context, ConnectionService.class);
        intent.setAction(ACTION_TRANSLATE_TEXT);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_LANGUAGE, lang);
        context.startService(intent);
    }

    public static IntentFilter getActionReturnLanguagesIntentFilter(){
        IntentFilter intentFilter = new IntentFilter(ACTION_RETURN_LANGUAGES);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        return intentFilter;
    }

    public static IntentFilter getActionReturnTranslationIntentFilter() {
        IntentFilter intentFilter = new IntentFilter(ACTION_RETURN_TRANSLATION);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        return intentFilter;
    }


    public ConnectionService() {
        super("ConnectionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_LANGUAGES.equals(action)) {
                ArrayList<Language> languages = handleGetAllLanguages();
                sendAvailableLanguages(languages);
            } else if (ACTION_TRANSLATE_TEXT.equals(action)) {
                final String text = intent.getStringExtra(EXTRA_TEXT);
                final Language lang = intent.getParcelableExtra(EXTRA_LANGUAGE);
                TranslationResult translationResult = handleTranslateText(text, lang);
                ArrayList<Language> languages = handleGetAllLanguages();
                Language sourceLanguage = null;
                for (Language language : languages) {
                    if (language.getLanguageCode().equals(translationResult.destination.split("-")[0])) {
                        sourceLanguage = language;
                        break;
                    }
                }
                String translation = translationResult.getTargetText();
                sendTranslation(translation, sourceLanguage);
            }
        }
    }

    private void sendTranslation(String translation, Language language) {
        Intent intent = new Intent();
        intent.setAction(ACTION_RETURN_TRANSLATION);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(EXTRA_TRANSLATION, translation);
        intent.putExtra(EXTRA_SOURCE_LANGUAGE, language);
        sendBroadcast(intent);
    }

    private void sendAvailableLanguages(ArrayList<Language> languages) {
        Intent intent = new Intent();
        intent.setAction(ACTION_RETURN_LANGUAGES);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putParcelableArrayListExtra(EXTRA_LANGUAGES, languages);
        sendBroadcast(intent);
    }

    private TranslationResult handleTranslateText(String text, Language lang) {
        YandexAPI api = new YandexAPI();
        JSONObject translation = api.translate(text, lang);
        String result = null;
        String destination = null;
        try {
            result = translation.getJSONArray("text").getString(0);
            destination = translation.getString("lang");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new TranslationResult(result, destination);
    }


    private class TranslationResult {
        private String targetText;
        private String destination;

        private TranslationResult(String targetText, String destination) {
            this.targetText = targetText;
            this.destination = destination;
        }

        public String getTargetText() {
            return targetText;
        }

        public String getDestination() {
            return destination;
        }
    }
    private ArrayList<Language> handleGetAllLanguages() {
        YandexAPI api = new YandexAPI();
        ArrayList<Language> languagesList = new ArrayList<Language>();
        try {
            JSONObject languages = api.getLanguages().getJSONObject("langs");
            Iterator<?> iterator = languages.keys();
            while (iterator.hasNext()) {
                String key = (String)iterator.next();
                String value = languages.getString(key);
                languagesList.add(new Language(key, value));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return languagesList;
    }
}
