package de.aaronoe.baking;

import android.app.Application;

import de.aaronoe.baking.di.component.NetComponent;
import de.aaronoe.baking.di.modules.AppModule;
import de.aaronoe.baking.di.modules.NetModule;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by private on 7/31/17.
 *
 */

public class BakingApp extends Application {

    private NetComponent mNetComponent;
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/";

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Realm.init(this);

/*
        mNetComponent = DaggerNetComponent
                .builder()
                .netModule(new NetModule(BASE_URL))
                .appModule(new AppModule(this))
                .build(); */
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
