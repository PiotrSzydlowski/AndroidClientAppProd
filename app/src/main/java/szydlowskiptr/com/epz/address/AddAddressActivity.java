package szydlowskiptr.com.epz.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rollbar.android.Rollbar;

import java.util.List;
import java.util.stream.Collectors;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.databinding.ActivityAddAddressBinding;
import szydlowskiptr.com.epz.interfacesCaller.Notify;
import szydlowskiptr.com.epz.model.AddAddressModel;
import szydlowskiptr.com.epz.model.AddressModel;
import szydlowskiptr.com.epz.repositories.AddressRepository;

public class AddAddressActivity extends AppCompatActivity implements Notify {

    ActivityAddAddressBinding binding;

    private AddressRepository repository = new AddressRepository(AddAddressActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PrefConfig.registerPref(getApplicationContext());
        clickSaveBtn();
        Rollbar.init(this);
    }


    private void clickSaveBtn() {
        binding.LogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.idEdtStreet.getText().toString().matches("") || binding.idEdtStreetNumber.getText().toString().matches("")
                        || binding.idEdtPostaCode.getText().toString().matches("")
                        || binding.idEdtCity.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Uzupełnij dane, pola nie moga być puste", Toast.LENGTH_SHORT).show();
                } else {
                    callAddAddressApi();
                }
            }
        });
    }


    public void getList() {
        List<AddressModel> collect = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            collect = repository.getAddressList()
                    .stream()
                    .filter(x -> x.isCurrent())
                    .collect(Collectors.toList());
            PrefConfig.saveMagIdInPref(getApplicationContext(), String.valueOf(collect.get(0).getMagId()));
            PrefConfig.saveAddressDoorNumberInPref(getApplicationContext(), collect.get(0).getDoorNumber());
            PrefConfig.saveAddressStreetInPref(getApplicationContext(),collect.get(0).getStreet());
            PrefConfig.saveAddressStreetNumberInPref(getApplicationContext(), collect.get(0).getStreetNumber());
            PrefConfig.saveCityInPref(getApplicationContext(), collect.get(0).getCity());
            PrefConfig.savePostalCodeInPref(getApplicationContext(), collect.get(0).getPostalCode());
            Intent i = new Intent(AddAddressActivity.this, AddressListActivity.class);
            startActivity(i);
        }
    }

    private void callAddAddressApi() {
        AddAddressModel addAddressModel = new AddAddressModel();
        addAddressModel.setStreet(binding.idEdtStreet.getText().toString());
        addAddressModel.setStreet_number(binding.idEdtStreetNumber.getText().toString());
        addAddressModel.setDoor_number(binding.idEdtAprtNumber.getText().toString());
        addAddressModel.setPostal_code(binding.idEdtPostaCode.getText().toString());
        addAddressModel.setCity(binding.idEdtCity.getText().toString());
        addAddressModel.setFlor(binding.idEdtFloor.getText().toString());
        addAddressModel.setMessage(binding.idInstraction.getText().toString());
        repository.addApiAddress(PrefConfig.loadUserIdFromPref(getApplicationContext()), addAddressModel, "");
    }

    @Override
    public void notifyOnResponseFinished() {
        getList();
    }
}
