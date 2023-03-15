package szydlowskiptr.com.epz.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.Category;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> {

    ArrayList<Category> newDataArray;
    Context context;

    public PromoAdapter(Context context, ArrayList<Category> newDataArray){
        this.context = context;
        this.newDataArray = newDataArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_grid_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category data = this.newDataArray.get(position);
        holder.title.setText(data.getName());
        holder.id.setText(data.getId());
        holder.gridIcon.setImageResource(R.drawable.baseline_access_alarms_24);
    }

    @Override
    public int getItemCount() {
        return newDataArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView gridIcon;
        TextView id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            gridIcon = itemView.findViewById(R.id.imageView2);
            id = itemView.findViewById(R.id.categoryId);
        }
    }
}
