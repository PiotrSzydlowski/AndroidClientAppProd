package szydlowskiptr.com.epz.activity;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<Product> newDataArray;
    private Context context;
    private CartModel getCartModel;
    private Fragment fragment;
    String tag;


    public ProductAdapter(Context context, ArrayList<Product> newDataArray, CartModel getCartModel, Fragment fragment, String tag) {
        this.context = context;
        this.newDataArray = newDataArray;
        this.getCartModel = getCartModel;
        this.fragment = fragment;
        this.tag = tag;
        Rollbar.init(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product data = this.newDataArray.get(position);
        holder.price.setText(data.getPrice() + " zł");
        holder.name.setText(data.getProductsName());
        holder.description.setText(data.getProductDescription());
        holder.id.setText(String.valueOf(data.getProductId()));
        setCounter(position, holder);
        Glide.with(holder.productIcon.getContext())
                .load(data.getPhoto())
                .into(holder.productIcon);

        setBadges(holder, data);
        decreaseAmmountProduct(holder, position, data);
        increaseAmmoutProduct(holder, position, data);
        clickOnProductCard(holder);
    }

    private void setBadges(@NonNull ViewHolder holder, Product data) {
        if (!data.isHit()) {
            holder.hitBadge.setVisibility(View.INVISIBLE);
        }
        if (!data.isPromo()) {
            holder.promoBadge.setVisibility(View.INVISIBLE);
            holder.procentageBa.setVisibility(View.INVISIBLE);
        } else {
            if (data.getPriceBeforePromo() > 0.00) {
                String procentDiscount = String.valueOf(((data.getPrice() - data.getPriceBeforePromo()) / data.getPriceBeforePromo() * 100));
                String substring = procentDiscount.substring(0, 5) + "%";
                holder.procentage_badge_text.setText(substring);
                holder.product_price_before_promo.setVisibility(View.VISIBLE);
                holder.price.setText(data.getPrice() + " zł");
                holder.price.setTextColor(Color.parseColor("#E51D20"));
                holder.product_price_before_promo.setText(data.getPriceBeforePromo() + " zł");
                holder.product_price_before_promo.setPaintFlags(holder.product_price_before_promo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.procentageBa.setVisibility(View.INVISIBLE);
            }
        }
        if (!data.isIs_new()) {
            holder.newBadge.setVisibility(View.INVISIBLE);
        }
    }

    private void clickOnProductCard(@NonNull ViewHolder holder) {
        holder.cardViewProductBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof HomeActivity) {
                    ((HomeActivity) context).moveToProductDescription((String) holder.id.getText());
                }
            }
        });
    }

    private void increaseAmmoutProduct(@NonNull ViewHolder holder, int position, Product data) {
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
                    holder.countProduct.setVisibility(View.VISIBLE);
                    switch (tag) {
                        case "HOME_FR":
                            ((HomeFragment) fragment).addToCart(String.valueOf(data.getId()));
                            ((HomeFragment) fragment).getCart();
                            break;
                        case "BASKET_FR_TAG":
                            ((BasketFragment) fragment).addToCart(String.valueOf(data.getId()));
                            ((BasketFragment) fragment).getCart();
                            break;
                        case "SEARCH_FR":
                            ((SearchFragment) fragment).addToCart(String.valueOf(data.getId()));
                            ((SearchFragment) fragment).getCart();
                            break;
                        case "PRODUCT_PER_CAT_FR":
                            ((ProductPerCategoryFragment) fragment).addToCart(String.valueOf(data.getId()));
                            ((ProductPerCategoryFragment) fragment).getCart();
                            break;
                    }
                    setCounter(position, holder);
                    holder.minusProduct.setBackgroundColor(Color.parseColor("#734B92"));
                    holder.minusProduct.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void setCounter(int position, @NonNull ViewHolder holder) {
        SharedPreferences sp = context.getSharedPreferences("preferences", MODE_PRIVATE);
        if (!sp.getString("user_id", null).matches("0")) {
            try {
                List<Item> items = this.getCartModel.getItems();
                Long id = newDataArray.get(position).getProductId();
                for (int i = 0; i < items.size(); i++) {
                    if (id == items.get(i).getProductId()) {
                        holder.countProduct.setText(String.valueOf(items.get(i).getProductQuantityInBasket()));
                        holder.countProduct.setVisibility(View.VISIBLE);
                        holder.minusProduct.setVisibility(View.VISIBLE);
                        holder.minusProduct.setBackgroundColor(Color.parseColor("#734B92"));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }

    private void decreaseAmmountProduct(@NonNull ViewHolder holder, int position, Product data) {
        holder.minusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (tag) {
                    case "HOME_FR":
                        ((HomeFragment) fragment).removeFromCart(String.valueOf(data.getId()));
                        ((HomeFragment) fragment).getCart();
                        break;
                    case "BASKET_FR_TAG":
                        ((BasketFragment) fragment).removeFromCart(String.valueOf(data.getId()));
                        ((BasketFragment) fragment).getCart();
                        break;
                    case "SEARCH_FR":
                        ((SearchFragment) fragment).removeFromCart(String.valueOf(data.getId()));
                        ((SearchFragment) fragment).getCart();
                        break;
                    case "PRODUCT_PER_CAT_FR":
                        ((ProductPerCategoryFragment) fragment).removeFromCart(String.valueOf(data.getId()));
                        ((ProductPerCategoryFragment) fragment).getCart();
                        break;
                }
                setCounter(position, holder);
//                if (newDataArray.get(position).getQty() == 0) {
//                    holder.minusProduct.setVisibility(View.INVISIBLE);
//                    holder.countProduct.setVisibility(View.INVISIBLE);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newDataArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView price;
        private ImageView productIcon;
        private TextView id;
        private TextView name;
        private TextView description;
        private TextView countProduct;
        private CardView minusProduct;
        private CardView plusProduct;
        private CardView promoBadge;
        private CardView procentageBa;
        private CardView newBadge;
        private CardView hitBadge;
        private CardView cardViewProductBox;
        private TextView procentage_badge_text;
        private TextView product_price_before_promo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.product_price);
            productIcon = itemView.findViewById(R.id.imageProductView);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);
            id = itemView.findViewById(R.id.productId);
            countProduct = itemView.findViewById(R.id.countProduct);
            minusProduct = itemView.findViewById(R.id.minusProduct);
            plusProduct = itemView.findViewById(R.id.product_plus);
            promoBadge = itemView.findViewById(R.id.product_promo_badge);
            procentageBa = itemView.findViewById(R.id.procentage_badge);
            newBadge = itemView.findViewById(R.id.new_badge);
            hitBadge = itemView.findViewById(R.id.hit_badge);
            cardViewProductBox = itemView.findViewById(R.id.cardViewProductBox);
            procentage_badge_text = itemView.findViewById(R.id.procentage_badge_text);
            product_price_before_promo = itemView.findViewById(R.id.product_price_before_promo);
        }
    }
}
