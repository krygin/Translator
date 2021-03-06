package bmstu.translator;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 15.10.2014.
 */
public class LanguageItemAdapter extends ArrayAdapter<Language> {

    public LanguageItemAdapter(Context context, int resource, int textViewResourceId, List<Language> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View languageView = super.getView(position, convertView, parent);
        Language language = getItem(position);
        String languageName = language.getLanguageName();
        String languageCode = language.getLanguageCode();
        TextView languageNameView = (TextView)languageView.findViewById(android.R.id.text1);
        TextView languageCodeView = (TextView)languageView.findViewById(android.R.id.text2);
        languageNameView.setText(languageName);
        languageCodeView.setText(languageCode);
        return languageView;
    }
}
