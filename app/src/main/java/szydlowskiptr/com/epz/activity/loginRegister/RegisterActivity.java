package szydlowskiptr.com.epz.activity.loginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rollbar.android.Rollbar;

import szydlowskiptr.com.epz.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailInput, userPasswordInput, userRepeateInput;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpView();
        registerBtnClickListener();
        Rollbar.init(this);
    }

    private void setUpView() {
        emailInput = findViewById(R.id.idEdtUserNameRegisterView);
        userPasswordInput = findViewById(R.id.idPassword);
        userRepeateInput = findViewById(R.id.idRepeatPassword);
        registerBtn = findViewById(R.id.idBtnRegister);
    }

    private boolean checkIfPasswordIsTheSame(String password, String repeatePassword) {
        return password.equals(repeatePassword);
    }


    private void registerBtnClickListener() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = userPasswordInput.getText().toString();
                String repeatePassword = userRepeateInput.getText().toString();
                String email = emailInput.getText().toString();

                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(repeatePassword) || TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Pola nie mogą być puste",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (!password.equals(repeatePassword)) {
                        Toast.makeText(RegisterActivity.this, "Hasła nie są jednakowe",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Rejestrujemy",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}