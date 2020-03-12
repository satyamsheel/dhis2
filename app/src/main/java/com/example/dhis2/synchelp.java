package com.example.dhis2;

import org.hisp.dhis.android.core.common.State;

public class synchelp {
    public static int programCount() {
        return setup.d2().programModule().programs().blockingCount();
    }

    public static int dataSetCount() {
        return setup.d2().dataSetModule().dataSets().blockingCount();
    }

    public static int trackedEntityInstanceCount() {
        return setup.d2().trackedEntityModule().trackedEntityInstances()
                .byState().neq(State.RELATIONSHIP).blockingCount();
    }

    public static int singleEventCount() {
        return setup.d2().eventModule().events().byEnrollmentUid().isNull().blockingCount();
    }

    public static int dataValueCount() {
        return setup.d2().dataValueModule().dataValues().blockingCount();
    }

    public static int noteModule() {
        return setup.d2().noteModule().notes().blockingCount();
    }

    public static int organisationUnitModel() {
        return setup.d2().organisationUnitModule().organisationUnits().blockingCount();
    }
    public static int periodModule() {
        return setup.d2().periodModule().periods().blockingCount();
    }
}

