package com.ami.fundapter;


import com.ami.fundapter.extractors.BooleanExtractor;
import com.ami.fundapter.extractors.IntegerExtractor;
import com.ami.fundapter.extractors.StringExtractor;
import com.ami.fundapter.fields.BaseField;
import com.ami.fundapter.fields.ConditionalVisibilityField;
import com.ami.fundapter.fields.ImageField;
import com.ami.fundapter.fields.ProgressBarField;
import com.ami.fundapter.fields.StringField;

import java.util.ArrayList;

/**
 * A dictionary to hold all the fields in a ListView item. Construct it with the
 * appropriate model class.
 *
 * @param <T>
 * @author Ami G
 */
public class BindDictionary<T> {

    private ArrayList<StringField<T>> mStringFields;
    private ArrayList<ImageField<T>> mImageFields;
    private ArrayList<ConditionalVisibilityField<T>> mConditionalVisibilityFields;
    private ArrayList<ProgressBarField<T>> mProgressBarFields;
    private ArrayList<BaseField<T>> mBaseFields;
    private ArrayList<CheckableField<T>> mCheckableFields;

    public BindDictionary() {
        mStringFields = new ArrayList<StringField<T>>();
        mImageFields = new ArrayList<ImageField<T>>();
        mConditionalVisibilityFields = new ArrayList<ConditionalVisibilityField<T>>();
        mProgressBarFields = new ArrayList<ProgressBarField<T>>();
        mBaseFields = new ArrayList<BaseField<T>>();
        mCheckableFields = new ArrayList<CheckableField<T>>();
    }

    // -----------------------------
    // Progress field methods
    // -----------------------------
    public ProgressBarField<T> addProgressBarField(int viewResId,
                                                   IntegerExtractor<T> progressExtractor,
                                                   IntegerExtractor<T> maxProgressExtractor) {

        ProgressBarField<T> field =
                new ProgressBarField<T>(viewResId, progressExtractor, maxProgressExtractor);

        mProgressBarFields.add(field);

        return field;
    }

    int getProgressBarFieldCount() {
        return mProgressBarFields != null ? mProgressBarFields.size() : 0;
    }

    ArrayList<ProgressBarField<T>> getProgressBarFields() {
        return mProgressBarFields;
    }

    // ---------------
    // ConditionalView methods
    // ---------------
    public ConditionalVisibilityField<T> addConditionalVisibilityField(int viewResId,
                                                                       BooleanExtractor<T> extractor,
                                                                       int visibilityIfFalse) {

        ConditionalVisibilityField<T> field =
                new ConditionalVisibilityField<T>(viewResId, extractor, visibilityIfFalse);

        mConditionalVisibilityFields.add(field);

        return field;
    }

    int getConditionalVisibilityFieldCount() {
        return mConditionalVisibilityFields != null ? mConditionalVisibilityFields.size() : 0;
    }

    ArrayList<ConditionalVisibilityField<T>> getConditionalVisibilityFields() {
        return mConditionalVisibilityFields;
    }

    // ---------------
    // Image field methods
    // ---------------
    public ImageField<T> addImageField(int viewResId, StringExtractor<T> extractor,
                                       ImageLoader imageLoader) {

        ImageField<T> field = new ImageField<T>(viewResId, extractor, imageLoader);

        mImageFields.add(field);

        return field;
    }

    int getImageFieldCount() {
        return mImageFields != null ? mImageFields.size() : 0;
    }

    ArrayList<ImageField<T>> getImageFields() {
        return mImageFields;
    }

    // ---------------
    // String field methods
    // ---------------
    public StringField<T> addStringField(int viewResId, StringExtractor<T> extractor) {

        StringField<T> field = new StringField<T>(viewResId, extractor);

        mStringFields.add(field);

        return field;
    }

    int getStringFieldCount() {
        return mStringFields != null ? mStringFields.size() : 0;
    }

    ArrayList<StringField<T>> getStringFields() {
        return mStringFields;
    }

    //Base field methods
    public BaseField<T> addBaseField(int viewResId) {

        BaseField<T> field = new BaseField<T>(viewResId);

        mBaseFields.add(field);

        return field;
    }

    int getBaseFieldCount() {
        return mBaseFields != null ? mBaseFields.size() : 0;
    }

    ArrayList<BaseField<T>> getBaseFields() {
        return mBaseFields;
    }

    //Checkable field methods
    public CheckableField<T> addCheckableField(int viewResId,
                                               BooleanExtractor<T> isCheckedExtractor) {

        CheckableField<T> field = new CheckableField<T>(viewResId, isCheckedExtractor);

        mCheckableFields.add(field);

        return field;
    }

    int getCheckableFieldCount() {
        return mCheckableFields != null ? mCheckableFields.size() : 0;
    }

    ArrayList<CheckableField<T>> getCheckableFields() {
        return mCheckableFields;
    }
}