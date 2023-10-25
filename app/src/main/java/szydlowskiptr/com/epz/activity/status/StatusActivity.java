package szydlowskiptr.com.epz.activity.status;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import szydlowskiptr.com.epz.databinding.ActivityStatusBinding;

public class StatusActivity extends AppCompatActivity {
    ActivityStatusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatusBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
}