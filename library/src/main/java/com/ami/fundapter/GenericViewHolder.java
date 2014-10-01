package com.ami.fundapter;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GenericViewHolder {
    public View root;
    public TextView[] stringFields;
    public ImageView[] dynamicImageFields;
    public ImageView[] staticImageFields;
    public View[] conditionalVisibilityFields;
    public ProgressBar[] progressBarFields;
    public View[] baseFields;
    public CompoundButton[] checkableFields;
}
