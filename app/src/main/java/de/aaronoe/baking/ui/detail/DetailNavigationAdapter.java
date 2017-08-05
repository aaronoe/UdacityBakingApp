package de.aaronoe.baking.ui.detail;

/**
 * Created by private on 8/1/17.
 *
 *
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Ingredient;
import de.aaronoe.baking.model.Step;

import static android.view.View.GONE;

class DetailNavigationAdapter extends RecyclerView.Adapter<DetailNavigationAdapter.StepViewHolder> {

    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    private Context mContext;
    private StepClickCallback callback;

    public DetailNavigationAdapter(Context context, StepClickCallback callback) {
        mContext = context;
        this.callback = callback;
    }

    public void setStepList(List<Step> stepList, List<Ingredient> ingredientList) {
        this.stepList = stepList;
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        // Ingredients here:
        if (position == 0) {
            holder.nameTv.setText(getIngredientString(ingredientList));
            holder.numberTv.setVisibility(GONE);
        } else {
            Step step = stepList.get(position - 1);
            holder.nameTv.setText(step.getShortDescription());
            holder.numberTv.setText(String.valueOf(step.getId() + 1));
        }
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return stepList == null ? 0 : stepList.size() + 1;
    }

    interface StepClickCallback {
        void onClickStep(int position);
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        @BindView(R.id.step_name_tv)
        TextView nameTv;

        @BindView(R.id.step_number_tv)
        TextView numberTv;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition != 0) {
                callback.onClickStep(adapterPosition);
            }
        }
    }

    private String getIngredientString(List<Ingredient> ingredientsList) {
        String ingredients = "";
        for (int i = 0; i < ingredientsList.size(); i++) {
            ingredients += (" - " + mContext.getString(
                    R.string.quantity_ingredient,
                    ingredientsList.get(i).getQuantity(),
                    ingredientsList.get(i).getMeasure()) + " - " + ingredientsList.get(i).getIngredient() );
            if (i != ingredientsList.size() - 1) {
                ingredients += "\n";
            }
        }
        return ingredients;
    }

}
