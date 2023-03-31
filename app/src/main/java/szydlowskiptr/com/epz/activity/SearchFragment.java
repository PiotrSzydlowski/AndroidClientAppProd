package szydlowskiptr.com.epz.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Product;


public class SearchFragment extends Fragment implements IMethodCaller{

    private View searchLabel;
    private RecyclerView searchRecyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> searchedProductArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        if (searchedProductArrayList.isEmpty()) {
            searchedProductArrayList = GetList.getAllProducts();
        }
        setView(view);
        setProductRecycler();
        return view;
    }

    private void setView(View view) {
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
        searchLabel = view.findViewById(R.id.search_view_on_search);
//        emptyView = findViewById(R.id.empty_view);
    }

    private void setProductRecycler() {
        productAdapter = new ProductAdapter(getContext(), searchedProductArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(gridLayoutManager);
        searchRecyclerView.setAdapter(productAdapter);
    }

    @Override
    public void showLogInDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Upss...")
                .setMessage("Wygląda na to, że nie jesteś zalogowany")
                .setCancelable(true)
                .setPositiveButton("Zaloguj się do aplikacji", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
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

    }
}