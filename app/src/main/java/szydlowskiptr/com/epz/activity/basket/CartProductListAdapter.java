package szydlowskiptr.com.epz.activity.basket;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.home.HomeActivity;
import szydlowskiptr.com.epz.home.HomeFragment;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Item;
import szydlowskiptr.com.epz.model.Product;
import szydlowskiptr.com.epz.product.ProductAdapter;
import szydlowskiptr.com.epz.product.ProductPerCategoryFragment;
import szydlowskiptr.com.epz.sliderSearch.SearchFragment;

public class CartProductListAdapter extends RecyclerView.Adapter<CartProductListAdapter.ViewHolder>{

    private List<Item> newDataArray;
    private Context context;
    private CartModel getCartModel;
    private Fragment fragment;
    String tag;

    public CartProductListAdapter(Context context, List<Item> newDataArray, CartModel getCartModel, Fragment fragment, String tag) {
        this.context = context;
        this.newDataArray = newDataArray;
        this.getCartModel = getCartModel;
        this.fragment = fragment;
        this.tag = tag;
        Rollbar.init(context);
    }


    @NonNull
    @Override
    public CartProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product_basket, parent, false);
        return new CartProductListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductListAdapter.ViewHolder holder, int position) {
        Item data = this.newDataArray.get(position);
        holder.price.setText(data.getPrice() + " zł");
        holder.name.setText(data.getName());
        holder.description.setText(data.getDescription());
        holder.countProductListBasket.setText(String.valueOf(data.getProductQuantityInBasket()));
        holder.id.setText(String.valueOf(data.getProductId()));
        holder.capacity.setText(String.valueOf(data.getWeight()));
        Glide.with(holder.productIcon.getContext())
                .load(data.getImage())
                .into(holder.productIcon);
        setCounter(position, holder);
        decreaseAmmountProduct(holder, position, data);
        increaseAmmoutProduct(holder, position, data);
    }

    private void increaseAmmoutProduct(@NonNull CartProductListAdapter.ViewHolder holder, int position, Item data) {
        holder.plusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = context.getSharedPreferences("preferences", MODE_PRIVATE);
                String userId = preferences.getString("user_id", null);
                String mag_id = preferences.getString("mag_id", null);
                if (userId.equals("0")) {
                    if (context instanceof HomeActivity) {
                        ((HomeActivity) context).showLogInDialog();
                    }
                    // TODO dla norki defoult jesli w zwrotce przyjdzie mag defoult przkierowywac do pop up podaj adres
                } else if ((!userId.equals("0")) && mag_id.equals("3")) {
                    if (context instanceof HomeActivity) {
                        ((HomeActivity) context).giveAnAddressPopUp();
                    }
                } else {
                    //TODO jesli zostanie zwrocona norka inna niz defoult w zerotce umozliwić dodnie produktu
                    holder.countProductListBasket.setVisibility(View.VISIBLE);
                    if ("BASKET_WITH_ITEMS_FRA_TAG".equals(tag)) {
                        ((BasketFragmentWithItems) fragment).addToCart(String.valueOf(data.getStockItemId()));
                        ((BasketFragmentWithItems) fragment).getCart();
                    }
                    setCounter(position, holder);
                    holder.minusProduct.setBackgroundColor(Color.parseColor("#734B92"));
                    holder.minusProduct.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void decreaseAmmountProduct(@NonNull CartProductListAdapter.ViewHolder holder, int position, Item data) {
        holder.minusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("BASKET_WITH_ITEMS_FRA_TAG".equals(tag)) {
                    ((BasketFragmentWithItems) fragment).removeFromCart(String.valueOf(data.getStockItemId()));
                    ((BasketFragmentWithItems) fragment).getCart();
                }
                setCounter(position, holder);
            }
        });
    }

    public void setCounter(int position, @NonNull CartProductListAdapter.ViewHolder holder) {
        SharedPreferences sp = context.getSharedPreferences("preferences", MODE_PRIVATE);
        if (!sp.getString("user_id", null).matches("0")) {
            try {
                List<Item> items = this.getCartModel.getItems();
                Long id = newDataArray.get(position).getProductId();
                for (int i = 0; i < items.size(); i++) {
                    if (id == items.get(i).getProductId()) {
                        holder.countProductListBasket.setText(String.valueOf(items.get(i).getProductQuantityInBasket()));
                        holder.countProductListBasket.setVisibility(View.VISIBLE);
                        holder.minusProduct.setVisibility(View.VISIBLE);
                        holder.minusProduct.setBackgroundColor(Color.parseColor("#734B92"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return newDataArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productIcon;
        private TextView name;
        private TextView capacity;
        private TextView description;
        private TextView countProductListBasket;
        private CardView minusProduct;
        private CardView plusProduct;
        private TextView id;
        private TextView price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.product_price);
            productIcon = itemView.findViewById(R.id.imageProductView);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.description);
            id = itemView.findViewById(R.id.productId);
            capacity = itemView.findViewById(R.id.capacity);
            countProductListBasket = itemView.findViewById(R.id.countProductListBasket);
            minusProduct = itemView.findViewById(R.id.minusProduct);
            plusProduct = itemView.findViewById(R.id.product_plus);
        }
    }
}
