package szydlowskiptr.com.epz.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Category;
import szydlowskiptr.com.epz.service.CategoryService;


public class CategoryFragment extends Fragment {

    RecyclerView dataList;
    ArrayList<Category> dataArrayList = new ArrayList<>();
    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        dataArrayList.removeAll(dataArrayList);
        dataList = (RecyclerView) view.findViewById(R.id.categoryDataList);

        adapter = new Adapter(getActivity(), dataArrayList);
        callApiGetCategory();

        return view;
    }


    private void callApiGetCategory() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Proszę czekać...");
        dialog.setCancelable(false);
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/categories/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CategoryService categoryservice = retrofit.create(CategoryService.class);
        Call<List<Category>> call = categoryservice.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dialog.dismiss();
                    List<Category> body = response.body();
                    for (Category p : body) {
                        dataArrayList.add(p);
                    }
                    parseArray();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), "Coś nie pykło", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseArray() {
        adapter = new Adapter(getActivity(), dataArrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
    }


}