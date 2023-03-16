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
import szydlowskiptr.com.epz.model.Category;
import szydlowskiptr.com.epz.model.Product;

public class HomeFragment extends Fragment {
    SliderView sliderView;
    Button searchBtn;
    int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};

    ArrayList<Product> promoItem = new ArrayList<>();

    RecyclerView promoRecyclerView;
    PromoAdapter promoAdapter;
    View promoView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }

        promoItem.add(new Product(1L, "123456987", "Wawrzyniec",
                "pasta z ciecierzycą", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(2L, "4123654789", "Crunchips",
                "fromage chipsy 140g", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(3L, "852123321", "Velvet",
                "ręcznik papierowy Turbo", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));

        setView(view);
        setPromoRecycler();
        setSlider();
        clickSearchBtnMain();
        return view;
    }

    private void setPromoRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(promoView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        promoRecyclerView.setLayoutManager(linearLayoutManager);
        promoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        promoAdapter = new PromoAdapter(getActivity(), promoItem);
        promoRecyclerView.setAdapter(promoAdapter);
    }

    private void setView(View view) {
        sliderView = view.findViewById(R.id.image_slider);
        searchBtn = view.findViewById(R.id.searchBtnMain);
        promoRecyclerView = view.findViewById(R.id.promo_recycler_view);
        promoView = view.findViewById(R.id.linear_for_promo_recycler);
    }

    private void setSlider() {
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
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