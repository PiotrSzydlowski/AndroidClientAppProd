package szydlowskiptr.com.epz.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import szydlowskiptr.com.epz.model.Category;

/**
 * Created by Piotr Szydlowski on 10.03.2023
 */
public interface CategoryService {
    @GET("categoryTreeByMag/1")
    Call<List<Category>> getCategory();
}
