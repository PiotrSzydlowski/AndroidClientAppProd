package szydlowskiptr.com.epz.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rollbar.android.Rollbar;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.loginRegister.LoginActivity;
import szydlowskiptr.com.epz.activity.loginRegister.RegisterActivity;


public class ProfileFragment extends Fragment {

    private Button loginBtn, registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        loginBtn = view.findViewById(R.id.idBtnLoginProfileFrag);
        registerBtn = view.findViewById(R.id.idBtnRegisterProfileFrag);
        loginBtnClickListener();
        registerBtnClickListener();
        Rollbar.init(getContext());
        return view;
    }

    private void loginBtnClickListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void registerBtnClickListener() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}