package szydlowskiptr.com.epz.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.service.ProductService;

public class DetailsProductActivity extends AppCompatActivity {

    SharedPreferences sp;
    ImageView imageView;
    TextView detailProductText;
    TextView detailProductDescription;
    TextView detailProductPrice;
//    Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);
        sp = getSharedPreferences("preferences", MODE_PRIVATE);
        setView();
        callApiGetProductsById();
    }



    private void setView() {
        imageView = findViewById(R.id.detailsProductImg);
        detailProductText = findViewById(R.id.detailProductText);
        detailProductDescription = findViewById(R.id.detailProductDescription);
        detailProductPrice = findViewById(R.id.detailProductPrice);
    }

    private void callApiGetProductsById() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<Product> call = productService.getProductById(sp.getString("product_id", null), sp.getString("mag_id", null));
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product>response) {
                if (response.isSuccessful() && response.body() != null) {
                    Glide.with(getApplicationContext())
                            .load(response.body().getPhoto())
                            .placeholder(R.drawable.placeholder)
                            .into(imageView);
                    detailProductText.setText(response.body().getProductsName());
                    detailProductDescription.setText(response.body().getProductDescription());
                    detailProductPrice.setText(response.body().getPrice() + " zł");
                }
            }
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
            }
        });
    }
}