package szydlowskiptr.com.epz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rollbar.android.Rollbar;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.repositories.AddressRepository;
import szydlowskiptr.com.epz.model.AddAddressModel;
import szydlowskiptr.com.epz.model.AddressModel;
import szydlowskiptr.com.epz.service.AddressesService;

public class AddAddressActivity extends AppCompatActivity implements Notify {

    SharedPreferences sp;
    EditText idEdtStreet, idEdtStreetNumber, idEdtAprtNumber, idEdtPostaCode, idEdtCity,
            idEdtFloor, idInstraction;
    Button logInBtn;
    private AddressRepository repository = new AddressRepository(AddAddressActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        sp = getApplication().getSharedPreferences("preferences", MODE_PRIVATE);
        setView();
        clickSaveBtn();
        Rollbar.init(this);
    }

    private void setView() {
        idEdtStreet = findViewById(R.id.idEdtStreet);
        idEdtStreetNumber = findViewById(R.id.idEdtStreetNumber);
        idEdtAprtNumber = findViewById(R.id.idEdtAprtNumber);
        idEdtPostaCode = findViewById(R.id.idEdtPostaCode);
        idEdtCity = findViewById(R.id.idEdtCity);
        idEdtFloor = findViewById(R.id.idEdtFloor);
        idInstraction = findViewById(R.id.idInstraction);
        logInBtn = findViewById(R.id.LogInBtn);
    }

    private void clickSaveBtn() {
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idEdtStreet.getText().toString().matches("") || idEdtStreetNumber.getText().toString().matches("")
                        || idEdtPostaCode.getText().toString().matches("")
                        || idEdtCity.getText().toString().matches("")) {
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
            System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh " + repository.getAddressList().toString());
            SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("mag_id", String.valueOf(collect.get(0).getMagId()));
            editor.putString("address_door_number", collect.get(0).getDoorNumber());
            editor.putString("address_street", collect.get(0).getStreet());
            editor.putString("address_street_number", collect.get(0).getStreetNumber());
            editor.putString("city", collect.get(0).getCity());
            editor.putString("postal_code", collect.get(0).getPostalCode());
            editor.apply();
            Intent i = new Intent(AddAddressActivity.this, AddressListActivity.class);
            startActivity(i);
        }
    }

    private void callAddAddressApi() {
        AddAddressModel addAddressModel = new AddAddressModel();
        addAddressModel.setStreet(idEdtStreet.getText().toString());
        addAddressModel.setStreet_number(idEdtStreetNumber.getText().toString());
        addAddressModel.setDoor_number(idEdtAprtNumber.getText().toString());
        addAddressModel.setPostal_code(idEdtPostaCode.getText().toString());
        addAddressModel.setCity(idEdtCity.getText().toString());
        addAddressModel.setFlor(idEdtFloor.getText().toString());
        addAddressModel.setMessage(idInstraction.getText().toString());
        repository.addApiAddress(sp.getString("user_id", null), addAddressModel, "");
    }

    @Override
    public void notifyOnResponseFinished() {
        getList();
    }
}
