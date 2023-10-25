package szydlowskiptr.com.epz.activity.basket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rollbar.android.Rollbar;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.databinding.ActivityCheckoutBinding;
import szydlowskiptr.com.epz.home.HomeActivity;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.CreateOrderAdditionalInfo;
import szydlowskiptr.com.epz.model.CreateOrderModel;
import szydlowskiptr.com.epz.model.ErrorModel;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.service.CreateOrder;

public class CheckoutActivity extends AppCompatActivity {

    ActivityCheckoutBinding binding;

    CartRepository cartRepository = new CartRepository(CheckoutActivity.this, "CHECKOUT_ACT_TAG");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        PrefConfig.registerPref(getApplicationContext());
        callApiToGetCart();
        setCurrentAddress();
        clickOnCreateBtn();
        Rollbar.init(getApplicationContext());
    }

    private void setCurrentAddress() {
        if (PrefConfig.loadAddressDoorNumberFromPref(getApplicationContext()).equals("")) {
            binding.addressFullTextView.setText(PrefConfig.loadPostalCodeFromPref(getApplicationContext()) + " " +
                    PrefConfig.loadCityFromPref(getApplicationContext()) + ", " +
                    PrefConfig.loadAddressStreetFromPref(getApplicationContext()) + " "
                    + PrefConfig.loadAddressStreetNumberFromPref(getApplicationContext()));
        } else {
            binding.addressFullTextView.setText(
                    PrefConfig.loadPostalCodeFromPref(getApplicationContext()) + " " +
                            PrefConfig.loadCityFromPref(getApplicationContext()) + ", " +
                            PrefConfig.loadAddressStreetFromPref(getApplicationContext()) + " "
                            + PrefConfig.loadAddressStreetNumberFromPref(getApplicationContext())
                            + "/" + PrefConfig.loadAddressDoorNumberFromPref(getApplicationContext()));
        }
    }

    private void clickOnCreateBtn() {
       binding.createOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isOpen = PrefConfig.loadOpenFromPref(getApplicationContext());
                String openTemp = PrefConfig.loadTempOpenFromPref(getApplicationContext());
                if (isOpen.equals("false")) {
                    Toast.makeText(CheckoutActivity.this, "Niestety, Sklep jest już zamknięty. Zapraszamy od godziny "
                                    + PrefConfig.loadOpenFromFromPref(getApplicationContext())
                            , Toast.LENGTH_SHORT).show();
                } else if (openTemp.equals("true")) {
                    Toast.makeText(CheckoutActivity.this, "Sklep jest chwilowo zamknięty, wróć do nas za kwadrans"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    CreateOrderAdditionalInfo createOrderAdditionalInfo = new CreateOrderAdditionalInfo();
                    if (binding.switchSlient.isChecked()) {
                        createOrderAdditionalInfo.setSlientDelivery(1);
                    } else {
                        createOrderAdditionalInfo.setSlientDelivery(0);
                    }
                    createOrderAdditionalInfo.setMessage(binding.additionalInfoEdittext.getText().toString());
                    createOrder(createOrderAdditionalInfo);
                }
            }
        });
    }

    public void createOrder(CreateOrderAdditionalInfo createOrderAdditionalInfo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.34:9193/prod/api/orders/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CreateOrder createOrder = retrofit.create(CreateOrder.class);
        Call<CreateOrderModel> call = createOrder.createOrder(PrefConfig.loadUserIdFromPref(getApplicationContext()), createOrderAdditionalInfo);
        call.enqueue(new Callback<CreateOrderModel>() {

            @Override
            public void onResponse(Call<CreateOrderModel> call, Response<CreateOrderModel> response) {
                if (response.code() == 200) {
                    //TODO przejsc na ekran statusu
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                    PrefConfig.saveActiveOrderInPref(getApplicationContext(), "true");
                    finish();
                    Toast.makeText(CheckoutActivity.this, "Order utworzony: " + response.body().getId(), Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new GsonBuilder().create();
                    ErrorModel errorModel;
                    try {
                        errorModel = gson.fromJson(response.errorBody().string(), ErrorModel.class);
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
        binding.orderSumValue.setText(String.valueOf(cartModel.getItemTotal()) + " zł");
        binding.bagSumValue.setText(String.valueOf(cartModel.getBagCost()) + " zł");
        binding.deliverySumValue.setText(String.valueOf(cartModel.getDelivery()) + " zł");
        binding.paySumValue.setText(String.valueOf(cartModel.getTotal()) + " zł");
        binding.itemCounter.setText("Liczba produktów: " + String.valueOf(cartModel.getItems().size()));
    }


    private void callApiToGetCart() {
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(getApplicationContext()));
    }

    public void notifyOnResponseGetCartFinished() {
        CartModel cartModel = cartRepository.getCartModel();
        PrefConfig.saveActiveOrderInPref(getApplicationContext(), String.valueOf(cartModel.isActiveOrder()));
        setFiled(cartModel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
        finish();
    }
}