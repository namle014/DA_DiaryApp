package com.example.doan_diaryapp.ui.analyze;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnalyzeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AnalyzeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}