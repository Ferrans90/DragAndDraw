package com.ferran.draganddraw.Presenter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ferran.draganddraw.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DragAndDrawFragment extends Fragment {
    private static final String ARG = "SaveData";

    public DragAndDrawFragment() {
        // Required empty public constructor
    }

    public static DragAndDrawFragment newInstance() {

        Bundle args = new Bundle();

        DragAndDrawFragment fragment = new DragAndDrawFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drag_and_draw, container, false);

        return v;
    }

}
