package de.aaronoe.baking.db;


import de.aaronoe.baking.model.Recipe;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by private on 8/13/17.
 *
 */

public class RecipeDao {

    private Realm realm;

    public RecipeDao() {
        realm = Realm.getDefaultInstance();
    }

    public Observable<RealmResults<Recipe>> getAllRecipesObservable() {
        return realm
                .where(Recipe.class)
                .findAll()
                .asObservable();
    }

    public Observable<Recipe> getRecipeByIdObservable(int id) {
        return realm
                .where(Recipe.class)
                .equalTo("id", id)
                .findFirst()
                .asObservable();
    }

}
