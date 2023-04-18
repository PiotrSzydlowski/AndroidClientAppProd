package szydlowskiptr.com.epz.activity;

import static android.content.Context.MODE_PRIVATE;
import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.rollbar.android.Rollbar;
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
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.model.SlidersModel;
import szydlowskiptr.com.epz.service.CartService;
import szydlowskiptr.com.epz.service.ProductService;

public class HomeFragment extends Fragment {
    SliderView sliderView;
    Button searchBtn;
    ArrayList<Product> promoProductsArrayList = new ArrayList<>();
    ArrayList<Product> hitProductsArrayList = new ArrayList<>();
    CartModel cartByUser;
    RecyclerView promoRecyclerView;
    RecyclerView hitRecyclerView;
    ProductAdapter productAdapter;
    View promoView;
    View hitView;
    Button addAddressBtn;
    CardView promoCard;
    CardView newCardProducts;
    CardView saleCard;
    SharedPreferences sp;
    ShimmerFrameLayout shimmerContainer;
    SearchFragment searchFragment = new SearchFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        setSliders();
        callApiToGetCart();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        promoProductsArrayList.removeAll(promoProductsArrayList);
        hitProductsArrayList.removeAll(hitProductsArrayList);
        setView(view);
        setSlider();
        clickSearchBtnMain();
        callLoginDialog();
        callApiGetPromoProducts();
        callApiGetHitProducts();
        clickPromoCard();
        clickNewCard();
        clickSaleCard();
        Rollbar.init(getContext());
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
//        setSliders();
//        promoProductsArrayList.removeAll(promoProductsArrayList);
//        hitProductsArrayList.removeAll(hitProductsArrayList);
//        callApiGetPromoProducts();
//        callApiGetHitProducts();
//        setAddressData();
//    }

    private ArrayList<SlidersModel> setSliders() {
        ArrayList<SlidersModel> sliders = new ArrayList<>();
        sliders.add(new SlidersModel(1L, "https://lisekappcontentprod.s3.eu-west-1.amazonaws.com/cache/f075bf860506ac9d0fe52fb919a75b1b772d3a5de6403c8f1df4962b504c8b55.jpg"));
        sliders.add(new SlidersModel(2L, "https://lisekappcontentprod.s3.eu-west-1.amazonaws.com/cache/1c675118fb4f156c1a9175997f561b3c387b0efa99e5c1b4fe26ef02ac63fd43.jpg"));
        sliders.add(new SlidersModel(3L, "https://lisekappcontentprod.s3.eu-west-1.amazonaws.com/cache/52d44ca01b817c86014c2e9b911857820dc5b0d508159b0e2a5093e4099bcfac.png"));
        sliders.add(new SlidersModel(4L, "https://lisekappcontentprod.s3.eu-west-1.amazonaws.com/cache/24bfc55ad2d23be0e7faf81e80371893c0134f4de3f8337b796ad01287c110dd.png"));
        return sliders;
    }

    private void setPromoRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(promoView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        promoRecyclerView.setLayoutManager(linearLayoutManager);
        promoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList, cartByUser);
        promoRecyclerView.setAdapter(productAdapter);
    }

    private void setHitRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(hitView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        hitRecyclerView.setLayoutManager(linearLayoutManager);
        hitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), hitProductsArrayList, cartByUser);
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
        promoCard = view.findViewById(R.id.promoCard);
        newCardProducts = view.findViewById(R.id.newCardProducts);
        saleCard = view.findViewById(R.id.saleCard);
        setAddressData();
//        shimmerContainer = view.findViewById(R.id.shimmer_view_container);
    }

    private void setAddressData() {
        String mag_id = sp.getString("mag_id", null);
        if (!mag_id.equals("3")) {
            if (sp.getString("address_door_number", null).equals("")) {
                addAddressBtn.setText(sp.getString("postal_code", null) + " " +
                                sp.getString("city", null) + ", " +
                        sp.getString("address_street", null) + " "
                        + sp.getString("address_street_number", null));
            } else {
                addAddressBtn.setText(
                        sp.getString("postal_code", null) + " " +
                                sp.getString("city", null) + ", " +
                        sp.getString("address_street", null) + " "
                        + sp.getString("address_street_number", null)
                        + "/" + sp.getString("address_door_number", null));
            }
        }
    }

    private void setSlider() {
        SliderAdapter sliderAdapter = new SliderAdapter(setSliders());
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
    }

    private void callApiToGetCart() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/basket/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CartService cartService = retrofit.create(CartService.class);
        Call<CartModel> call = cartService.getCart(sp.getString("user_id", null));
        call.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CartModel body = response.body();
                    cartByUser = body;
                }
            }
            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
            }
        });
    }

    private void callLoginDialog() {
        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = sp.getString("user_id", null);
                if ((!user_id.equals("0"))) {
                    Intent intent = new Intent(getActivity(), AddressListActivity.class);
                    startActivity(intent);
                } else {
                    ((HomeActivity) getActivity()).showLogInDialog();
                }
            }
        });
    }

    public void clickSearchBtnMain() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
            }
        });
    }

    private void callApiGetPromoProducts() {
        ProductService productService = getProductService();
        Call<List<Product>> call = productService.getPromoProducts(sp.getString("mag_id", null));
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> body = response.body();
                    promoProductsArrayList.addAll(body);
                    parseArrayPromoProducts();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @NonNull
    private ProductService getProductService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        return productService;
    }

    private void parseArrayPromoProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList, cartByUser);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setPromoRecycler();
    }

    private void callApiGetHitProducts() {
        ProductService productService = getProductService();
        Call<List<Product>> call = productService.getHitProducts(sp.getString("mag_id", null));
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> body = response.body();
                    hitProductsArrayList.addAll(body);
                    parseArrayHitProducts();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private void parseArrayHitProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), hitProductsArrayList, cartByUser);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setHitRecycler();
    }

    private void clickPromoCard() {
        promoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                16 promo
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("product_by_cat_id", "16");
                editor.apply();
                ProductPerCategoryFragment productPerCategoryFragment = new ProductPerCategoryFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, productPerCategoryFragment)
                        .commit();
            }
        });
    }

    private void clickNewCard() {
        newCardProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("product_by_cat_id", "17");
                editor.apply();
                ProductPerCategoryFragment productPerCategoryFragment = new ProductPerCategoryFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, productPerCategoryFragment)
                        .commit();
            }
        });
    }

    private void clickSaleCard() {
        saleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("product_by_cat_id", "18");
                editor.apply();
                ProductPerCategoryFragment productPerCategoryFragment = new ProductPerCategoryFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, productPerCategoryFragment)
                        .commit();
            }
        });
    }
}