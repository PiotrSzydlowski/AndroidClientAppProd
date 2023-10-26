package szydlowskiptr.com.epz.activity.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.Helper.PrefConfig;
import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.databinding.FragmentCategoryBinding;
import szydlowskiptr.com.epz.model.Category;
import szydlowskiptr.com.epz.repositories.CategoryRepository;
import szydlowskiptr.com.epz.sliderSearch.SearchFragment;


public class CategoryFragment extends Fragment {

    FragmentCategoryBinding binding;
    ArrayList<Category> categoryDataArrayList = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    SearchFragment searchFragment = new SearchFragment();
    CategoryRepository categoryRepository = new CategoryRepository(CategoryFragment.this);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        categoryDataArrayList.removeAll(categoryDataArrayList);
        categoryAdapter = new CategoryAdapter(getActivity(), categoryDataArrayList);
        PrefConfig.registerPref(getContext());
        callApiGetCategory();
        clickSearchCategory();
        getPreferences();
        parseArray();
        Rollbar.init(getContext());
        return binding.getRoot();
    }


    private void getPreferences() {
        PrefConfig.loadMagIdFromPref(getContext());
    }

    public void clickSearchCategory() {
        binding.searchBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
            }
        });
    }

    private void callApiGetCategory() {
        categoryRepository.callApiGetCategory(  PrefConfig.loadMagIdFromPref(getContext()));
    }

    private void parseArray() {
        try {
            categoryAdapter = new CategoryAdapter(getActivity(), categoryDataArrayList);
        } catch (Exception e) {
            System.out.println("Wczesniejsze wyjscie");
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        binding.categoryDataList.setLayoutManager(gridLayoutManager);
        binding.categoryDataList.setAdapter(categoryAdapter);
    }

    public void notifyOnResponseFinished() {
        List<Category> body = categoryRepository.getAddressList();
        for (Category p : body) {
            categoryDataArrayList.add(p);
        }
        parseArray();
    }
}