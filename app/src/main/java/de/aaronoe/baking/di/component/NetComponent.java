package de.aaronoe.baking.di.component;

import javax.inject.Singleton;

import dagger.Component;
import de.aaronoe.baking.di.modules.AppModule;
import de.aaronoe.baking.di.modules.NetModule;
import de.aaronoe.baking.storage.RecipeInfoManager;
import de.aaronoe.baking.ui.detail.DetailActivity;
import de.aaronoe.baking.ui.list.ListViewModel;
import de.aaronoe.baking.ui.list.MainActivity;
import de.aaronoe.baking.widget.IngredientListWidgetService;
import de.aaronoe.baking.widget.RecipeWidgetProvider;

/**
 * Created by private on 7/31/17.
 *
 */

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(MainActivity mainActivity);
    void inject(ListViewModel viewModel);
    void inject(IngredientListWidgetService.GridRemoteViewsFactory remoteViewsFactory);
    void inject(RecipeWidgetProvider recipeWidgetProvider);
    void inject(RecipeInfoManager recipeInfoManager);
    void inject(DetailActivity detailActivity);

}