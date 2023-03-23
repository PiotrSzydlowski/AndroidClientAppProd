package szydlowskiptr.com.epz.activity;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import szydlowskiptr.com.epz.R;

public class MainActivity extends AppCompatActivity {

    Button loginButton, moveToAppBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.LogInBtn);
        moveToAppBtn = findViewById(R.id.MoveToAppBtn);
        clickOnLoginBtn();
        clickOnMoveToAppBtn();

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
                    savePreferences("0", "1");
                    Intent i = new Intent(getApplicationContext(), HomeActivityWithoutLogIn.class);
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
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mag_id", magId);
        editor.putString("user_id", userId );
        editor.commit();
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