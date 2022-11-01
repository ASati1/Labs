package com.example.labs;

import android.content.Context;

public class ItemsRepository {
    public static ItemsRepository sItemsRepository;

    private Context mApplicationContext;

    private ItemsRepository(Context pApplicationContext){
        this.mApplicationContext = pApplicationContext;
    }

    public static ItemsRepository getInstance(Context pApplicationContext){
        if(sItemsRepository ==null){
            sItemsRepository = new ItemsRepository(pApplicationContext);
        }
        return sItemsRepository;
    }
}
