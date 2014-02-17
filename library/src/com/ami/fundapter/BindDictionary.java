package com.ami.fundapter;


import com.ami.fundapter.extractors.BooleanExtractor;
import com.ami.fundapter.extractors.IntegerExtractor;
import com.ami.fundapter.extractors.StringExtractor;
import com.ami.fundapter.fields.BaseField;
import com.ami.fundapter.fields.CheckableField;
import com.ami.fundapter.fields.ConditionalVisibilityField;
import com.ami.fundapter.fields.DynamicImageField;
import com.ami.fundapter.fields.ProgressBarField;
import com.ami.fundapter.fields.StaticImageField;
import com.ami.fundapter.fields.StringField;
import com.ami.fundapter.interfaces.DynamicImageLoader;
import com.ami.fundapter.interfaces.StaticImageLoader;

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
    private ArrayList<DynamicImageField<T>> mDynamicImageFields;
    private ArrayList<StaticImageField<T>> mStaticImageFields;
    private ArrayList<ConditionalVisibilityField<T>> mConditionalVisibilityFields;
    private ArrayList<ProgressBarField<T>> mProgressBarFields;
    private ArrayList<BaseField<T>> mBaseFields;
    private ArrayList<CheckableField<T>> mCheckableFields;

    public BindDictionary() {
        mStringFields = new ArrayList<StringField<T>>();
        mDynamicImageFields = new ArrayList<DynamicImageField<T>>();
        mConditionalVisibilityFields = new ArrayList<ConditionalVisibilityField<T>>();
        mProgressBarFields = new ArrayList<ProgressBarField<T>>();
        mBaseFields = new ArrayList<BaseField<T>>();
        mCheckableFields = new ArrayList<CheckableField<T>>();
        mStaticImageFields = new ArrayList<StaticImageField<T>>();
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
    public DynamicImageField<T> addDynamicImageField(int viewResId, StringExtractor<T> extractor,
                                                     DynamicImageLoader dynamicImageLoader) {

        DynamicImageField<T> field =
                new DynamicImageField<T>(viewResId, extractor, dynamicImageLoader);

        mDynamicImageFields.add(field);

        return field;
    }

    int getDynamicImageFieldCount() {
        return mDynamicImageFields != null ? mDynamicImageFields.size() : 0;
    }

    ArrayList<DynamicImageField<T>> getDynamicImageFields() {
        return mDynamicImageFields;
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

    //static image field methods
    public StaticImageField<T> addStaticImageField(int viewResId,
                                                   StaticImageLoader<T> staticImageLoader) {

        StaticImageField<T> field = new StaticImageField<T>(viewResId, staticImageLoader);

        mStaticImageFields.add(field);

        return field;
    }

    int getStaticImageFieldCount() {
        return mStaticImageFields != null ? mStaticImageFields.size() : 0;
    }

    ArrayList<StaticImageField<T>> getStaticImageFields() {
        return mStaticImageFields;
    }
}