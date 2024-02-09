package szydlowskiptr.com.epz.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.onesignal.OneSignal;
import com.rollbar.android.Rollbar;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.loginRegister.LoginActivity;
import szydlowskiptr.com.epz.activity.status.StatusActivity;
import szydlowskiptr.com.epz.databinding.ActivityMainBinding;
import szydlowskiptr.com.epz.home.HomeActivity;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    private static final String ONESIGNAL_APP_ID = "bc142489-78d2-46cf-93c5-8f8d498a0dda";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Rollbar.init(this);
        PrefConfig.registerPref(getApplicationContext());
        clickOnLoginBtn();
        clickOnMoveToAppBtn();
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//        OneSignal.initWithContext(this);
//        OneSignal.setAppId(ONESIGNAL_APP_ID);
//        OneSignal.promptForPushNotifications();
        getPreferences(getApplicationContext());
    }


    private void getPreferences(Context context) {
        String s = PrefConfig.loadUserIdFromPref(context);
        if (s == null) {
            PrefConfig.saveActiveOrderInPref(context, "false");
            PrefConfig.saveIfUserBannedInPref(context, "false");
            PrefConfig.saveIfOpenInPref(context, "true");
            PrefConfig.saveIfTempOpenInPref(context, "false");
        }
        if (PrefConfig.loadUserIdFromPref(context) != null) {
            System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF " + PrefConfig.loadUserIdFromPref(context));
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
        }
    }


    private boolean connectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isConnected();
        } else
            return false;
    }

    public void clickOnLoginBtn() {
        binding.LogInBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (connectedToNetwork()) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
                } else {
                    Toast.makeText(getApplicationContext(), "Brak połączenia z Internetem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clickOnMoveToAppBtn() {
        binding.MoveToAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectedToNetwork()) {
//                    PrefConfig.saveActiveOrderInPref(getApplicationContext(), "false");
                    savePreferences("3", "0");
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
                } else {
                    Toast.makeText(getApplicationContext(), "Brak połączenia z Internetem", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void savePreferences(String magId, String userId) {
        PrefConfig.saveMagIdInPref(getApplicationContext(), magId);
        PrefConfig.saveUserIdInPref(getApplicationContext(), userId);
    }
}