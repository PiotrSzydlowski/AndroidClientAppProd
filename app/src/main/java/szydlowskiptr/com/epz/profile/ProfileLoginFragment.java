package szydlowskiptr.com.epz.profile;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.rollbar.android.Rollbar;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.loginRegister.LoginActivity;
import szydlowskiptr.com.epz.home.HomeActivity;

public class ProfileLoginFragment extends Fragment {

CardView myData, myCoupons, myOrders;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_login, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        Rollbar.init(getContext());
        setView(view);
        clickOnMyData();
        return view;
    }

    private void setView(View view) {
        myData = view.findViewById(R.id.myData);
        myCoupons = view.findViewById(R.id.myCoupons);
        myOrders = view.findViewById(R.id.myOrders);
    }

    private void clickOnMyData(){
        myData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ProfileDataActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}