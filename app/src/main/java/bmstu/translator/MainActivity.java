package bmstu.translator;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Language> languages = getIntent().getParcelableArrayListExtra(ConnectionService.EXTRA_LANGUAGES);
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ConnectionService.EXTRA_LANGUAGES, languages);
        LanguagesListFragment fragment = new LanguagesListFragment();
        fragment.setArguments(arguments);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    public static class LanguagesListFragment extends ListFragment {
        private ArrayList<Language> languages;
        private ArrayAdapter<String> adapter;
        public LanguagesListFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.languages = getArguments().getParcelableArrayList(ConnectionService.EXTRA_LANGUAGES);
            ArrayList<String> strings = new ArrayList<String>();
            for (Language language: languages) {
                strings.add(language.getLanguageName());
            }
            setListAdapter(new ArrayAdapter<Language>(getActivity(), android.R.layout.simple_list_item_activated_1, android.R.id.text1, this.languages));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }
}
