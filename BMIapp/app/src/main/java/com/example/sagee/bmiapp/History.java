package com.example.sagee.bmiapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class History extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<ListItem> listItems = new ArrayList<>();

        Cursor result = DataBaseHelper.getInstance(getActivity()).getAllData();

        if (result.getCount() == 0) return view;

        while (result.moveToNext())
        {
            // make the weight font size bigger then "kg"
            String weightString = result.getString(2) + " kg";
            SpannableString weightSpannableString = new SpannableString(weightString);
            weightSpannableString.setSpan(new RelativeSizeSpan(1.3f), 0, result.getString(2).length(), 0);

            // make the height font size bigger then "cm"
            String heightString = result.getString(3) + " cm";
            SpannableString heightSpannableString = new SpannableString(heightString);
            heightSpannableString.setSpan(new RelativeSizeSpan(1.3f), 0, result.getString(3).length(), 0);

            // make the bmi result font size bigger then "bmi"
            String bmiResultString = result.getString(4) + " bmi";
            SpannableString bmiResultSpannableString = new SpannableString(bmiResultString);
            bmiResultSpannableString.setSpan(new RelativeSizeSpan(1.3f), 0, result.getString(4).length(), 0);

            ListItem listItem = new ListItem(result.getString(0), result.getString(1), weightSpannableString, heightSpannableString, bmiResultSpannableString);
            listItems.add(listItem);
        }

        RecyclerView.Adapter adapter = new MyAdapter(listItems, getActivity(), this);
        recyclerView.setAdapter(adapter);

        return view;
    }
}