package szydlowskiptr.com.epz.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import szydlowskiptr.com.epz.R;

public class BasketFragment extends Fragment {

    Button startShoppingBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        startShoppingBtn = view.findViewById(R.id.start_shopping_btn_cart);
        clickStartShoppingBtn();
        return view;
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
}