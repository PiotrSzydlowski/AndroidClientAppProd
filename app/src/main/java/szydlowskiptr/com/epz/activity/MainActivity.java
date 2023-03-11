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

import androidx.appcompat.app.AppCompatActivity;

import szydlowskiptr.com.epz.R;

public class MainActivity extends AppCompatActivity {

    Button loginButton, moveToAppBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.LogInBtn);
        moveToAppBtn = (Button) findViewById(R.id.MoveToAppBtn);
        clickOnLoginBtn();
        clickOnMoveToAppBtn();

    }


    public void clickOnLoginBtn() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
            }
        });
    }

    public void clickOnMoveToAppBtn() {
        moveToAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeActivityWithoutLogIn.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
            }
        });
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