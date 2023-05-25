package szydlowskiptr.com.epz.sliderSearch;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.activity.loginRegister.LoginActivity;
import szydlowskiptr.com.epz.interfacesCaller.IMethodCaller;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.product.ProductAdapter;
import szydlowskiptr.com.epz.repositories.CartRepository;
import szydlowskiptr.com.epz.repositories.SearchRepository;


public class SearchFragment extends Fragment implements IMethodCaller {

    private View searchLabel;
    private TextView searchText;
    private SearchView search_view_on_search;
    private RecyclerView searchRecyclerView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> searchedProductArrayList = new ArrayList<>();
    CartModel cartByUser;
    final Handler handler = new Handler();
    CartRepository cartRepository = new CartRepository(SearchFragment.this, "SEARCH_FR");
    SearchRepository searchRepository = new SearchRepository(SearchFragment.this, "SEARCH_FR");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        PrefConfig.registerPref(getContext());
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
        cartRepository.callApiToGetCart(PrefConfig.loadUserIdFromPref(getContext()));
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
                    }, 400);
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
        searchRepository.callApiSearch(PrefConfig.loadMagIdFromPref(getContext()), String.valueOf(search_view_on_search.getQuery()));
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
        cartRepository.addToCart(stockItemId, PrefConfig.loadUserIdFromPref(getContext()));
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
        cartRepository.removeFromCart(stockItemId, PrefConfig.loadUserIdFromPref(getContext()));
    }

    public void notifyOnResponseGetCartFinished() {
        CartModel body = cartRepository.getCartModel();
        PrefConfig.saveCartItemInPref(getContext(), String.valueOf(body.getItems().size()));
        cartByUser = body;
        PrefConfig.saveActiveOrderInPref(getContext(), String.valueOf(cartByUser.isActiveOrder()));
        if (cartByUser.isEmptyBasket()) {
            PrefConfig.saveEmptyBasketInPref(getContext(), "true");
        } else {
            PrefConfig.saveEmptyBasketInPref(getContext(), "false");
        }
        PrefConfig.saveCartItemInPref(getContext(), String.valueOf(body.getItems().size()));
        setProductRecycler();
    }


    public void notifyOnResponseGetSearchFinished() {
        List<Product> body = searchRepository.searchedProduct();
        searchedProductArrayList.removeAll(searchedProductArrayList);
        searchedProductArrayList.addAll(body);
        setProductRecycler();
        setTextLabelForSearching();
    }
}