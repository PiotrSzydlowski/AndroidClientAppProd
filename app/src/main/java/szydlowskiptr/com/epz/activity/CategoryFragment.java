package szydlowskiptr.com.epz.activity;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    ArrayList<Category> categoryDataArrayList = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    SearchView searchView;
    String mag_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        categoryDataArrayList.removeAll(categoryDataArrayList);
        dataList = view.findViewById(R.id.categoryDataList);
        searchView = view.findViewById(R.id.search_category);
        categoryAdapter = new CategoryAdapter(getActivity(), categoryDataArrayList);
        callApiGetCategory();
        clickSearchCategory();
        getPreferences();
        setData();
        parseArray();
        return view;
    }

    private void setData() {
        categoryDataArrayList.add(new Category(10L,"Woda", null, true ));
        categoryDataArrayList.add(new Category(13L,"Pieczywo", null, true ));
        categoryDataArrayList.add(new Category(16L,"Mięso i ryby", null, true ));
        categoryDataArrayList.add(new Category(17L,"Mrożonki", null, true ));
        categoryDataArrayList.add(new Category(18L,"Napoje", null, true ));
        categoryDataArrayList.add(new Category(19L,"Owoce i warzywa", null, true ));
        categoryDataArrayList.add(new Category(20L,"Produkty sypkie", null, true ));
        categoryDataArrayList.add(new Category(21L,"Wędliny", null, true ));
        categoryDataArrayList.add(new Category(22L,"Przekąski", null, true ));
    }

    private void getPreferences() {
        SharedPreferences preferences = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        mag_id = preferences.getString("mag_id", "");
    }

    public void clickSearchCategory() {
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SearchActivity.class);
                startActivity(i);
            }
        });
    }


    private void callApiGetCategory() {
//        //TODO    @GetMapping("/categoryTreeByMag/mag_id") zmieni[c endpoint na odpytywanie defoultowego magazynu , uzyc String mag_id
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.100.4:9193/prod/api/categories/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        CategoryService categoryservice = retrofit.create(CategoryService.class);
//        Call<List<Category>> call = categoryservice.getCategory();
//        call.enqueue(new Callback<List<Category>>() {
//            @Override
//            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Category> body = response.body();
//                    for (Category p : body) {
//                        categoryDataArrayList.add(p);
//                    }
//                    parseArray();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Category>> call, Throwable t) {
//                try {
//                    final ProgressDialog dialog = new ProgressDialog(getActivity());
//                    dialog.setMessage("Nasze serwery mają tymczasowe problemy. Spróbuj za chwilę");
//                    dialog.setCancelable(true);
//                    dialog.show();
//                } catch (Exception e) {
//                    Log.d("ERROR", "nie załadowano komunikatu");
//                }
//            }
//        });
    }

    private void parseArray() {
        try {
            categoryAdapter = new CategoryAdapter(getActivity(), categoryDataArrayList);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(categoryAdapter);
    }
}