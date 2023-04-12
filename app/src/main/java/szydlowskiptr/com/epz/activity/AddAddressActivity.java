package szydlowskiptr.com.epz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import szydlowskiptr.com.epz.R;

public class AddAddressActivity extends AppCompatActivity {

    SharedPreferences sp;
    EditText idEdtStreet, idEdtStreetNumber, idEdtAprtNumber, idEdtPostaCode, idEdtCity,
            idEdtFloor, idInstraction;
    Button logInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        sp = getApplication().getSharedPreferences("preferences", MODE_PRIVATE);
        setView();
        clickSaveBtn();
    }

    private void setView() {
        idEdtStreet = findViewById(R.id.idEdtStreet);
        idEdtStreetNumber = findViewById(R.id.idEdtStreetNumber);
        idEdtAprtNumber = findViewById(R.id.idEdtAprtNumber);
        idEdtPostaCode = findViewById(R.id.idEdtPostaCode);
        idEdtCity = findViewById(R.id.idEdtCity);
        idEdtFloor = findViewById(R.id.idEdtFloor);
        idInstraction = findViewById(R.id.idInstraction);
        logInBtn = findViewById(R.id.LogInBtn);
    }

    private void clickSaveBtn() {
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idEdtStreet.getText().toString().matches("") || idEdtStreetNumber.getText().toString().matches("")
                        || idEdtAprtNumber.getText().toString().matches("") || idEdtPostaCode.getText().toString().matches("")
                        || idEdtCity.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Uzupełnij dane, pola nie moga być puste", Toast.LENGTH_SHORT).show();
                } else {
                    callAddAddressApi();
                }
            }
        });

    }

    private void callAddAddressApi() {

    }
}