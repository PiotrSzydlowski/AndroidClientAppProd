package szydlowskiptr.com.epz.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.databinding.ActivityProfileDataBinding;
import szydlowskiptr.com.epz.model.Customer;
import szydlowskiptr.com.epz.repositories.UserDataRepository;

public class ProfileDataActivity extends AppCompatActivity {

    ActivityProfileDataBinding binding;
    private UserDataRepository repository = new UserDataRepository(ProfileDataActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_data);
        binding = ActivityProfileDataBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        PrefConfig.registerPref(getApplicationContext());
        saveProfileData();
        deleteAccount();
    }

    private void saveProfileData() {
        binding.idBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callUpdateApiUserData();
            }
        });
    }

    private void callUpdateApiUserData() {
        Customer customer = new Customer();
        customer.setId(Long.valueOf(PrefConfig.loadUserIdFromPref(getApplicationContext())));
        customer.setEmail(String.valueOf(binding.emailEdittext.getText()));
        customer.setPhone(String.valueOf(binding.phoneEdittext.getText()));
        customer.setPassword(String.valueOf(binding.passwordEdittext.getText()));
        if (customer.getEmail().equals("") ||customer.getPassword().equals("") || customer.getPhone().equals("")) {
            Toast.makeText(ProfileDataActivity.this, "Pola nie mogą puste ", Toast.LENGTH_SHORT).show();
        } else {
            repository.updateUserData(customer);
        }
    }

    private void deleteAccount() {
        binding.idBtnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public void notifyOnResponseFinished() {
        Toast.makeText(getApplicationContext(), "Dane użytkownika zmienione", Toast.LENGTH_SHORT).show();
    }
}