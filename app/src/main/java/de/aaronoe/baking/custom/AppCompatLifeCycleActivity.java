package de.aaronoe.baking.custom;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v7.app.AppCompatActivity;

/**
 * Class used to support {@link AppCompatActivity} features while also being a {@link LifecycleRegistryOwner}
 * Created by aoe on 8/5/17.
 */

@SuppressLint("Registered")
public class AppCompatLifeCycleActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }
}