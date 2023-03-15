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

    public CategoryAdapter(Context ctx, ArrayList<Category> dataArrayList2s){
        this.inflater = LayoutInflater.from(ctx);
        this.categoryDataArrayList = dataArrayList2s;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_grid_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category data = this.categoryDataArrayList.get(position);
        holder.title.setText(data.getName());
        holder.id.setText(data.getId());
        holder.gridIcon.setImageResource(R.drawable.baseline_access_alarms_24);
    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView gridIcon;
        TextView id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            gridIcon = itemView.findViewById(R.id.imageView2);
            id = itemView.findViewById(R.id.categoryId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked -> " + id.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
