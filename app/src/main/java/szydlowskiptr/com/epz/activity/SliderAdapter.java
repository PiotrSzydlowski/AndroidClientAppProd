package szydlowskiptr.com.epz.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

import szydlowskiptr.com.epz.R;
import szydlowskiptr.com.epz.model.SlidersModel;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

//    int[] images;
//    ArrayList<SlidersModel> sliders = new ArrayList<>();
    private ArrayList<SlidersModel> sliders;

    public SliderAdapter(ArrayList<SlidersModel> sliders){
        this.sliders = sliders;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_slider_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        SlidersModel data = this.sliders.get(position);
//        viewHolder.imageView.setImageResource(data.getUrl());
        Glide.with(viewHolder.imageView.getContext())
                .load(data.getUrl())
                .into(viewHolder.imageView);

    }

    @Override
    public int getCount() {
        return sliders.size();
    }

    public class Holder extends  SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public Holder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

        }
    }

}
