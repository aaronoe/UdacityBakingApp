package de.aaronoe.baking.ui.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.aaronoe.baking.R;
import de.aaronoe.baking.model.Recipe;

/**
 * Created by private on 7/31/17.
 *
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private RecipeClickListener clickListener;
    private Context mContext;

    RecipeAdapter(Context context, RecipeClickListener clickListener) {
        mContext = context;
        this.clickListener = clickListener;
    }

    void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipeItem = recipeList.get(position);
        holder.recipeNameTv.setText(recipeItem.getName());
        holder.servingsTv.setText(recipeItem.getServings() + " Servings");
        if (recipeItem.getImage() != null && !TextUtils.isEmpty(recipeItem.getImage())) {
            holder.recipeImageView.setVisibility(View.VISIBLE);
            Picasso.with(mContext)
                    .load(recipeItem.getImage())
                    .into(holder.recipeImageView);
        } else {
            holder.recipeImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return recipeList == null ? 0 : recipeList.size();
    }

    interface RecipeClickListener {
        void clickOnRecipe(Recipe recipe);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.recipe_servings_tv)
        TextView servingsTv;

        @BindView(R.id.recipe_name_tv)
        TextView recipeNameTv;

        @BindView(R.id.recipe_item_image_view)
        ImageView recipeImageView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.clickOnRecipe(recipeList.get(getAdapterPosition()));
        }
    }

}