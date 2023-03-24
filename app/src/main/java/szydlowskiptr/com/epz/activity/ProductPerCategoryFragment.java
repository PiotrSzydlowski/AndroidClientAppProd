package szydlowskiptr.com.epz.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Category;
import szydlowskiptr.com.epz.model.Product;

public class ProductPerCategoryFragment extends Fragment {

    ArrayList<Product> products = new ArrayList<>();
    RecyclerView productsRecyclerView;
    View productView;
    ProductAdapter productAdapter;
    ImageView backArrowProductByCat;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_per_category, container, false);

        if (products.isEmpty()) {
            for (int i = 0; i < 50; i++) {
                products.add(new Product(1L, "123456987gf", "Wawrzyniec12",
                        "pasta z ciecierzycą", null, null, true,
                        null, null, null, null,
                        R.drawable.product, false, 300));
            }
        }
        setView(view);
        setProductRecycler();
        clickOnBackArrowBtn();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setProductRecycler();
    }

    private void setView(View view) {
        productsRecyclerView = view.findViewById(R.id.recyclerViewProducts);
        productView = view.findViewById(R.id.linear_for_products_recycler);
        backArrowProductByCat = view.findViewById(R.id.backArrowProductByCat);
    }

    private void setProductRecycler() {
        productAdapter = new ProductAdapter(getActivity(), products);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        productsRecyclerView.setLayoutManager(gridLayoutManager);
        productsRecyclerView.setAdapter(productAdapter);
    }

    public void clickOnBackArrowBtn(){
        backArrowProductByCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment cat = new CategoryFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, cat);
                ft.commit();
            }
        });
    }
}