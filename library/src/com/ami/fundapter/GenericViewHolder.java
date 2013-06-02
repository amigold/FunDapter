package com.ami.fundapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GenericViewHolder {
    public View root;
    public TextView[] stringFields;
    public ImageView[] imageFields;
    public View[] conditionalVisibilityFields;
    public ProgressBar[] progressBarFields;
}
