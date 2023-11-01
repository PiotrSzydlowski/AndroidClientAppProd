package szydlowskiptr.com.epz.activity.status;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.databinding.ActivityStatusBinding;

public class StatusActivity extends AppCompatActivity {
    ActivityStatusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatusBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.stepView.getState()
                .steps(new ArrayList<String>() {{
                    add("Zamówienie złożone");
                    add("Zamówienie kompletowane");
                    add("Zamówienie dostarczane");
                    add("Zamówienie dostarczone");
                }})
                .stepsNumber(4)
                .commit();

        binding.stepView.go(2, true);
    }
}