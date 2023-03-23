package szydlowskiptr.com.epz.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Category> categoryDataArrayList;
    LayoutInflater inflater;

    public CategoryAdapter(Context ctx, ArrayList<Category> dataArrayList2s) {
        this.inflater = LayoutInflater.from(ctx);
        this.categoryDataArrayList = dataArrayList2s;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category data = this.categoryDataArrayList.get(position);
        holder.title.setText(data.getName());
        holder.id.setText(data.getId());
        setImageForCategory(holder, data);

    }

    private void setImageForCategory(@NonNull ViewHolder holder, Category data) {
        switch (data.getId()) {
            case "10":
                holder.gridIcon.setImageResource(R.drawable.woda);
                break;
            case "13":
                holder.gridIcon.setImageResource(R.drawable.pieczywo);
                break;
            case "16":
                holder.gridIcon.setImageResource(R.drawable.miesoryby);
                break;
            case "17":
                holder.gridIcon.setImageResource(R.drawable.mrozonki);
                break;
            case "18":
                holder.gridIcon.setImageResource(R.drawable.napoje);
                break;
            case "19":
                holder.gridIcon.setImageResource(R.drawable.owoceiwarzywa);
                break;
            case "20":
                holder.gridIcon.setImageResource(R.drawable.produktysypkie);
                break;
            case "21":
                holder.gridIcon.setImageResource(R.drawable.wedliny);
                break;
            case "22":
                holder.gridIcon.setImageResource(R.drawable.przekoskibakalie);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView gridIcon;
        TextView id;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.product_price);
            gridIcon = itemView.findViewById(R.id.imageView2);
            id = itemView.findViewById(R.id.categoryId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked -> " + id.getText(), Toast.LENGTH_SHORT).show();
                    context = id.getContext();
                    ((HomeActivityWithoutLogIn) context).moveToProducts();
                }
            });
        }
    }
}
