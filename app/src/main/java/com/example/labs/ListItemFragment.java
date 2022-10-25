package com.example.labs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListItemFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INDEX = "index";

    private int mIndex;
    MyViewModel mViewModel;
    View mInflatedView;

    public int getShownIndex(){
        return mIndex;
    }

    public ListItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index Parameter 1 is the index of the context data we want to display.
     * @return A new instance of fragment BlankFragment.
     */
    public static ListItemFragment newInstance(int index) {
        ListItemFragment fragment = new ListItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_INDEX);
        }
        mViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        mViewModel.selectItem(mIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(this.getClass().getSimpleName()+" Observer","onCreateView");
        mInflatedView = inflater.inflate(R.layout.fragment_list_item,container,false);
//        Create the observer which updates the UI.
        final Observer<item> itemObserver = new Observer<item>() {
            @Override
            public void onChanged(@Nullable final item item){
                TextView text =(TextView) mInflatedView.findViewById(R.id.listItemTextView);
                text.setText(DummyData.DATA_CONTENT[mIndex]);
            }
        };
        mViewModel.getSelectedItem().observe(getViewLifecycleOwner(),itemObserver);
        return mInflatedView;
    }
}