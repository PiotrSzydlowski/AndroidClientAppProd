package szydlowskiptr.com.epz.home;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rollbar.android.Rollbar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.status.StatusActivity;
import szydlowskiptr.com.epz.address.AddressListActivity;
import szydlowskiptr.com.epz.databinding.FragmentHomeBinding;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.model.SlidersModel;
import szydlowskiptr.com.epz.product.ProductAdapter;
import szydlowskiptr.com.epz.product.ProductPerCategoryFragment;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.repositories.ProductRepository;
import szydlowskiptr.com.epz.sliderSearch.SearchFragment;
import szydlowskiptr.com.epz.sliderSearch.SliderAdapter;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ArrayList<Product> promoProductsArrayList = new ArrayList<>();
    ArrayList<Product> hitProductsArrayList = new ArrayList<>();
    CartModel cartByUser;
    ProductAdapter productAdapter;
    SearchFragment searchFragment = new SearchFragment();
    ProductRepository productRepository = new ProductRepository(HomeFragment.this, "HOME_FR");
    CartRepository cartRepository = new CartRepository(HomeFragment.this, "HOME_FR");
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        PrefConfig.registerPref(getContext());
        setSliders();
        callApiToGetCart();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        promoProductsArrayList.removeAll(promoProductsArrayList);
        hitProductsArrayList.removeAll(hitProductsArrayList);
        setSlider();
        clickSearchBtnMain();
        callLoginDialog();
        callApiGetPromoProducts();
        callApiGetHitProducts();
        clickPromoCard();
        clickNewCard();
        getCart();
        setAddressData();
        displayMessageIntoCustomer();
        displayPopUpTempOpen();
        Rollbar.init(getContext());
        scheduledExecutor();
        clickSaleCard();
        return binding.getRoot();
    }

    private void scheduledExecutor() {
        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        getCart();
                    }
                }, 0, 15, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate
                (new Runnable() {
                    public void run() {
                        callApiGetPromoProducts();
                    }
                }, 0, 45, TimeUnit.SECONDS);
    }

    private void displayPopUpTempOpen() {
        String openTemp = PrefConfig.loadTempOpenFromPref(getContext());
        if (openTemp.equals("true")) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Sklep jest chwilowo zamknięty")
                    .setMessage("Łapiemy sekundę oddechu, wróć do nas za kwadrans")
                    .setCancelable(false)
                    .setPositiveButton("Zamknij", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                    .show();
        }
    }

    private void
    displayMessageIntoCustomer() {
        String activeOrder = PrefConfig.loadActiveOrderFromPref(getContext());
        String userPref = PrefConfig.loadUserIdFromPref(getContext());
        String userBanned = PrefConfig.loadIfUserBannedFromPref(getContext());
        String open = PrefConfig.loadOpenFromPref(getContext());
        String openTo = PrefConfig.loadTempOpenToFromPref(getContext());
        String openTemp = PrefConfig.loadTempOpenFromPref(getContext());
        String openFrom = PrefConfig.loadOpenFromFromPref(getContext());
        if (userBanned.equals("true")) {
            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.custom_dialog_layout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.show();

        }
        if (!userPref.equals("0")) {
            if (activeOrder.equals("true")) {
                binding.messageActiveOrder.setText(PrefConfig.loadOrderMsgFromPref(getContext()));
                binding.activeOrderLayout.setVisibility(View.VISIBLE);
                binding.activeOrderLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getActivity(), StatusActivity.class);
                        startActivity(i);
                    }
                });
            } else {
                binding.activeOrderLayout.setVisibility(View.INVISIBLE);
            }
        } else {
            binding.activeOrderLayout.setVisibility(View.INVISIBLE);
        }
        if (open.equals("false")) {
            binding.messageOpenStore.setText("Sklep obecnie jest zamknięty. Zapraszamy codziennie od " + openFrom + " do " + openTo);
            binding.messageOpenStore.setVisibility(View.VISIBLE);
        } else {
            binding.messageOpenStore.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setSliders();
        promoProductsArrayList.removeAll(promoProductsArrayList);
        hitProductsArrayList.removeAll(hitProductsArrayList);
        callApiGetPromoProducts();
        callApiGetHitProducts();
        callApiToGetCart();
        setAddressData();
        displayMessageIntoCustomer();
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.linearForPromoRecycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.promoRecyclerView.setLayoutManager(linearLayoutManager);
        binding.promoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList, cartByUser, HomeFragment.this, "HOME_FR");
        binding.promoRecyclerView.setAdapter(productAdapter);
    }

    private void setHitRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(binding.hitRecyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.hitRecyclerView.setLayoutManager(linearLayoutManager);
        binding.hitRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), hitProductsArrayList, cartByUser, HomeFragment.this, "HOME_FR");
        binding.hitRecyclerView.setAdapter(productAdapter);
    }

    private void setAddressData() {
        String mag_id = PrefConfig.loadMagIdFromPref(getContext());
        if (!mag_id.equals("3")) {
            if (PrefConfig.loadAddressDoorNumberFromPref(getContext()).equals("")) {
                binding.addAddressBtnMain.setText(PrefConfig.loadPostalCodeFromPref(getContext()) + " " +
                        PrefConfig.loadCityFromPref(getContext()) + ", " +
                        PrefConfig.loadAddressStreetFromPref(getContext()) + " "
                        + PrefConfig.loadAddressStreetNumberFromPref(getContext()));
            } else {
                binding.addAddressBtnMain.setText(
                        PrefConfig.loadPostalCodeFromPref(getContext()) + " " +
                                PrefConfig.loadCityFromPref(getContext()) + ", " +
                                PrefConfig.loadAddressStreetFromPref(getContext()) + " "
                                + PrefConfig.loadAddressStreetNumberFromPref(getContext())
                                + "/" + PrefConfig.loadAddressDoorNumberFromPref(getContext()));
            }
        }
    }

    private void setSlider() {
        SliderAdapter sliderAdapter = new SliderAdapter(setSliders());
        binding.imageSlider.setSliderAdapter(sliderAdapter);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        binding.imageSlider.startAutoCycle();
    }

    private void callApiToGetCart() {
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(getContext()));
    }

    private void callLoginDialog() {
        binding.addAddressBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_id = PrefConfig.loadUserIdFromPref(getContext());
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
        binding.searchBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
            }
        });
    }

    private void callApiGetPromoProducts() {
        productRepository.callApiGetPromoProducts(PrefConfig.loadMagIdFromPref(getContext()));
    }

    private void parseArrayPromoProducts() {
        try {
            productAdapter = new ProductAdapter(getActivity(), promoProductsArrayList, cartByUser, HomeFragment.this, "HOME_FR");
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie ");
        }
        setPromoRecycler();
    }

    private void callApiGetHitProducts() {
        productRepository.callApiGetHitProducts(PrefConfig.loadMagIdFromPref(getContext()));
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
        binding.promoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefConfig.saveProdByCatIdInPref(getContext(), "16");
                ProductPerCategoryFragment productPerCategoryFragment = new ProductPerCategoryFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, productPerCategoryFragment)
                        .commit();
            }
        });
    }

    private void clickNewCard() {
        binding.newCardProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefConfig.saveProdByCatIdInPref(getContext(), "17");
                ProductPerCategoryFragment productPerCategoryFragment = new ProductPerCategoryFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, productPerCategoryFragment)
                        .commit();
            }
        });
    }

    private void clickSaleCard() {
        binding.saleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefConfig.saveProdByCatIdInPref(getContext(), "18");
                ProductPerCategoryFragment productPerCategoryFragment = new ProductPerCategoryFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.container, productPerCategoryFragment)
                        .commit();
            }
        });
    }

    public void addToCart(String stockItemId) {
        cartRepository.addToCart(stockItemId, PrefConfig.loadUserIdFromPref(getContext()));
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
        cartRepository.removeFromCart(stockItemId, PrefConfig.loadUserIdFromPref(getContext()));
    }

    public void notifyOnResponseGetCartFinished() {
        cartByUser = cartRepository.getCartModel();
        setHitRecycler();
        setPromoRecycler();
        System.out.println("ggggggggggggggggggggggggggggggggggggggggggdddddddddddddddddddddddddddddddddddddddd " + cartByUser.isActiveOrder());
        PrefConfig.saveOrderMessageInPref(getContext(), cartByUser.getMessage());
        PrefConfig.saveBasketTotalInPref(getContext(), String.valueOf(cartByUser.getTotal()));
        PrefConfig.saveCartItemInPref(getContext(), String.valueOf(cartByUser.getItems().size()));
        PrefConfig.saveActiveOrderInPref(getContext(), String.valueOf(cartByUser.isActiveOrder()));
        PrefConfig.saveIfOpenInPref(getContext(), String.valueOf(cartByUser.isOpen()));
        PrefConfig.saveIfTempOpenInPref(getContext(), String.valueOf(cartByUser.isTempOpen()));
        PrefConfig.saveIfUserBannedInPref(getContext(), String.valueOf(cartByUser.isUserBanned()));
        if (cartByUser.isEmptyBasket()) {
            PrefConfig.saveEmptyBasketInPref(getContext(), "true");
        } else {
            PrefConfig.saveEmptyBasketInPref(getContext(), "false");
        }
        displayMessageIntoCustomer();
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
}