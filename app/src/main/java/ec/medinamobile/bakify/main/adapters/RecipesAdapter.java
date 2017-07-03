package ec.medinamobile.bakify.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.entities.Recipe;

/**
 * Created by Supertel on 1/7/17.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private Recipe[] mRecipes;
    private OnRecipeItemClickListener mListener;
    private Context context;

    public RecipesAdapter(Recipe[] recipes, OnRecipeItemClickListener listener){
        mRecipes = recipes;
        mListener = listener;
    }

    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesAdapter.ViewHolder holder, int position) {
        Recipe recipe = mRecipes[position];
        int[] colorArray = context.getResources().getIntArray(R.array.recipe_item_color_array);
        int colorPosition = position%5;
        holder.itemView.setBackgroundColor(colorArray[colorPosition]);
        holder.position = position;
        holder.name.setText(recipe.getName());

        String image = recipe.getImage();
        if (image!=null && !TextUtils.isEmpty(image)){
            Picasso.with(context).load(image).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        if (mRecipes==null) return 0;
        return mRecipes.length;
    }

    public void swapRecipes(Recipe[] recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_recipe_image)
        ImageView image;
        @BindView(R.id.item_recipe_name)
        TextView name;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onRecipeClicked(mRecipes[position]);
                }
            });
        }


    }
}
