package szydlowskiptr.com.epz.activity;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.loginRegister.LoginActivity;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.model.ResponseModel;
import szydlowskiptr.com.epz.product.ProductAdapter;
import szydlowskiptr.com.epz.service.CartService;
import szydlowskiptr.com.epz.service.ProductService;


public class SearchFragment extends Fragment implements IMethodCaller {

    private View searchLabel;
    private TextView searchText;
    private SearchView search_view_on_search;
    private RecyclerView searchRecyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> searchedProductArrayList = new ArrayList<>();
    CartModel cartByUser;
    final Handler handler = new Handler();
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        setView(view);
        search();
        callApiToGetCart();
        Rollbar.init(getContext());
        return view;
    }

    private void setView(View view) {
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
        searchLabel = view.findViewById(R.id.search_view_on_search);
        search_view_on_search = view.findViewById(R.id.search_view_on_search);
//        emptyView = findViewById(R.id.empty_view);
        searchText = view.findViewById(R.id.searchText);
    }

    private void setProductRecycler() {
        productAdapter = new ProductAdapter(getContext(), searchedProductArrayList, cartByUser, SearchFragment.this, "SEARCH_FR");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        searchRecyclerView.setLayoutManager(gridLayoutManager);
        searchRecyclerView.setAdapter(productAdapter);
    }

    private void callApiToGetCart() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/basket/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CartService cartService = retrofit.create(CartService.class);
        Call<CartModel> call = cartService.getCart(sp.getString("user_id", null));
        call.enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CartModel body = response.body();
                    cartByUser = body;
                }
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
            }
        });
    }

    @Override
    public void showLogInDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Upss...")
                .setMessage("Wygląda na to, że nie jesteś zalogowany")
                .setCancelable(true)
                .setPositiveButton("Zaloguj się do aplikacji", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }

    private void search() {
        search_view_on_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String query = String.valueOf(search_view_on_search.getQuery());
                if (!query.isEmpty()) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callApiSearch();
                        }
                    }, 800);
                }
                return false;
            }
        });
    }

    private void setTextLabelForSearching() {
        if (searchedProductArrayList.size() == 1) {
            searchText.setText(searchedProductArrayList.size() + " wynik");
        } else if (searchedProductArrayList.size() > 1 && searchedProductArrayList.size() < 5) {
            searchText.setText(searchedProductArrayList.size() + " wyniki");
        } else if (searchedProductArrayList.size() > 9) {
            searchText.setText(searchedProductArrayList.size() + " wyników");
        } else if (searchedProductArrayList.size() == 0) {
            searchText.setText("Brak wynków dla wyszukiwania: " + search_view_on_search.getQuery());
        }
    }

    private void callApiSearch() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/stocks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getProductsBySearch(sp.getString("mag_id", null), String.valueOf(search_view_on_search.getQuery()));
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> body = response.body();
                    searchedProductArrayList.removeAll(searchedProductArrayList);
                    searchedProductArrayList.addAll(body);
                }
                setProductRecycler();
                setTextLabelForSearching();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    @Override
    public void moveToProducts() {

    }

    @Override
    public void moveToProductDescription(String prodId) {

    }

    @Override
    public void moveToProductsPerCat(String catId) {

    }

    @Override
    public void giveAnAddressPopUp() {

    }

    public void addToCart(String stockItemId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/basket/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CartService cartService = retrofit.create(CartService.class);
        Call<ResponseModel> call = cartService.addItemToCart(stockItemId, "1", sp.getString("user_id", null));
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
    }

    public void getCart() {
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callApiToGetCart();
    }

    public void removeFromCart(String stockItemId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.100.4:9193/prod/api/basket/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CartService cartService = retrofit.create(CartService.class);
        Call<ResponseModel> call = cartService.removeItemFromCart(stockItemId, "1", sp.getString("user_id", null));
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
    }
}