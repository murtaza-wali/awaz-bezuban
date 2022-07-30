package com.festdepartment.awaaz_e_bezuban.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.festdepartment.awaaz_e_bezuban.Model.SliderItem;
import com.festdepartment.awaaz_e_bezuban.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends  SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    public SliderAdapter(List<SliderItem> mSliderItems) {
        this.mSliderItems = mSliderItems;

    }

    public SliderAdapter(Context context) {
        this.context = context;

    }


    public void renewItems(List<SliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate( R.layout.slider_dashboard_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SliderItem sliderItem = mSliderItems.get(position);


        Glide.with(viewHolder.itemView)
                .load(sliderItem.getSlider_Img())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.slider_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;
        CardView slider_click;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.slider_image);
            slider_click = itemView.findViewById(R.id.slider_click);
            this.itemView = itemView;
        }
    }

}