package com.cmdemo.cmdemo.textview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmdemo.cmdemo.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ItemFragment extends Fragment {

    public static ItemFragment newInstance(String item) {
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", item);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, null);
        TextView tv = (TextView)view.findViewById(R.id.text);
        Bundle bundle = getArguments();
        tv.setText(bundle.getString("title"));
        tv.setTextSize(40);
        return view;
    }
}