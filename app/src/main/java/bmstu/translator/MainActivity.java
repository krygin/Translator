package bmstu.translator;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;


public class MainActivity extends Activity implements LanguageFragment.OnLanguageSelectListener, TranslationFragment.OnTranslateListener {
    private static String TRANSLATION_FRAGMENT_TAG = "translation_fragment_tag";
    private static String LANGUAGES_FRAGMENT_TAG = "languages_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<Language> languages = getIntent().getParcelableArrayListExtra(ConnectionService.EXTRA_LANGUAGES);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment languageFragment = LanguageFragment.newInstance(languages);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, languageFragment)
                    .commit();
        }
    }
    @Override
    public void onLanguageSelect(Language language) {
        //TODO:Заменить фрагмент со списком языков фрагментом с переводом
        //сам буду пока доделывать сервис, чтобы возвращал "особые значения" при отсутствии сети
        TranslationFragment translationFragment = TranslationFragment.newInstance(language);
        getFragmentManager().beginTransaction().replace(R.id.container, translationFragment, TRANSLATION_FRAGMENT_TAG).addToBackStack(null).commit();
    }

    @Override
    public void OnTranslate(Language source_language, Language target_language, String text) {
        ConnectionService.startActionTranslateText(this, text, target_language);
        IntentFilter intentFilter = ConnectionService.getActionReturnTranslationIntentFilter();
        BroadcastReceiver receiver = new TranslationBroadcastReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private class TranslationBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            TranslationFragment translationFragment = (TranslationFragment)getFragmentManager().findFragmentByTag(TRANSLATION_FRAGMENT_TAG);
            String translation = intent.getStringExtra(ConnectionService.EXTRA_TRANSLATION);
            Language sourceLanguage = intent.getParcelableExtra(ConnectionService.EXTRA_SOURCE_LANGUAGE);
            translationFragment.setSourceLanguage(sourceLanguage);
            translationFragment.setTranslation(translation);
        }
    }
}