package ec.medinamobile.bakify.detail.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ec.medinamobile.bakify.R;
import ec.medinamobile.bakify.entities.Ingredient;
import ec.medinamobile.bakify.utils.AdapterUtils;

/**
 * Created by Supertel on 3/7/17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private Context mContext;
    private Ingredient[] mIngredients;

    public IngredientsAdapter(Ingredient[] ingredients){
        mIngredients = ingredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ingredient_reverse, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = mIngredients[position];
        holder.name.setText(ingredient.getIngredient());
        holder.quantity.setText(AdapterUtils.getStringFromFloat(ingredient.getQuantity()));
        holder.measure.setImageResource(AdapterUtils.getImageResourceForMeasure(ingredient.getMeasure()));
    }

    @Override
    public int getItemCount() {
        if (mIngredients==null) return 0;
        return mIngredients.length;
    }

    public void swapIngredients(Ingredient[] ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ingredient_measure)
        ImageView measure;
        @BindView(R.id.ingredient_name)
        TextView name;
        @BindView(R.id.ingredient_quantity)
        TextView quantity;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
