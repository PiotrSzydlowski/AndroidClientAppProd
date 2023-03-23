package szydlowskiptr.com.epz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import szydlowskiptr.com.epz.R;

public class HomeActivityWithoutLogIn extends AppCompatActivity implements IMethodCaller {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    CategoryFragment categoryFragment = new CategoryFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    BasketFragment basketFragment = new BasketFragment();
    ProductPerCategoryFragment productPerCategoryFragment = new ProductPerCategoryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_without_log_in);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        menuItemSelected();
    }

    private void menuItemSelected() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, categoryFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        return true;
                    case R.id.basket:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, basketFragment).commit();
                        return true;
                    case R.id.product:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, productPerCategoryFragment).commit();
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
    public void plusProduct() {

    }

    @Override
    public void minusProduct() {

    }

    @Override
    public void moveToProducts() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ProductPerCategoryFragment())
                .commit();
    }
}