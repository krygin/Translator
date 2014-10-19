package bmstu.translator;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link bmstu.translator.TranslationFragment.OnTranslateListener} interface
 * to handle interaction events.
 * Use the {@link TranslationFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TranslationFragment extends Fragment {

    private static final String ARG_LANGUAGE = "language";

    private String translation;
    private Language targetLanguage;
    private Language sourceLanguage;


    private OnTranslateListener mListener;

    public static TranslationFragment newInstance(Language language) {
        TranslationFragment fragment = new TranslationFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_LANGUAGE, language);
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }
    public TranslationFragment() {
        // Required empty public constructor
    }

    public void setDestinationLanguage (Language language) {
        this.targetLanguage = language;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            this.targetLanguage = getArguments().getParcelable(ARG_LANGUAGE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_translation, container, false);
        Button swap_button = (Button)view.findViewById(R.id.swap_button);
        Button translate_button = (Button)view.findViewById(R.id.translate_button);
        swap_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Language tempLanguage = sourceLanguage;
                sourceLanguage = targetLanguage;
                targetLanguage = tempLanguage;
                ((TextView)view.findViewById(R.id.target_language)).setText(targetLanguage ==null?"": targetLanguage.getLanguageName());
                ((TextView)view.findViewById(R.id.source_language)).setText(sourceLanguage ==null?"": sourceLanguage.getLanguageName());
            }
        });

        translate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText source_edit_text = (EditText)view.findViewById(R.id.source_text);
                String source_text = source_edit_text.getText().toString();
                if (mListener != null && targetLanguage != null)
                    mListener.OnTranslate(sourceLanguage, targetLanguage, source_text);
            }
        });
        ((TextView)view.findViewById(R.id.target_language)).setText(targetLanguage ==null?"": targetLanguage.getLanguageName());
        ((TextView)view.findViewById(R.id.source_language)).setText(sourceLanguage ==null?"": sourceLanguage.getLanguageName());
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTranslateListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
        if (getView()!=null)
            ((TextView)getView().findViewById(R.id.target_text)).setText(translation);
    }

    public void setSourceLanguage(Language sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
        if (getView()!=null)
            ((TextView)getView().findViewById(R.id.source_language)).setText(sourceLanguage.getLanguageName());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTranslateListener {
        public void OnTranslate(Language sourceLanguage, Language targetLanguage, String text);
    }
}