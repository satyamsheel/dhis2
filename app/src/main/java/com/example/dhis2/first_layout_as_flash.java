package com.example.dhis2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.dhis2.login.Login;

import org.hisp.dhis.android.core.D2Manager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class first_layout_as_flash extends AppCompatActivity {
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_layout_as_flash);


        disposable = D2Manager.instantiateD2(setup.configuration(this))
                .flatMap(d2 -> d2.userModule().isLogged())
                .doOnSuccess(isLogged -> {
                    if (isLogged) {
                        ActivityStarter.startActivity(this, MainActivity.getMainActivityIntent(this),true);
                    } else {
                        ActivityStarter.startActivity(this, Login.getLoginActivityIntent(this),true);
                    }
                }).doOnError(throwable -> {
                    throwable.printStackTrace();
                    ActivityStarter.startActivity(this, Login.getLoginActivityIntent(this),true);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    // to dispose off link
    //refrence from Disposable.java
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
