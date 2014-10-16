package bmstu.translator;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import java.util.ArrayList;


public class MainActivity extends Activity implements LanguageFragment.OnLanguageSelectListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Language> languages = getIntent().getParcelableArrayListExtra(ConnectionService.EXTRA_LANGUAGES);
        Fragment fragment = LanguageFragment.newInstance(languages);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public void onLanguageSelect(Language language) {
        //TODO:Заменить фрагмент со списком языков фрагментом с переводом
        //сам буду пока доделывать сервис, чтобы возвращал "особые значения" при отсутствии сети
    }
}