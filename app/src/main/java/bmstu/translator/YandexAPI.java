package bmstu.translator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Ivan on 13.10.2014.
 */
public class YandexAPI {
    private final String key = "trnsl.1.1.20141009T140242Z.a5cbba1e79293f2b.e621f39f66ae2b18ef9a71c2e8d69b543234bd50";
    private final String url = "https://translate.yandex.net/api/v1.5/tr.json/";


    public JSONObject detectLanguage(String text) {
        JSONObject language = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(this.url + "detect" + "?" + "key=" + this.key + "&"+ "text=" + text);
            connection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = connection.getInputStream();
            String response = readStream(inputStream);
            language = new JSONObject(response);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return language;
    }

    public JSONObject getLanguages() {
        JSONObject language = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(this.url + "getLangs" + "?" + "key=" + this.key + "&" + "ui=ru");
            connection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = connection.getInputStream();
            String response = readStream(inputStream);
            language = new JSONObject(response);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return language;
    }

    public JSONObject translate(String text, Language lang) {
        JSONObject translation = null;
        HttpURLConnection connection = null;
        String languageCode = lang.getLanguageCode();
        try {
            URL url = new URL(this.url + "translate" + "?" + "key=" + this.key + "&" + "text=" + URLEncoder.encode(text, "UTF-8") + "&" + "lang=" + languageCode);
            connection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = connection.getInputStream();
            String response = readStream(inputStream);
            translation = new JSONObject(response);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return translation;
    }

    private String readStream(InputStream is) {
        String s;
        ArrayList<Byte> byteArray = new ArrayList<Byte>();
        while(true) {
            try {
                byteArray.add((byte) is.read());
                if (byteArray.get(byteArray.size() - 1) == -1)
                    break;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            byte arr[] = new byte[byteArray.size() - 1];
            for (int i = 0; i < byteArray.size() - 1; i++) {
                arr[i] = byteArray.get(i);
            }
            s = new String(arr, "UTF-8");
            return s;
        }
        catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
