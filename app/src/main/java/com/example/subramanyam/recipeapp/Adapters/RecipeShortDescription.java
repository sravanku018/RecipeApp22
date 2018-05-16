package com.example.subramanyam.recipeapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.subramanyam.recipeapp.R;
import com.example.subramanyam.recipeapp.data.RecipeItem;
import com.example.subramanyam.recipeapp.data.StepsItems;

import java.util.List;

public class RecipeShortDescription extends RecyclerView.Adapter<RecipeShortDescription.ViewHolder> {
    List<StepsItems> lSteps;

    private  String recipeName;
    final private ListItemClickListener itemClickListener;

    public interface ListItemClickListener {

        void onListItemClick(List<StepsItems> stepsOut,int clickedItemIndex,String recipeName);

    }



    public RecipeShortDescription(ListItemClickListener listener) {

        itemClickListener =listener;

    }

    public void RecipeIngrediantsData(List<RecipeItem> recipeItemList, Context mcontext)
    {
        lSteps=recipeItemList.get(0).getSteps();
        recipeName=recipeItemList.get(0).getName();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_shortdetails,parent,false);
        return new ViewHolder(view) {
        };
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.description.setText(lSteps.get(position).getId() +"." +lSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return lSteps !=null ? lSteps.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            description=itemView.findViewById(R.id.shortDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickPosition=getAdapterPosition();
            itemClickListener.onListItemClick(lSteps,clickPosition,recipeName);

        }
    }
}
