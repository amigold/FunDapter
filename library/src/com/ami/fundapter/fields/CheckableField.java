package com.ami.fundapter.fields;

import com.ami.fundapter.extractors.BooleanExtractor;
import com.ami.fundapter.fields.BaseField;
import com.ami.fundapter.interfaces.CheckedChangeListener;

public class CheckableField<T> extends BaseField<T> {

    public final BooleanExtractor<T> checkedExtractor;
    public CheckedChangeListener<T> checkedChangeListener;

    public CheckableField(int viewResId, BooleanExtractor<T> isCheckedExtractor) {
        super(viewResId);
        checkedExtractor = isCheckedExtractor;
    }

    public void setCheckedChangeListener(CheckedChangeListener<T> checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
    }
}
