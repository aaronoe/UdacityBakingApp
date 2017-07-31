package de.aaronoe.baking.ui.list;

import java.util.List;

import de.aaronoe.baking.model.Recipe;

/**
 * Created by private on 7/31/17.
 *
 */

interface ListContract {

    interface View {
        void showItems(List<Recipe> recipes);
        void showError();
    }

    interface Presenter {
        void getRecipes();
    }

}
