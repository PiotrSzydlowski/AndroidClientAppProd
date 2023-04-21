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

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.BasketFragment;
import szydlowskiptr.com.epz.activity.category.CategoryFragment;
import szydlowskiptr.com.epz.activity.loginRegister.LoginActivity;
import szydlowskiptr.com.epz.address.AddressListActivity;
import szydlowskiptr.com.epz.interfacesCaller.IMethodCaller;
import szydlowskiptr.com.epz.product.DetailsProductActivity;
import szydlowskiptr.com.epz.product.ProductPerCategoryFragment;
import szydlowskiptr.com.epz.product.ProfileFragment;
import szydlowskiptr.com.epz.profile.ProfileLoginFragment;
import szydlowskiptr.com.epz.sliderSearch.SearchFragment;

public class HomeActivity extends AppCompatActivity implements IMethodCaller {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    CategoryFragment categoryFragment = new CategoryFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    ProfileLoginFragment profileLoginFragment = new ProfileLoginFragment();
    ProductPerCategoryFragment productPerCategoryFragment = new ProductPerCategoryFragment();
    SearchFragment searchFragment = new SearchFragment();
    FloatingActionButton fabBasket;
    TextView text_count;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_without_log_in);
        sp = getApplication().getSharedPreferences("preferences", MODE_PRIVATE);
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
        Rollbar.init(this);
    }

    private void setBasketTotal() {
        if (sp.getString("basket_total", null) != null) {
            if (!sp.getString("basket_total", null).matches("0.00")) {
                text_count.setVisibility(View.VISIBLE);
                text_count.setText(sp.getString("basket_total", null) + " zł");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBasketTotal();
    }

    private void setView() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        fabBasket = findViewById(R.id.fab);
        text_count = findViewById(R.id.text_count);
    }

    private void clickBasketIcon() {
        fabBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BasketFragment fragment = new BasketFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
    }

    private void menuItemSelected() {
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
                        if (sp.getString("user_id", null).equals("0")) {
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
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("product_id", prodId);
        editor.apply();
        Intent i = new Intent(getApplicationContext(), DetailsProductActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
    }

    @Override
    public void moveToProductsPerCat(String catId) {
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("product_by_cat_id", catId);
        editor.apply();
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
}