package com.festdepartment.awaaz_e_bezuban.Model;

import android.widget.ImageView;
import android.widget.TextView;

public class SliderItem {
    private Integer Slider_Img;
    private String Slider_txt;



    public SliderItem(){

    }

    public SliderItem(Integer slider_Img , String slider_txt) {
        Slider_Img = slider_Img;
        Slider_txt = slider_txt;
    }

    public Integer getSlider_Img() {
        return Slider_Img;
    }

    public void setSlider_Img(Integer slider_Img) {
        Slider_Img = slider_Img;
    }

    public String getSlider_txt() {
        return Slider_txt;
    }

    public void setSlider_txt(String slider_txt) {
        Slider_txt = slider_txt;
    }
}
