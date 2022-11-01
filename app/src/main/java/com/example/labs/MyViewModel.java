package com.example.labs;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

public class MyViewModel extends AndroidViewModel {
    private LiveData<ArrayList<item>> mItems;
    private LiveData<item> mSelectedItem;
    private ItemsRepository mItemRepository;
    private int mSelectedIndex;
    public MyViewModel(@NonNull Application application) {
        super(application);
        mItemRepository = ItemsRepository.getInstance(getApplication());
        getItems();
    }

    //private void generateItems(){
        //ArrayList<item> items = new ArrayList<item>();
        //items.add(new item("First","Link1","01/12/19","Fill in text 1"));
        //items.add(new item("Second","Link2","02/12/19","Fill in text 2"));
        //items.add(new item("Third","Link3","03/12/19","Fill in text 3"));
        //items.add(new item("Fourth","Link4","04/12/19","Fill in text 4"));
        //items.add(new item("Fifth","Link5","05/12/19","Fill in text 5"));
        //mItems.getValue(items);
    //}
    public LiveData<ArrayList<item>> getItems(){
        if(mItems == null){
            mItems = mItemRepository.getItems();
        }
        return mItems;
    }

    public LiveData<item> getItem(int pItemIndex){
        return mItemRepository.getItem(pItemIndex);
    }

    public void selectItem(int pIndex) {
        if(pIndex !=mSelectedIndex || mSelectedItem==null){
            mSelectedIndex = pIndex;
            mSelectedItem = getItem(mSelectedIndex);
        }
    }

    public LiveData<item> getSelectedItem(){
        return mSelectedItem;
    }
}
