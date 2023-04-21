package szydlowskiptr.com.epz.activity.category;

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

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Category;
import szydlowskiptr.com.epz.repositories.CategoryRepository;
import szydlowskiptr.com.epz.sliderSearch.SearchFragment;


public class CategoryFragment extends Fragment {

    RecyclerView categoryRecyclerView;
    ArrayList<Category> categoryDataArrayList = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    Button searchView;
    String mag_id;
    SharedPreferences sp;
    //    ShimmerFrameLayout shimmerContainer;
    SearchFragment searchFragment = new SearchFragment();
    CategoryRepository categoryRepository = new CategoryRepository(CategoryFragment.this);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        categoryDataArrayList.removeAll(categoryDataArrayList);
        categoryRecyclerView = view.findViewById(R.id.categoryDataList);
//        shimmerContainer = view.findViewById(R.id.shimmer_view_container);
        searchView = view.findViewById(R.id.searchBtnMain);
        categoryAdapter = new CategoryAdapter(getActivity(), categoryDataArrayList);
        sp = getContext().getSharedPreferences("preferences", MODE_PRIVATE);
        callApiGetCategory();
        clickSearchCategory();
        getPreferences();
        parseArray();
        Rollbar.init(getContext());
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
        categoryRepository.callApiGetCategory(sp.getString("mag_id", null));
    }

    private void parseArray() {
        try {
            categoryAdapter = new CategoryAdapter(getActivity(), categoryDataArrayList);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    public void notifyOnResponseFinished() {
        List<Category> body = categoryRepository.getAddressList();
        for (Category p : body) {
            categoryDataArrayList.add(p);
        }
        parseArray();
    }
}