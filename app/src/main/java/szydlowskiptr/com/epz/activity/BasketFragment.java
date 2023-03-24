package szydlowskiptr.com.epz.activity;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Product;

public class BasketFragment extends Fragment {

    Button startShoppingBtn;
    ArrayList<Product> promoItem = new ArrayList<>();
    View promoView;
    RecyclerView promoRecyclerView;
    ProductAdapter productAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }

        setView(view);
        clickStartShoppingBtn();
        setUpDataForRecycler();
        setPromoRecycler();
        return view;
    }

    private void setView(View view) {
        startShoppingBtn = view.findViewById(R.id.start_shopping_btn_cart);
        promoView = view.findViewById(R.id.linear_for_promo_recycler);
        promoRecyclerView = view.findViewById(R.id.promo_recycler_view);
    }

    private void setPromoRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(promoView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        promoRecyclerView.setLayoutManager(linearLayoutManager);
        promoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productAdapter = new ProductAdapter(getActivity(), promoItem);
        promoRecyclerView.setAdapter(productAdapter);
    }

    private void clickStartShoppingBtn() {
        startShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment home = new HomeFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, home);
                ft.commit();
            }
        });
    }


    private void setUpDataForRecycler() {
        if (promoItem.isEmpty()){
        promoItem.add(new Product(1L, "123456987", "Wawrzyniec",
                "pasta z ciecierzycą", null, null, true,
                null, null, null, null,
                R.drawable.product, false, 300));
        promoItem.add(new Product(2L, "4123654789", "Crunchips",
                "fromage chipsy 140g", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(3L, "852123321", "Velvet",
                "ręcznik papierowy Turbo", null, null, true,
                null, null, null, null,
                R.drawable.product, false, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        promoItem.add(new Product(4L, "4120000001", "Monster Energy",
                "Ultra Paradise", null, null, true,
                null, null, null, null,
                R.drawable.product, true, 300));
        }
    }
}