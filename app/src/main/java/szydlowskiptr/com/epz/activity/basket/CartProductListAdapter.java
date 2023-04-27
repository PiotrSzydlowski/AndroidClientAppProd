package szydlowskiptr.com.epz.activity.basket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.rollbar.android.Rollbar;

import java.util.ArrayList;
import java.util.List;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.CartModel;
import szydlowskiptr.com.epz.model.Item;

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
        Item item = this.newDataArray.get(position);

    }

    @Override
    public int getItemCount() {
        return newDataArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productIcon;
        private TextView name;
        private TextView description;
        private TextView countProduct;
        private CardView minusProduct;
        private CardView plusProduct;
        private TextView id;
        private TextView price;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.product_price);
            productIcon = itemView.findViewById(R.id.imageProductView);
            name = itemView.findViewById(R.id.product_name);
            description = itemView.findViewById(R.id.product_description);
            id = itemView.findViewById(R.id.productId);
        }
    }
}
