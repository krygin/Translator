package bmstu.translator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends Activity implements LanguageFragment.OnLanguageSelectListener, TranslationFragment.OnFragmentInteractionListener{
    private static final String LANGUAGE_FRAGMENT_TAG = "language_fragment_tag";
    private static final String TRANSLATION_FRAGMENT_TAG = "translation_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Language> languages = getIntent().getParcelableArrayListExtra(ConnectionService.EXTRA_LANGUAGES);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Fragment languageFragment = LanguageFragment.newInstance(languages);
            Fragment translationFragment = TranslationFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, languageFragment, LANGUAGE_FRAGMENT_TAG)
                    .add(R.id.container2, translationFragment, TRANSLATION_FRAGMENT_TAG)
                    .commit();
        }
    }
    @Override
    public void onLanguageSelect(Language language) {
        //TODO:Заменить фрагмент со списком языков фрагментом с переводом
        //сам буду пока доделывать сервис, чтобы возвращал "особые значения" при отсутствии сети
        TranslationFragment translateFragment = (TranslationFragment)getFragmentManager().findFragmentByTag(TRANSLATION_FRAGMENT_TAG);
        LanguageFragment languageFragment = (LanguageFragment)getFragmentManager().findFragmentByTag(LANGUAGE_FRAGMENT_TAG);
        translateFragment.setDestinationLanguage(language);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.
                remove(languageFragment).
                remove(translateFragment).
                add(R.id.container, translateFragment).
                addToBackStack(null).
                commit();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}