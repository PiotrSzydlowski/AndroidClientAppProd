package szydlowskiptr.com.epz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.User;
import szydlowskiptr.com.epz.model.UserLog;
import szydlowskiptr.com.epz.service.LogUser;

public class LoginActivity extends AppCompatActivity {

    private EditText userNameInput, userPasswordInput;
    private Button loginBtn;
    String userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUpView();
        loginBtnClickListener();
    }


    private void setUpView() {
        userNameInput = findViewById(R.id.idEdtUserName);
        userPasswordInput = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);
    }

    private void loginBtnClickListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = userNameInput.getText().toString();
                password = userPasswordInput.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Wprowadź nazwę użytkownika i hasło",
                            Toast.LENGTH_SHORT).show();
                } else {
                    hideKeyboard(loginBtn);
                    loginUser(userName, password);
                }
            }
        });
    }

    private void loginUser(String login, String password) {
        UserLog userLog = new UserLog(login, password, login);
        callLoginApi(userLog);
    }


    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }

    private void clearDataInInput() {
        userNameInput.setText("");
        userPasswordInput.setText("");
    }

    private void savePreferences(String magId, String userId) {
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mag_id", magId);
        editor.putString("user_id", userId );
        editor.commit();
    }

    private void callLoginApi(UserLog userLog) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Proszę czekać...");
        dialog.setCancelable(false);
        dialog.show();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        LogUser logUser = retrofit.create(LogUser.class);
        Call<User> userCall = logUser.logInUser(userLog);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    savePreferences(response.body().getMagId(), String.valueOf(response.body().getId()));
                    System.out.println("====================================================== " + response.body().getMagId() + " " + response.body().getId());
                    Intent i = new Intent(LoginActivity.this, HomeActivityWithoutLogIn.class);
                    startActivity(i);
                    clearDataInInput();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
                } else if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "Nazwa użytkownika lub hasło są nieprawidłowe", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
                }
                clearDataInInput();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                clearDataInInput();
                Toast.makeText(LoginActivity.this, "Brak połączenia z serwerem", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void RegisterNewUser(View view) {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
        clearDataInInput();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
    }
}
