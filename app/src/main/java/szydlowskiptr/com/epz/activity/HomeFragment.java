package szydlowskiptr.com.epz.activity;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import java.util.ArrayList;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Product;

public class HomeFragment extends Fragment {
    SliderView sliderView;
    Button searchBtn;
    int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};

    ArrayList<Product> allProducts = new ArrayList<>();

    RecyclerView promoRecyclerView;
    RecyclerView hitRecyclerView;
    ProductAdapter productAdapter;
    View promoView;
    View hitView;
    Button addAddressBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }

        setView(view);
        setPromoRecycler();
        setSlider();
        clickSearchBtnMain();
        setHitRecycler();
        callLoginDialog();
        if (allProducts.isEmpty()) {
            allProducts = GetList.getAllProducts();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setPromoRecycler();
        setSlider();
        clickSearchBtnMain();
        setHitRecycler();
    }

    private void setPromoRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(promoView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        promoRecyclerView.setLayoutManager(linearLayoutManager);
        promoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), allProducts);
        promoRecyclerView.setAdapter(productAdapter);
    }

    private void setHitRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(hitView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        hitRecyclerView.setLayoutManager(linearLayoutManager);
        hitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), allProducts);
        hitRecyclerView.setAdapter(productAdapter);
    }

    private void setView(View view) {
        sliderView = view.findViewById(R.id.image_slider);
        searchBtn = view.findViewById(R.id.searchBtnMain);
        promoRecyclerView = view.findViewById(R.id.promo_recycler_view);
        hitRecyclerView = view.findViewById(R.id.hit_recycler_view);
        promoView = view.findViewById(R.id.linear_for_promo_recycler);
        hitView = view.findViewById(R.id.linear_for_hit_recycler);
        addAddressBtn = view.findViewById(R.id.addAddressBtnMain);
    }

    private void setSlider() {
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
    }

    private void callLoginDialog() {
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivityWithoutLogIn) getActivity()).showLogInDialog();
            }
        });
    }

    public void clickSearchBtnMain() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SearchActivity.class);
                startActivity(i);
            }
        });
    }
}