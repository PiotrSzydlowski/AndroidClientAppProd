package szydlowskiptr.com.epz.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import szydlowskiptr.com.epz.R;

public class DetailsProductActivity extends AppCompatActivity {

    SharedPreferences sp;
    ImageView imageView;
    TextView detailProductText;
    TextView detailProductDescription;
    TextView detailProductPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        sp = getSharedPreferences("preferences", MODE_PRIVATE);
        setView();
    }

    private void setView() {
        imageView = findViewById(R.id.detailsProductImg);
        detailProductText = findViewById(R.id.detailProductText);
        detailProductDescription = findViewById(R.id.detailProductDescription);
        detailProductPrice = findViewById(R.id.detailProductPrice);
    }
}