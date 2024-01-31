package szydlowskiptr.com.epz.profile;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.databinding.ActivityCheckoutBinding;
import szydlowskiptr.com.epz.databinding.ActivityProfileDataBinding;

public class ProfileDataActivity extends AppCompatActivity {

    ActivityProfileDataBinding binding;

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
                String userId = PrefConfig.loadUserIdFromPref(getApplicationContext());
                Editable emailTextView = binding.emailEdittext.getText();
                Editable birthdayText = binding.birthday.getText();
                Editable phoneEdittextText = binding.phoneEdittext.getText();

                if (!emailTextView.equals(null) ||!birthdayText.equals(null) || !phoneEdittextText.equals(null)){
                    Toast.makeText(getApplicationContext(), PrefConfig.loadUserIdFromPref(getApplicationContext()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteAccount(){
        binding.idBtnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}