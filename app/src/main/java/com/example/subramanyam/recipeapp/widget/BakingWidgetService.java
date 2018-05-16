package com.example.subramanyam.recipeapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.subramanyam.recipeapp.R;

import java.util.List;

import static com.example.subramanyam.recipeapp.widget.RecipeProcess.ingrediantsList;

public class BakingWidgetService extends RemoteViewsService {
    List<String> remoteViewIngrediantList;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewFactory(this.getApplicationContext(),intent);
    }
    private class GridRemoteViewFactory implements RemoteViewsFactory
    {
        Context mcontext=null;
        private GridRemoteViewFactory(Context context,Intent intent)
        {
            mcontext=context;

        }


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

            remoteViewIngrediantList= ingrediantsList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return remoteViewIngrediantList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views=new RemoteViews(mcontext.getPackageName(), R.layout.recipe_process2);
            views.setTextViewText(R.id.recipe_process2,remoteViewIngrediantList.get(position));

            Intent fillInIntent=new Intent();
            views.setOnClickFillInIntent(R.layout.recipe_process2,fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
