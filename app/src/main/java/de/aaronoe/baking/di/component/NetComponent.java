package de.aaronoe.baking.di.component;

import javax.inject.Singleton;

import dagger.Component;
import de.aaronoe.baking.ui.list.MainActivity;
import de.aaronoe.baking.di.modules.AppModule;
import de.aaronoe.baking.di.modules.NetModule;

/**
 * Created by private on 7/31/17.
 *
 */

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(MainActivity mainActivity);

}