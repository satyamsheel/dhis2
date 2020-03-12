package com.example.dhis2.login;

import androidx.annotation.Nullable;

import org.hisp.dhis.android.core.user.User;

public class result {
    @Nullable
    private User success;
    @Nullable
    private Integer error;

    result(@Nullable Integer error) {
        this.error = error;
    }

    result(@Nullable User success) {
        this.success = success;
    }

    @Nullable
    User getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
