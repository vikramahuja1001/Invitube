package com.yourevent.mobilevideoinvitation;

/**
 * Created by imjalpreet on 09-10-2014.
 */
import android.renderscript.Script;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
public class SampleData{
    public static final int SAMPLE_DATA_ITEM_COUNT = 3;
    public static ArrayList<String> generateSampleData() {
        final ArrayList<String> data = new ArrayList<String>(SAMPLE_DATA_ITEM_COUNT);
        //for (int i = 0; i < SAMPLE_DATA_ITEM_COUNT; i++) {
        data.add(0, "Formal");
        data.add(1, "Family");
        data.add(2, "Informal");
        return data;
    }
}