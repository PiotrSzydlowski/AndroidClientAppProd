package szydlowskiptr.com.epz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Product;

public class SearchActivity extends AppCompatActivity implements IMethodCaller {
    private View searchLabel;
    private RecyclerView searchRecyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> searchedProductArrayList = new ArrayList<>();
//    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (searchedProductArrayList.isEmpty()) {
            searchedProductArrayList = GetList.getAllProducts();
        }
        setView();
        setProductRecycler();
//        if (searchedProductArrayList.isEmpty()) {
//            searchRecyclerView.setVisibility(View.GONE);
//            emptyView.setVisibility(View.VISIBLE);
//        }
//        else {
//            searchRecyclerView.setVisibility(View.VISIBLE);
//            emptyView.setVisibility(View.GONE);
//        }
    }

    private void setView() {
        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        searchLabel = findViewById(R.id.search_view_on_search);
//        emptyView = findViewById(R.id.empty_view);
    }

    private void setProductRecycler() {
        productAdapter = new ProductAdapter(getApplicationContext(), searchedProductArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3, GridLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(gridLayoutManager);
        searchRecyclerView.setAdapter(productAdapter);
    }

    @Override
    public void showLogInDialog() {
        new AlertDialog.Builder(getApplicationContext())
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
    public void moveToProductDescription() {
        Intent i = new Intent(getApplicationContext(), DetailsProductActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_letf);
    }
}