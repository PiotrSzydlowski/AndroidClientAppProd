package szydlowskiptr.com.epz.product;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rollbar.android.Rollbar;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.repositories.ProductRepository;

public class DetailsProductActivity extends AppCompatActivity {

    SharedPreferences sp;
    ImageView imageView;
    TextView detailProductText;
    TextView detailProductDescription;
    TextView detailProductPrice;
    ImageView backArrowBtn;
    ProductRepository productRepository = new ProductRepository(DetailsProductActivity.this, "DETAIL_PRODUCT_ACT");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        sp = getSharedPreferences("preferences", MODE_PRIVATE);
        setView();
        callApiGetProductsById();
        backBtn();
        Rollbar.init(this);
    }

    private void backBtn() {
        backArrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setView() {
        imageView = findViewById(R.id.detailsProductImg);
        detailProductText = findViewById(R.id.detailProductText);
        detailProductDescription = findViewById(R.id.detailProductDescription);
        detailProductPrice = findViewById(R.id.detailProductPrice);
        backArrowBtn = findViewById(R.id.backArrowDetailsProduct);
    }

    private void callApiGetProductsById() {
        productRepository.callApiGetProductsById(sp.getString("product_id", null), sp.getString("mag_id", null));
    }

    public void notifyOnResponseGetProductByIdFinished() {
        Product productById = productRepository.getProductById();
        Glide.with(getApplicationContext())
                .load(productById.getPhoto())
                .placeholder(R.drawable.placeholder)
                .into(imageView);
        detailProductText.setText(productById.getProductsName());
        detailProductDescription.setText(productById.getProductDescription());
        detailProductPrice.setText(productById.getPrice() + " zł");
    }
}