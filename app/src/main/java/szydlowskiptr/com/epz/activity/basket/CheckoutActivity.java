package szydlowskiptr.com.epz.activity.basket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rollbar.android.Rollbar;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.loginRegister.LoginActivity;
import szydlowskiptr.com.epz.activity.loginRegister.RegisterActivity;
import szydlowskiptr.com.epz.address.AddAddressActivity;
import szydlowskiptr.com.epz.model.AddAddressModel;
import szydlowskiptr.com.epz.model.AddressModel;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.CreateOrderModel;
import szydlowskiptr.com.epz.model.ErrorModel;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.service.AddressesService;
import szydlowskiptr.com.epz.service.CreateOrder;

public class CheckoutActivity extends AppCompatActivity {

    CartRepository cartRepository = new CartRepository(CheckoutActivity.this, "CHECKOUT_ACT_TAG");
    TextView orderSumValue, bagSumValue, deliverySumValue, paySumValue, itemCounter;
    Button createOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        PrefConfig.registerPref(getApplicationContext());
        callApiToGetCart();
        setView();
        clickOnCreateBtn();
        Rollbar.init(getApplicationContext());
    }

    private void clickOnCreateBtn() {
        createOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder();
            }
        });
    }

        public void createOrder() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.100.4:9193/prod/api/orders/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            CreateOrder createOrder = retrofit.create(CreateOrder.class);
            Call<CreateOrderModel> call = createOrder.createOrder(PrefConfig.loadUserIdFromPref(getApplicationContext()));
            call.enqueue(new Callback<CreateOrderModel>() {

                @Override
                public void onResponse(Call<CreateOrderModel> call, Response<CreateOrderModel> response) {
                    if (response.code() == 200) {
                        //TODO przejsc na ekran statusu
                        //TODO na wczesniejszych ekranach na on pouse ? pobrac koszyk
                        //TODO umiescic info ze user ma aktywne zamowienie na home fragment
                        Toast.makeText(CheckoutActivity.this, "Order utworzony: " + response.body().getId() , Toast.LENGTH_SHORT).show();
                    } else {
                        Gson gson = new GsonBuilder().create();
                        ErrorModel errorModel;
                        try {
                            errorModel = gson.fromJson(response.errorBody().string(),ErrorModel.class);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(getApplicationContext(), errorModel.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<CreateOrderModel> call, Throwable t) {

                }
            });
        }

    private void setFiled(CartModel cartModel) {
        orderSumValue.setText(String.valueOf(cartModel.getItemTotal()) + " zł");
        bagSumValue.setText("0.99 zł");
        deliverySumValue.setText(String.valueOf(cartModel.getDelivery()) + " zł");
        paySumValue.setText(String.valueOf(cartModel.getTotal()) + " zł");
        itemCounter.setText("Liczba produktów: " + String.valueOf(cartModel.getItems().size()));
    }

    private void setView() {
        orderSumValue = findViewById(R.id.order_sum_value);
        bagSumValue = findViewById(R.id.bag_sum_value);
        deliverySumValue = findViewById(R.id.delivery_sum_value);
        paySumValue = findViewById(R.id.pay_sum_value);
        itemCounter = findViewById(R.id.itemCounter);
        createOrderBtn = findViewById(R.id.createOrderBtn);
    }

    private void callApiToGetCart() {
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(getApplicationContext()));
    }

    public void notifyOnResponseGetCartFinished() {
        CartModel cartModel = cartRepository.getCartModel();
        setFiled(cartModel);
    }
}