package szydlowskiptr.com.epz.activity;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
    ArrayList<Product> hitItem = new ArrayList<>();

    RecyclerView promoRecyclerView;
    RecyclerView hitRecyclerView;
    PromoAdapter promoAdapter;
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

        promoItem.add(new Product(1L, "123456987", "Wawrzyniec",
                "pasta z ciecierzycą", null, null, true,
                null, null, null, null,
                R.drawable.product, false, 300));
        promoItem.add(new Product(2L, "4123654789", "Crunchips",
                "fromage chipsy 140g", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(3L, "852123321", "Velvet",
                "ręcznik papierowy Turbo", null, null, true,
                null, null, null, null,
                R.drawable.product, false, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));

        setView(view);
        setPromoRecycler();
        setSlider();
        clickSearchBtnMain();
        setHitRecycler();
        callLoginDialog();
        return view;
    }

    @Override
    public void onResume(){
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
        promoAdapter = new PromoAdapter(getActivity(), promoItem);
        promoRecyclerView.setAdapter(promoAdapter);
    }

    private void setHitRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(hitView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        hitRecyclerView.setLayoutManager(linearLayoutManager);
        hitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        promoAdapter = new PromoAdapter(getActivity(), promoItem);
        hitRecyclerView.setAdapter(promoAdapter);
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

   private void callLoginDialog(){
       addAddressBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ((HomeActivityWithoutLogIn)getActivity()).showLogInDialog();
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