package szydlowskiptr.com.epz.activity.status;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.databinding.ActivityStatusBinding;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.repositories.CartRepository;

public class StatusActivity extends AppCompatActivity {
    ActivityStatusBinding binding;
    CartRepository cartRepository = new CartRepository(StatusActivity.this, "STATUS_ACT");
    CartModel cartModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatusBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        scheduledExecutor();
        setDeliveryStepView();
        callApiToGetCart();
    }

    private void scheduledExecutor() {
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        callApiToGetCart();
                    }
                }, 0, 45, TimeUnit.SECONDS);
    }

    private void callApiToGetCart() {
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(getApplicationContext()));
    }

    private void setLogicInDeliveryStep() {
        int orderStatus = cartModel.getOrderStatus().getOrderStatusInfo();
        double sum = cartModel.getOrderStatus().getAmount() - cartModel.getBagCost() - 5.99;
        binding.addressTextView.setText(setAddressView());
        binding.cityTextView.setText(cartModel.getOrderStatus().getPostalCode() + " " + cartModel.getOrderStatus().getCity());
        binding.deliveryOrderNumber.setText(String.valueOf(cartModel.getOrderId()));
        binding.deliveryInstructionTextView.setText(cartModel.getOrderStatus().getMessage());
        binding.orderAmountTextView.setText(Math.floor(sum * 100) / 100 + " zł");
        binding.bagCostAmountTextView.setText(cartModel.getBagCost() + " zł");
        binding.deliveryCostTextView.setText(cartModel.getOrderStatus().getDeliveryCost() + " zł" );
        binding.totalAmountTextView.setText(cartModel.getOrderStatus().getAmount() + " zł");



        switch (orderStatus) {
            case 1:
                binding.clockTextInfo.setText(R.string.orderPlaced);
            case 2:
                //zmiana tekstu ponizej
                break;
            case 3:
                binding.stepView.go(1, true);
                binding.clockTextInfo.setText(R.string.orderPicking);
                break;
            case 4:
                binding.stepView.go(2, true);
                binding.clockTextInfo.setText(R.string.orderDelivering);
                break;
            case 5:
                binding.stepView.go(3, true);
                binding.stepView.done(true);
                binding.clockTextInfo.setText(R.string.orderDelivered);
                break;
        }
    }

    private String setAddressView() {
        String street = cartModel.getOrderStatus().getStreet();
        String streetNumber = cartModel.getOrderStatus().getStreetNumber();
        String doorNumber = cartModel.getOrderStatus().getDoorNumber();
        if (!doorNumber.equals("")) {
            return street + " " + streetNumber + "/" + doorNumber;
        }
        return street + " " + streetNumber;
    }

    private void setDeliveryStepView() {
        binding.stepView.getState()
                .steps(new ArrayList<String>() {{
                    add("Zamówienie złożone");
                    add("Zamówienie kompletowane");
                    add("Zamówienie dostarczane");
                    add("Zamówienie dostarczone");
                }})
                .stepsNumber(4)
                .commit();
    }

    public void notifyOnResponseGetCartFinished() {
        cartModel = cartRepository.getCartModel();
        setLogicInDeliveryStep();
    }
}