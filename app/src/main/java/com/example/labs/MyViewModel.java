package com.example.labs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

public class MyViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<item>> mItems;
    private MutableLiveData<item> mSelectedItem;
    private int mSelectedIndex;
    public MyViewModel(@NonNull Application application) {
        super(application);
    }

    private void generateItems(){
        ArrayList<item> items = new ArrayList<item>();
        items.add(new item("First","Link1","01/12/19","Fill in text 1"));
        items.add(new item("Second","Link2","02/12/19","Fill in text 2"));
        items.add(new item("Third","Link3","03/12/19","Fill in text 3"));
        items.add(new item("Fourth","Link4","04/12/19","Fill in text 4"));
        items.add(new item("Fifth","Link5","05/12/19","Fill in text 5"));
        mItems.setValue(items);
    }
    public LiveData<ArrayList<item>> getItems(){
        if(mItems == null){
            mItems = new MutableLiveData<ArrayList<item>>();
            generateItems();
            selectItem(0);
        }
        return mItems;
    }

    public void selectItem(int pIndex) {
        mSelectedIndex = pIndex;
        item selectedItem = mItems.getValue().get(mSelectedIndex);
        mSelectedItem = new MutableLiveData<item>();
        mSelectedItem.setValue(selectedItem);
    }
}
