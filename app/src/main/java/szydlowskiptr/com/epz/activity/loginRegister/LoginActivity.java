package szydlowskiptr.com.epz.activity.loginRegister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import szydlowskiptr.com.epz.databinding.ActivityLoginBinding;
import szydlowskiptr.com.epz.home.HomeActivity;
import szydlowskiptr.com.epz.model.ErrorModel;
import szydlowskiptr.com.epz.model.User;
import szydlowskiptr.com.epz.model.UserLog;
import szydlowskiptr.com.epz.service.LogUser;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    String userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PrefConfig.registerPref(getApplicationContext());
        loginBtnClickListener();
        Rollbar.init(this);
    }

    private void loginBtnClickListener() {
        binding.idBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = binding.idEdtUserName.getText().toString();
                password = binding.idEdtPassword.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Wprowadź nazwę użytkownika i hasło",
                            Toast.LENGTH_SHORT).show();
                } else {
                    hideKeyboard(binding.idBtnLogin);
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
        binding.idEdtUserName.setText("");
        binding.idEdtPassword.setText("");
    }

    private void savePreferences(String magId, String userId,String activeOrder, String userBanned,
                                 String openFrom, String openTo, String open, String temOpen) {
        PrefConfig.saveMagIdInPref(getApplicationContext(), magId);
        PrefConfig.saveUserIdInPref(getApplicationContext(), userId);
        PrefConfig.saveActiveOrderInPref(getApplicationContext(), activeOrder);
        PrefConfig.saveIfUserBannedInPref(getApplicationContext(), userBanned);
        PrefConfig.saveOpenFromInPref(getApplicationContext(), openFrom);
        PrefConfig.saveOpenToInPref(getApplicationContext(), openTo);
        PrefConfig.saveIfOpenInPref(getApplicationContext(), open);
        PrefConfig.saveIfTempOpenInPref(getApplicationContext(), temOpen);
    }

    private void saveAddressPreferences(String street, String streetNumber, String doorNumber, String postalCode, String city) {
        PrefConfig.saveAddressStreetInPref(getApplicationContext(), street);
        PrefConfig.saveAddressStreetNumberInPref(getApplicationContext(), streetNumber);
        PrefConfig.saveAddressDoorNumberInPref(getApplicationContext(), doorNumber);
        PrefConfig.savePostalCodeInPref(getApplicationContext(), postalCode);
        PrefConfig.saveCityInPref(getApplicationContext(), city);
    }

    private void callLoginApi(UserLog userLog) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Proszę czekać...");
        dialog.setCancelable(false);
        dialog.show();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.34:9193/prod/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        LogUser logUser = retrofit.create(LogUser.class);
        Call<User> userCall = logUser.logInUser(userLog);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    savePreferences(response.body().getMagazine(), String.valueOf(response.body().getId()), String.valueOf(response.body().isActiveOrder()),
                            String.valueOf(response.body().isBanned()), response.body().getOpenFrom(),
                            response.body().getOpenTo(), String.valueOf(response.body().isOpen()), String.valueOf(response.body().isTempOpen()));
                    if (!response.body().getMagazine().equals("3")) {
                        saveAddressPreferences(response.body().getStreet(), response.body().getStreetNumber(),
                                response.body().getDoorNumber(), response.body().getPostalCode(),
                                response.body().getCity());
                    }
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                    clearDataInInput();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
                } else if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "Nazwa użytkownika lub hasło są nieprawidłowe", Toast.LENGTH_SHORT).show();
                } else {
                    showErrorFromApi(response);
//                    Toast.makeText(LoginActivity.this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
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

    private void showErrorFromApi(Response<User> response) {
        Gson gson = new GsonBuilder().create();
        ErrorModel errorModel;
        try {
            errorModel = gson.fromJson(response.errorBody().string(), ErrorModel.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Toast.makeText(getApplicationContext(), errorModel.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void finish() {
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
