package bmstu.translator;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Ivan on 14.10.2014.
 */
public class Language implements Parcelable {
    private String languageCode;
    private String languageName;

    public Language(String languageCode, String languageName) {
        this.languageCode = languageCode;
        this.languageName = languageName;
    }

    private Language(Parcel parcel) {
        String [] data = new String[2];
        parcel.readStringArray(data);
        this.languageCode = data[0];
        this.languageName = data[1];
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {languageCode, languageName});
    }

    @Override
    public String toString() {
        return languageName + "-" + languageCode;
    }

    public static final Parcelable.Creator<Language> CREATOR = new Parcelable.Creator<Language>() {

        @Override
        public Language createFromParcel(Parcel source) {
            return new Language(source);
        }

        @Override
        public Language[] newArray(int size) {
            return new Language[size];
        }
    };
}