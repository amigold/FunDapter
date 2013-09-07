package com.ami.fundapter;

import com.ami.fundapter.extractors.BooleanExtractor;
import com.ami.fundapter.fields.BaseField;

public class CheckableField<T> extends BaseField<T> {

    final BooleanExtractor<T> checkedExtractor;
    CheckedChangeListener checkedChangeListener;

    public CheckableField(int viewResId, BooleanExtractor<T> isCheckedExtractor) {
        super(viewResId);
        checkedExtractor = isCheckedExtractor;
    }

    public void setCheckedChangeListener(CheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
    }
}
