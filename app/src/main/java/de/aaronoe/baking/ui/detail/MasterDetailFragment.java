package de.aaronoe.baking.ui.detail;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Ingredient;
import de.aaronoe.baking.model.Step;

/**
 * Created by private on 7/31/17.
 *
 */

@EFragment(R.layout.master_detail)
public class MasterDetailFragment extends Fragment {

    @BindView(R.id.master_detail_rv)
    RecyclerView masterDetailRv;

    List<Ingredient> mIngredientList;
    Step mStep;

    public void setIngredients(@FragmentArg ArrayList<Ingredient> ingredients) {
        mIngredientList = ingredients;
    }

    public void setSteps(@FragmentArg Step step) {
        mStep = step;
    }

}