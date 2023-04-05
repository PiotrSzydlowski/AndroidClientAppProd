package szydlowskiptr.com.epz.activity;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import szydlowskiptr.com.epz.model.CategoryModel;
import szydlowskiptr.com.epz.service.CategoryService;


public class CategoryFragment extends Fragment {

    RecyclerView categoryRecyclerView;
    ArrayList<CategoryModel> categoryModelDataArrayList = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    Button searchView;
    String mag_id;
    SharedPreferences sp;
//    ShimmerFrameLayout shimmerContainer;
    SearchFragment searchFragment = new SearchFragment();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        categoryModelDataArrayList.removeAll(categoryModelDataArrayList);
        categoryRecyclerView = view.findViewById(R.id.categoryDataList);
//        shimmerContainer = view.findViewById(R.id.shimmer_view_container);
        searchView = view.findViewById(R.id.searchBtnMain);
        categoryAdapter = new CategoryAdapter(getActivity(), categoryModelDataArrayList);
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        callApiGetCategory();
        clickSearchCategory();
        getPreferences();
        parseArray();
        return view;
    }


    private void getPreferences() {
        SharedPreferences preferences = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        mag_id = preferences.getString("mag_id", "");
    }

    public void clickSearchCategory() {
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
            }
        });
    }

    private void callApiGetCategory() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/categories/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CategoryService categoryservice = retrofit.create(CategoryService.class);
        Call<List<CategoryModel>> call = categoryservice.getCategory(sp.getString("mag_id", null));
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoryModel> body = response.body();
                    for (CategoryModel p : body) {
                        categoryModelDataArrayList.add(p);
                    }
                    parseArray();
                }
            }
            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
            }
        });
    }

    private void parseArray() {
        try {
            categoryAdapter = new CategoryAdapter(getActivity(), categoryModelDataArrayList);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }
}