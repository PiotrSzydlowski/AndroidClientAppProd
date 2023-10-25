package szydlowskiptr.com.epz.product;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rollbar.android.Rollbar;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.databinding.ActivityDetailsProductBinding;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.repositories.ProductRepository;

public class DetailsProductActivity extends AppCompatActivity {

    ActivityDetailsProductBinding binding;

    ProductRepository productRepository = new ProductRepository(DetailsProductActivity.this, "DETAIL_PRODUCT_ACT");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PrefConfig.registerPref(getApplicationContext());
        callApiGetProductsById();
        backBtn();
        Rollbar.init(this);
    }

    private void backBtn() {
        binding.backArrowDetailsProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void callApiGetProductsById() {
        productRepository.callApiGetProductsById(PrefConfig.loadProdIdFromPref(getApplicationContext()),PrefConfig.loadMagIdFromPref(getApplicationContext()));
    }

    public void notifyOnResponseGetProductByIdFinished() {
        Product productById = productRepository.getProductById();
        Glide.with(getApplicationContext())
                .load(productById.getPhoto())
                .placeholder(R.drawable.placeholder)
                .into(binding.detailsProductImg);
        binding.detailProductText.setText(productById.getProductsName());
        binding.detailProductDescription.setText(productById.getProductDescription());
        binding.detailProductPrice.setText(productById.getPrice() + " zł");
    }
}