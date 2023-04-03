package szydlowskiptr.com.epz.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.CartDao;
import szydlowskiptr.com.epz.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList<Product> newDataArray;
    private Context context;
    private List<CartDao> cart = GetList.getCart();


    public ProductAdapter(Context context, ArrayList<Product> newDataArray) {
        this.context = context;
        this.newDataArray = newDataArray;
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
        decreaseAmmountProduct(holder, position);
        increaseAmmoutProduct(holder, position);
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
                holder.procentage_badge_text
                        .setText(substring);
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
                if (context instanceof HomeActivityWithoutLogIn) {
                    ((HomeActivityWithoutLogIn) context).moveToProductDescription((String) holder.id.getText());
                }
            }
        });
    }

    private void increaseAmmoutProduct(@NonNull ViewHolder holder, int position) {
        holder.plusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
                String userId = preferences.getString("user_id", "");
                if (userId.equals("0")) {
                    if (context instanceof HomeActivityWithoutLogIn) {
                        ((HomeActivityWithoutLogIn) context).showLogInDialog();
                    }
                    if (context instanceof SearchActivity) {
                        ((SearchActivity) context).showLogInDialog();
                    }
                } else {
                    holder.countProduct.setVisibility(View.VISIBLE);
                    holder.minusProduct.setVisibility(View.VISIBLE);
                    setCounter(position, holder);
                    holder.minusProduct.setBackgroundColor(Color.parseColor("#734B92"));
                }
            }
        });
    }

    private void setCounter(int position, @NonNull ViewHolder holder) {
        cart = GetList.getCart();
        Long id = newDataArray.get(position).getProductId();
        for (int i = 0; i < cart.size(); i++) {
            if (id == cart.get(i).getId()) {
                holder.countProduct.setText(String.valueOf(cart.get(i).getQty()));
                holder.countProduct.setVisibility(View.VISIBLE);
                holder.minusProduct.setVisibility(View.VISIBLE);
                holder.minusProduct.setBackgroundColor(Color.parseColor("#734B92"));
            }
        }
    }

    private void decreaseAmmountProduct(@NonNull ViewHolder holder, int position) {
        holder.minusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        }
    }
}
