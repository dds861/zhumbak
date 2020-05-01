package com.dd.conqazaqstan.sqlite.analytics;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsManager {
    private static AnalyticsManager single_instance = null;
    private static FirebaseAnalytics firebaseAnalytics;

    public static AnalyticsManager getInstance(Context context) {
        if (single_instance == null) {
            single_instance = new AnalyticsManager();
            firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        }
        return single_instance;
    }

    public static void registerEvent(String name, Bundle bundle ){
        firebaseAnalytics.logEvent(name, bundle);
    }
}
