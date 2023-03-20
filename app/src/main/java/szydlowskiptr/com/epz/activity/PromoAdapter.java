package szydlowskiptr.com.epz.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Product;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> {

    ArrayList<Product> newDataArray;
    Context context;

    public PromoAdapter(Context context, ArrayList<Product> newDataArray) {
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
        holder.price.setText(data.getEan());
        holder.productIcon.setImageResource(R.drawable.product);
        holder.name.setText(data.getName());
        holder.description.setText(data.getDescription());
        holder.id.setText(data.getId());

        if (!data.isActive()) {
            holder.promoBadge.setVisibility(View.INVISIBLE);
            holder.procentageBa.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return newDataArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price;
        ImageView productIcon;
        TextView id;
        TextView name;
        TextView description;
        TextView countProduct;
        CardView minusProduct;
        CardView plusProduct;
        CardView promoBadge;
        CardView procentageBa;
        CardView newBadge;
        CardView hitBadge;

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

            plusProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
                    String userId = preferences.getString("user_id", "");
                    if (userId.equals("0")) {
                        if (context instanceof HomeActivityWithoutLogIn) {
                            ((HomeActivityWithoutLogIn) context).showLogInDialog();
                        }
                    }
                }
            });
        }
    }
}
