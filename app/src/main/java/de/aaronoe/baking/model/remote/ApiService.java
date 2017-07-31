package de.aaronoe.baking.model.remote;

import java.util.List;

import de.aaronoe.baking.model.Recipe;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by private on 7/31/17.
 *
 */

public interface ApiService {

    @GET("59121517_baking/baking.json")
    Observable<List<Recipe>> getRecipes();

}
