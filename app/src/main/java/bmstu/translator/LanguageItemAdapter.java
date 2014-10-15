package bmstu.translator;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ivan on 15.10.2014.
 */
public class LanguageItemAdapter extends ArrayAdapter<Language> {
    private int textViewResourceId;

    public LanguageItemAdapter(Context context, int textViewResourceId, List<Language> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View languageView = super.getView(position, convertView, parent);
        Language language = getItem(position);
        String languageName = language.getLanguageName();
        String languageCode = language.getLanguageCode();

//        if (convertView == null) {
//            languageView = new LinearLayout(getContext());
//            String inflater = Context.LAYOUT_INFLATER_SERVICE;
//            LayoutInflater layoutInflater;
//            layoutInflater = (LayoutInflater) getContext().getSystemService(inflater);
//            layoutInflater.inflate(textViewResourceId, languageView, true);
//        }
//        else {
//            languageView = (LinearLayout)convertView;
//        }
        TextView languageNameView = (TextView)languageView.findViewById(android.R.id.text1);
        TextView languageCodeView = (TextView)languageView.findViewById(android.R.id.text2);
        languageNameView.setText(languageName);
        languageCodeView.setText(languageCode);
        return languageView;
    }
}
