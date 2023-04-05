package szydlowskiptr.com.epz.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import szydlowskiptr.com.epz.model.CategoryModel;

/**
 * Created by Piotr Szydlowski on 10.03.2023
 */
public interface CategoryService {
    @GET("categoryTreeByMag/{magId}")
    Call<List<CategoryModel>> getCategory(@Path("magId") String id);
}
