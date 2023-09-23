package szydlowskiptr.com.epz.repositories;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.activity.category.CategoryFragment;
import szydlowskiptr.com.epz.model.Category;
import szydlowskiptr.com.epz.service.CategoryService;

public class CategoryRepository {

    List<Category> body = new ArrayList<>();
    Fragment fragment;

    public CategoryRepository(Fragment fragment) {
      this.fragment = fragment;
    }

    public void callApiGetCategory(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.15:9193/prod/api/categories/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CategoryService categoryservice = retrofit.create(CategoryService.class);
        Call<List<Category>> call = categoryservice.getCategory(userId);
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    body = response.body();
                    ((CategoryFragment) fragment).notifyOnResponseFinished();
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
            }
        });
    }

    public List<Category> getAddressList() {
        return body;
    }
}
