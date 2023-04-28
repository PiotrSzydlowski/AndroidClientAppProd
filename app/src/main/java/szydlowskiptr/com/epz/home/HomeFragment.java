package szydlowskiptr.com.epz.home;

import static android.content.Context.MODE_PRIVATE;
import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.address.AddressListActivity;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.model.SlidersModel;
import szydlowskiptr.com.epz.product.ProductAdapter;
import szydlowskiptr.com.epz.product.ProductPerCategoryFragment;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.repositories.ProductRepository;
import szydlowskiptr.com.epz.sliderSearch.SearchFragment;
import szydlowskiptr.com.epz.sliderSearch.SliderAdapter;

public class HomeFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener{
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
    String total;
    TextView text_count;
    SearchFragment searchFragment = new SearchFragment();
    ProductRepository productRepository = new ProductRepository(HomeFragment.this, "HOME_FR");
    CartRepository cartRepository = new CartRepository(HomeFragment.this, "HOME_FR");

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
            Thread.sleep(400);
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
        onSharedPreferenceChanged(sp, sp.getString("basket_total", null));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        setSliders();
        promoProductsArrayList.removeAll(promoProductsArrayList);
        hitProductsArrayList.removeAll(hitProductsArrayList);
        callApiGetPromoProducts();
        callApiGetHitProducts();
        callApiToGetCart();
        setAddressData();
        onSharedPreferenceChanged(sp, sp.getString("basket_total", null));
    }

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
        productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList, cartByUser, HomeFragment.this, "HOME_FR");
        promoRecyclerView.setAdapter(productAdapter);
    }

    private void setHitRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(hitView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        hitRecyclerView.setLayoutManager(linearLayoutManager);
        hitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), hitProductsArrayList, cartByUser, HomeFragment.this, "HOME_FR");
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
        cartRepository.callApiToGetCart(sp.getString("user_id", null));
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
        productRepository.callApiGetPromoProducts(sp.getString("mag_id", null));
    }

    private void parseArrayPromoProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList, cartByUser, HomeFragment.this, "HOME_FR");
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setPromoRecycler();
    }

    private void callApiGetHitProducts() {
        productRepository.callApiGetHitProducts(sp.getString("mag_id", null));
    }

    private void parseArrayHitProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), hitProductsArrayList, cartByUser, HomeFragment.this, "HOME_FR");
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        setHitRecycler();
    }

    private void clickPromoCard() {
        promoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public void addToCart(String stockItemId) {
        cartRepository.addToCart(stockItemId, sp.getString("user_id", null));
    }

    public void getCart() {
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callApiToGetCart();
    }

    public void removeFromCart(String stockItemId) {
        cartRepository.removeFromCart(stockItemId, sp.getString("user_id", null));
    }

    public void notifyOnResponseGetCartFinished() {
        cartByUser = cartRepository.getCartModel();
        setHitRecycler();
        setPromoRecycler();
        String total = String.valueOf(cartByUser.getTotal());
//        if (!(total == null)) {
//        total = String.valueOf(cartByUser.getTotal());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("basket_total", total);
        editor.putString("cartItem", String.valueOf(cartByUser.getItems().size()));
        editor.apply();
    }

    public void notifyOnResponseGetHitProductsFinished() {
        List<Product> getHitProducts = productRepository.getGetHitProducts();
        hitProductsArrayList.removeAll(hitProductsArrayList);
        hitProductsArrayList.addAll(getHitProducts);
        parseArrayHitProducts();
    }

    public void notifyOnResponseGetPromoProductsFinished() {
        List<Product> body = productRepository.getGetPromoProducts();
        promoProductsArrayList.removeAll(promoProductsArrayList);
        promoProductsArrayList.addAll(body);
        parseArrayPromoProducts();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("basket_total", total);
        editor.apply();
    }
}