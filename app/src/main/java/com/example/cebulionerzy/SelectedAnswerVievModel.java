package com.example.cebulionerzy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectedAnswerVievModel extends ViewModel {
    private final MutableLiveData<Integer> selectedAnsewr1 = new MutableLiveData<Integer>();
    private final MutableLiveData<Integer> selectedAnsewr2 = new MutableLiveData<Integer>();

    public void setData(int ans1, int ans2) {
        selectedAnsewr1.setValue(ans1);
        selectedAnsewr2.setValue(ans2);
    }

    public LiveData<Integer> getSelectedAnswer1() {
        return selectedAnsewr1;
    }

    public LiveData<Integer> getSelectedAnswer2() {
        return selectedAnsewr2;
    }
}
