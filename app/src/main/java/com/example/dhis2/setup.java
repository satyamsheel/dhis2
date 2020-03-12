package com.example.dhis2;

import android.content.Context;

import org.hisp.dhis.android.core.D2;
import org.hisp.dhis.android.core.D2Configuration;
import org.hisp.dhis.android.core.D2Manager;

public class setup {

    public static D2 d2() throws IllegalArgumentException {
        return D2Manager.getD2();
    }
    public static D2Configuration configuration(Context context){
        return D2Configuration.builder()
                .context(context)
                .build();
    }
}
