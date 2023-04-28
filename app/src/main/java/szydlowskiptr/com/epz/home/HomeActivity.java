package szydlowskiptr.com.epz.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.rollbar.android.Rollbar;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.basket.BasketFragment;
import szydlowskiptr.com.epz.activity.basket.BasketFragmentWithItems;
import szydlowskiptr.com.epz.activity.category.CategoryFragment;
import szydlowskiptr.com.epz.activity.loginRegister.LoginActivity;
import szydlowskiptr.com.epz.address.AddressListActivity;
import szydlowskiptr.com.epz.interfacesCaller.IMethodCaller;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.product.DetailsProductActivity;
import szydlowskiptr.com.epz.product.ProductPerCategoryFragment;
import szydlowskiptr.com.epz.profile.ProfileFragment;
import szydlowskiptr.com.epz.profile.ProfileLoginFragment;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.sliderSearch.SearchFragment;

public class HomeActivity extends AppCompatActivity implements IMethodCaller, SharedPreferences.OnSharedPreferenceChangeListener {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    CategoryFragment categoryFragment = new CategoryFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    ProfileLoginFragment profileLoginFragment = new ProfileLoginFragment();
    ProductPerCategoryFragment productPerCategoryFragment = new ProductPerCategoryFragment();
    SearchFragment searchFragment = new SearchFragment();
    FloatingActionButton fabBasket;
    TextView text_count;
    String counter;
    CartRepository cartRepository = new CartRepository(HomeActivity.this, "HOME_ACT_TAG");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_without_log_in);
        PrefConfig.registerPref(this, this);
        setView();
        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        menuItemSelected();
        clickBasketIcon();
        text_count.setVisibility(View.INVISIBLE);
        setBasketTotal();
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(this));
        Rollbar.init(this);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(this));
    }

    public void setBasketTotal() {
        if (PrefConfig.loadBasketTotalFromPref(this) != null) {
            if (!PrefConfig.loadBasketTotalFromPref(this).matches("0.00")) {
                text_count.setVisibility(View.VISIBLE);
//                text_count.setText(PrefConfig.loadBasketTotalFromPref(this) + " zł"); ll
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBasketTotal();
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(this));
    }

    private void setView() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        fabBasket = findViewById(R.id.fab);
        text_count = findViewById(R.id.text_count);
    }

    private void clickBasketIcon() {
        String userIdFromPref = PrefConfig.loadUserIdFromPref(this);
        String loadCartItemFromPref = PrefConfig.loadCartItemFromPref(this);
        fabBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userIdFromPref.equals("0")) {
                    BasketFragment fragment = new BasketFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                } else {
                    if (!(loadCartItemFromPref.equals("0"))) {
                        BasketFragmentWithItems basketFragmentWithItems = new BasketFragmentWithItems();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, basketFragmentWithItems).commit();
                    } else {
                        BasketFragment fragment = new BasketFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    }
                }
            }
        });
    }

    private void menuItemSelected() {
        String userIdFromPref = PrefConfig.loadUserIdFromPref(this);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment, "HOME_FRAGMENT_TAG").commit();
                        return true;
                    case R.id.category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, categoryFragment, "CATEGORY_FRAGMENT_TAG").commit();
                        return true;
                    case R.id.profile:
                        if (userIdFromPref.equals("0")) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment, "PROFILE_FRAGMENT_TAG").commit();
                        } else {
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, profileLoginFragment, "PROFILE_LOGIN_FRAGMENT_TAG").commit();
                        }
                        return true;
                    case R.id.product:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, productPerCategoryFragment, "PRODUCT_PER_CATEGORY_FRAGMENT_TAG").commit();
                        return true;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment, "SEARCH_FRAGMENT_TAG").commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (homeFragment != null && homeFragment.isVisible()) {
            new AlertDialog.Builder(this)
                    .setMessage("Czy jestes pewien, że chcesz wyjść z aplikacji?")
                    .setCancelable(false)
                    .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("NIE", null)
                    .show();
        }
//        else {
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, this.homeFragment).commit();
//            bottomNavigationView.setSelectedItemId( bottomNavigationView.getSelectedItemId());
//        }
    }

    @Override
    public void showLogInDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Upss...")
                .setMessage("Wygląda na to, że nie jesteś zalogowany")
                .setCancelable(true)
                .setPositiveButton("Zaloguj się do aplikacji", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    @Override
    public void moveToProducts() {

    }

    @Override
    public void moveToProductDescription(String prodId) {
        PrefConfig.saveProdIdInPref(this, prodId);
        Intent i = new Intent(getApplicationContext(), DetailsProductActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
    }

    @Override
    public void moveToProductsPerCat(String catId) {
        PrefConfig.saveProdByCatIdInPref(this, catId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, productPerCategoryFragment)
                .commit();
    }

    @Override
    public void giveAnAddressPopUp() {
        new AlertDialog.Builder(this)
                .setTitle("Upss...")
                .setMessage("Potrzebujemy Twojego adresu żeby pokazać Ci aktualnie dostępne produkty")
                .setCancelable(true)
                .setPositiveButton("Podaj adres", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), AddressListActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    public void notifyOnResponseGetCartFinished() {
        CartModel cartModel = cartRepository.getCartModel();
        PrefConfig.saveCartItemInPref(this, String.valueOf(cartModel.getItems().size()));
        setBasketTotal();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PrefConfig.BASKET_TOTAL_PREF)) {
            counter = PrefConfig.loadBasketTotalFromPref(this);
            text_count.setText(counter + " zł");
        }
    }
}