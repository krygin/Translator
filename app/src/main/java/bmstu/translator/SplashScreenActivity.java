package bmstu.translator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;


public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ConnectionService.startActionGetAllLanguages(this);
        IntentFilter intentFilter = ConnectionService.getActionReturnLanguagesIntentFilter();
        BroadcastReceiver receiver = new LanguagesBroadcastReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private class LanguagesBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final ArrayList<Language> languages = intent.getParcelableArrayListExtra(ConnectionService.EXTRA_LANGUAGES);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    intent.putParcelableArrayListExtra(ConnectionService.EXTRA_LANGUAGES, languages);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
            unregisterReceiver(this);
        }
    }
}