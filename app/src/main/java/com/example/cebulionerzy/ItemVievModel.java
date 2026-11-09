package com.example.cebulionerzy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemVievModel extends ViewModel {
    private final MutableLiveData<Integer> selectedItem = new MutableLiveData<Integer>();

    public void setData(int item) {
        selectedItem.setValue(item);
    }
    public LiveData<Integer> getSelectedItem() {
        return selectedItem;
    }
}
