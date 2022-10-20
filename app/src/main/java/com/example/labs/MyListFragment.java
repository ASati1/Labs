package com.example.labs;

import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

public class MyListFragment extends ListFragment {
    int mCurCheckPosition = 0;
    boolean msSingleActivity;

    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);

        setListAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                DummyData.DATA_HEADINGS));

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        View contentFrame = getActivity().findViewById(R.id.content);
        msSingleActivity = contentFrame != null
                && contentFrame.getVisibility() == View.VISIBLE;

        if (saveInstanceState != null) {
//            Restore last state for checked position.
            mCurCheckPosition = saveInstanceState.getInt("curChoice", 0);
        }
        if (msSingleActivity) {
            showContext((mCurCheckPosition));
        } else {
            getListView().setItemChecked(mCurCheckPosition, true);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showContext(position);
    }

    private void showContext(int index) {
        mCurCheckPosition = index;

        if (msSingleActivity) {
            getListView().setItemChecked(index, true);

//            Check what fragment is currently shown, replace if needed.
            ListItemFragment content = (ListItemFragment) getFragmentManager()
                    .findFragmentById(R.id.content);
            if (content == null || content.getShownIndex() != index) {
//                Make new fragment to show this selection.

                content = ListItemFragment.newInstance(index);

//                Execute a transaction, replacing any existing fragment.
//                with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content, content);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        } else {
//            Create an intent for starting the DetailsActivity

            Intent intent = new Intent();

//        explicitly set the activity context and class
//        associate with the intent (context, class)
            intent.setClass(getActivity(), ItemActivity.class);

//        pass the current position
            intent.putExtra("index", index);

            startActivity(intent);

        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("curChoice", mCurCheckPosition);
    }
}
