package com.example.dhis2;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dhis2.login.Login;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class logout  {
    public static Disposable logOut(AppCompatActivity activity) {
        return setup.d2().userModule().logOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> ActivityStarter.startActivity(activity, Login.getLoginActivityIntent(activity.getApplicationContext()), true),
                        Throwable::printStackTrace);
    }
}
