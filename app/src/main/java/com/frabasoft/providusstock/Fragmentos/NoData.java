package com.frabasoft.providusstock.Fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.frabasoft.providusstock.R;

public class NoData extends Fragment {
    View view;
    ImageView imageView;

    public NoData() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_no_data, container, false);
        imageView = view.findViewById(R.id.ivNoData);
        imageView.setImageResource(R.drawable.nodatafound);
        return view;
    }
}