package com.example.dhis2.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class factory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(model.class)) {
            return (T) new model();
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }

    }
}
