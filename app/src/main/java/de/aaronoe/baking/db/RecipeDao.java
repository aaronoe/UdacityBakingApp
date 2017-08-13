package de.aaronoe.baking.db;


import java.util.List;

import de.aaronoe.baking.model.Recipe;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by private on 8/13/17.
 *
 */

public class RecipeDao {

    Realm realm;

    public RecipeDao() {
        realm = Realm.getDefaultInstance();
    }

    public void saveRecipes(final List<Recipe> recipeList) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(recipeList);
            }
        });
    }

    public Observable<RealmResults<Recipe>> getAllRecipesObservable() {
        return realm
                .where(Recipe.class)
                .findAllAsync()
                .asObservable();
    }

    public Observable<Recipe> getRecipeByIdObservable(int id) {
        return realm
                .where(Recipe.class)
                .equalTo("id", id)
                .findFirstAsync()
                .asObservable();
    }

}