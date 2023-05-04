package szydlowskiptr.com.epz.activity.loginRegister;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rollbar.android.Rollbar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.home.HomeActivity;
import szydlowskiptr.com.epz.home.HomeFragment;
import szydlowskiptr.com.epz.model.ErrorModel;
import szydlowskiptr.com.epz.model.RegisterModel;
import szydlowskiptr.com.epz.model.User;
import szydlowskiptr.com.epz.service.LogUser;
import szydlowskiptr.com.epz.service.RegisterUserService;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailInput, userPasswordInput, userRepeateInput, phone;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpView();
        PrefConfig.registerPref(getApplicationContext());
        registerBtnClickListener();
        Rollbar.init(this);
    }

    private void setUpView() {
        emailInput = findViewById(R.id.idEdtUserNameRegisterView);
        userPasswordInput = findViewById(R.id.idPassword);
        userRepeateInput = findViewById(R.id.idRepeatPassword);
        registerBtn = findViewById(R.id.idBtnRegister);
        phone = findViewById(R.id.phone);
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
                String phone = RegisterActivity.this.phone.getText().toString();

                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(repeatePassword) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
                    Toast.makeText(RegisterActivity.this, "Pola nie mogą być puste",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (!password.equals(repeatePassword)) {
                        Toast.makeText(RegisterActivity.this, "Hasła nie są jednakowe",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        RegisterModel registerModel = new RegisterModel();
                        registerModel.setEmail(email);
                        registerModel.setPassword(password);
                        registerModel.setPhone(phone);

                        callApiRegisterUser(registerModel);
                    }
                }
            }
        });
    }


    private void callApiRegisterUser(RegisterModel registerModel) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RegisterUserService registerUserService = retrofit.create(RegisterUserService.class);
        Call<User> userCall = registerUserService.registerUser(registerModel);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                    clearDataInInput();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
                    Toast.makeText(RegisterActivity.this, "Na Twój adres email wysłaliśmy link potwierdzający", Toast.LENGTH_SHORT).show();
                    clearDataInInput();
                } else if (response.code() == 400) {
                    Gson gson = new GsonBuilder().create();
                    ErrorModel errorModel;
                    try {
                        errorModel = gson.fromJson(response.errorBody().string(),ErrorModel.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(getApplicationContext(), errorModel.getMessage(), Toast.LENGTH_LONG).show();
                    clearDataInInput();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void clearDataInInput() {
        phone.setText("");
        userPasswordInput.setText("");
        userRepeateInput.setText("");
        emailInput.setText("");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}