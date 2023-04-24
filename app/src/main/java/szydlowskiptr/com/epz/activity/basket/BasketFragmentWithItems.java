package szydlowskiptr.com.epz.activity.basket;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.rollbar.android.Rollbar;

import szydlowskiptr.com.epz.R;

public class BasketFragmentWithItems extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket_with_items, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        Rollbar.init(getContext());
        return view;
    }

    public void ClearBasket(View view) {
    }
}