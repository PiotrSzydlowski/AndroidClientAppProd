package szydlowskiptr.com.epz.profile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}