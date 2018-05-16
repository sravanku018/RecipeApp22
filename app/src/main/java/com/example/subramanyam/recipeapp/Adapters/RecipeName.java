package com.example.subramanyam.recipeapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.subramanyam.recipeapp.R;
import com.example.subramanyam.recipeapp.data.RecipeItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeName extends RecyclerView.Adapter<RecipeName.ViewHolder>{
    private ArrayList<RecipeItem> recipeItems;

    private Context mContext;

    final private ListItemClickListener itemClickListener;


    public interface ListItemClickListener {

        void onListItemClick(RecipeItem clickedItemIndex);

    }



    public RecipeName(ListItemClickListener listener) {

        itemClickListener =listener;

    }

    public void setRecipeData(ArrayList<RecipeItem> recipeItems1,Context context)
    {
        recipeItems=recipeItems1;
        mContext=context;
        notifyDataSetChanged();

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_items,parent,false);
        return new ViewHolder(view) {
        };
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textRecyclerView.setText(recipeItems.get(position).getName() +"\n"+"Servings"+":"+ recipeItems.get(position).getServings());
        String imageUrl=recipeItems.get(position).getImage() ;

        if(!imageUrl.equals(""))
        {
            Uri uri=Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(mContext).load(uri).into(holder.imageRecyclerView);
        }

    }

    @Override
    public int getItemCount() {
        return recipeItems !=null ? recipeItems.size():0 ;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textRecyclerView;

        ImageView imageRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            textRecyclerView=itemView.findViewById(R.id.recipeTitle);
            imageRecyclerView=itemView.findViewById(R.id.recipeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickPosition=getAdapterPosition();
            itemClickListener.onListItemClick(recipeItems.get(clickPosition));



        }
    }
}
