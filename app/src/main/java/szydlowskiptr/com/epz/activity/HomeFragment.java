package szydlowskiptr.com.epz.activity;

import static android.content.Context.MODE_PRIVATE;
import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.service.ProductService;

public class HomeFragment extends Fragment {
    SliderView sliderView;
    Button searchBtn;
    int[] images = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};

    ArrayList<Product> promoProductsArrayList = new ArrayList<>();
    ArrayList<Product> hitProductsArrayList = new ArrayList<>();

    RecyclerView promoRecyclerView;
    RecyclerView hitRecyclerView;
    ProductAdapter productAdapter;
    View promoView;
    View hitView;
    Button addAddressBtn;
    CardView cardView;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        promoProductsArrayList.removeAll(promoProductsArrayList);
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        setView(view);
//        setPromoRecycler();
        setSlider();
        clickSearchBtnMain();
        setHitRecycler();
        callLoginDialog();
        callApiGetPromoProducts();
        if (hitProductsArrayList.isEmpty()) {
            hitProductsArrayList = GetList.getAllProducts();
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
        productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList);
        promoRecyclerView.setAdapter(productAdapter);
    }

    private void setHitRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(hitView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        hitRecyclerView.setLayoutManager(linearLayoutManager);
        hitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), hitProductsArrayList);
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
        cardView = view.findViewById(R.id.card_viewLeft);
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

    private void callApiGetPromoProducts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getHitProducts(sp.getString("mag_id", null));
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> body = response.body();
                    promoProductsArrayList.addAll(body);
                    parseArray();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                try {
                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("Nasze serwery mają tymczasowe problemy. Spróbuj za chwilę");
                    dialog.setCancelable(true);
                    dialog.show();
                } catch (Exception e) {
                    Log.d("ERROR", "nie załadowano komunikatu");
                }
            }
        });
    }

    private void parseArray() {
        try {
            productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setPromoRecycler();
    }
}