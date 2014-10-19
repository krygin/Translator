package bmstu.translator;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class LanguageFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LANGUAGES = "languages";

    // TODO: Rename and change types of parameters
    private ArrayList<Language> languages;

    private OnLanguageSelectListener mListener;

    // TODO: Rename and change types of parameters
    public static LanguageFragment newInstance(ArrayList<Language> languages) {
        LanguageFragment fragment = new LanguageFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_LANGUAGES, languages);
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LanguageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.languages = getArguments().getParcelableArrayList(ARG_LANGUAGES);
        }

        // TODO: Change Adapter to display your content
        setListAdapter(new LanguageItemAdapter(getActivity(),
                android.R.layout.simple_list_item_2, android.R.id.text1, this.languages));
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
          mListener = (OnLanguageSelectListener) activity;
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


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onLanguageSelect(languages.get(position));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
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
    public interface OnLanguageSelectListener {
        // TODO: Update argument type and name
        public void onLanguageSelect(Language language);
    }

}
