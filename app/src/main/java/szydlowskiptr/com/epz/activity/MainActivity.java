package szydlowskiptr.com.epz.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.onesignal.OneSignal;
import com.rollbar.android.Rollbar;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.loginRegister.LoginActivity;
import szydlowskiptr.com.epz.home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    Button loginButton, moveToAppBtn;
    private static final String ONESIGNAL_APP_ID = "bc142489-78d2-46cf-93c5-8f8d498a0dda";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Rollbar.init(this);
        PrefConfig.registerPref(getApplicationContext());
        loginButton = findViewById(R.id.LogInBtn);
        moveToAppBtn = findViewById(R.id.MoveToAppBtn);
        clickOnLoginBtn();
        clickOnMoveToAppBtn();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.promptForPushNotifications();
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
        loginButton.setOnClickListener(new View.OnClickListener() {
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
        moveToAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectedToNetwork()) {
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Czy jestes pewien, że chcesz wyjść z aplikacji?")
                .setCancelable(false)
                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("NIE", null)
                .show();
    }


}