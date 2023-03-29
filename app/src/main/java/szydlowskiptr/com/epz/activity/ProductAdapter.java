package szydlowskiptr.com.epz.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
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
import java.util.List;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.CartDao;
import szydlowskiptr.com.epz.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    ArrayList<Product> newDataArray;
    Context context;
    int productCounter;
    int count;


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
        holder.price.setText(data.getEan());
        holder.productIcon.setImageResource(R.drawable.product);
        holder.name.setText(data.getName());
        holder.description.setText(data.getDescription());
        holder.id.setText(data.getId());
//        holder.countProduct.setText(String.valueOf(newDataArray.get(position).getQty()));

        if (newDataArray.get(position).getQty() > 0) {
            holder.countProduct.setVisibility(View.VISIBLE);
            holder.minusProduct.setVisibility(View.VISIBLE);
            holder.minusProduct.setBackgroundColor(Color.parseColor("#734B92"));
        } else {
            holder.minusProduct.setVisibility(View.INVISIBLE);
            holder.countProduct.setVisibility(View.INVISIBLE);
        }

        if (!data.isActive()) {
            holder.promoBadge.setVisibility(View.INVISIBLE);
            holder.procentageBa.setVisibility(View.INVISIBLE);
        }

        decreaseAmmountProduct(holder, position);
        increaseAmmoutProduct(holder, position);
        clickOnProductCard(holder);
    }

    private void clickOnProductCard(@NonNull ViewHolder holder) {
        holder.cardViewProductBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof HomeActivityWithoutLogIn) {
                    ((HomeActivityWithoutLogIn) context).moveToProductDescription();
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
                } else {
                    productCounter = 0;
                    holder.countProduct.setVisibility(View.VISIBLE);
                    holder.minusProduct.setVisibility(View.VISIBLE);
                    count = 0;


//                    List<CartDao> cart = Cart.getCart();
//                    if (cart.isEmpty()) {
//                        CartDao cartDao = new CartDao();
//                        cartDao.setId(Integer.parseInt(newDataArray.get(position).getId()));
//                        cartDao.setQty(cartDao.getQty() + 1);
//                        Cart.addToCart(cartDao);
//                    } else {
//                        for (int i = 0; i < cart.size(); i++) {
//                            if (cart.get(i).getId() == Integer.parseInt(newDataArray.get(position).getId())) {
//                                cart.get(i).setQty(cart.get(i).getQty() + 1);
//                            } else {
//                                CartDao cartDao = new CartDao();
//                                cartDao.setId(Integer.parseInt(newDataArray.get(position).getId()));
//                                cart.get(i).setQty(cart.get(i).getQty() + 1);
//                                Cart.addToCart(cartDao);
//                            }
//                        }
//                    }


                    System.out.println("DDDDDDDDDDDDDDDDDD " + GetList.getCart());
                    List<CartDao> cart1 = GetList.getCart();

//                    cart1.get(position).setQty(cart1.get(position).getQty() + 1);
//                    newDataArray.get(position).setQty(newDataArray.get(position).getQty() + 1);
//                    holder.countProduct.setText(String.valueOf(newDataArray.get(position).getQty()));
                    int id = Integer.parseInt(newDataArray.get(position).getId());
                    System.out.println("IDDDDDDDDDDDDDDDDDDD " + id);
                    System.out.println("JJJJJJJJJJJJJJJJJJJJJJ " + cart1.get(0).getId());

                    for (int i = 0; i < cart1.size(); i++) {
                        if (id == cart1.get(i).getId()){
                            holder.countProduct.setText(String.valueOf(cart1.get(i).getQty()));
                            Toast.makeText(view.getContext(), "ID równe + ilosc: " + cart1.get(i).getQty(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(view.getContext(), "ID nierówne", Toast.LENGTH_SHORT).show();
                            holder.countProduct.setText(String.valueOf(0));
                            holder.countProduct.setVisibility(View.INVISIBLE);
                            holder.minusProduct.setVisibility(View.INVISIBLE);
                        }
                    }


//                    try {
//                        holder.countProduct.setText(String.valueOf(cart1.get(position).getQty()));
//                    } catch (IndexOutOfBoundsException e) {
//                        holder.countProduct.setText(String.valueOf(0));
//                        holder.countProduct.setVisibility(View.INVISIBLE);
//                        holder.minusProduct.setVisibility(View.INVISIBLE);
//                    }
                    holder.minusProduct.setBackgroundColor(Color.parseColor("#734B92"));
//                    if (context instanceof HomeActivityWithoutLogIn) {
//                        ((HomeActivityWithoutLogIn) context).plusProduct();
//                    }
                }
            }
        });
    }

    private void decreaseAmmountProduct(@NonNull ViewHolder holder, int position) {
        holder.minusProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.countProduct.setVisibility(View.VISIBLE);
                holder.minusProduct.setVisibility(View.VISIBLE);
                newDataArray.get(position).setQty(newDataArray.get(position).getQty() - 1);
                holder.countProduct.setText(String.valueOf(newDataArray.get(position).getQty()));
                if (newDataArray.get(position).getQty() == 0) {
                    holder.minusProduct.setVisibility(View.INVISIBLE);
                    holder.countProduct.setVisibility(View.INVISIBLE);
                }
            }
        });
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
        CardView cardViewProductBox;

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
        }
    }
}
