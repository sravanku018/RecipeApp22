package com.example.subramanyam.recipeapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.subramanyam.recipeapp.R;
import com.example.subramanyam.recipeapp.userinterface.RecipeDescriptionActivity;

import java.util.ArrayList;

import static com.example.subramanyam.recipeapp.widget.UpdateBakingService.FROM_ACTIVITY_INGREDIENTS_LIST;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeProcess extends AppWidgetProvider {
    static ArrayList<String> ingrediantsList=new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_process);
        Intent appIntent=new Intent(context, RecipeDescriptionActivity.class);
        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.recipe_process,pendingIntent);

        Intent intent=new Intent(context,BakingWidgetService.class);
        views.setRemoteAdapter(R.id.recipe_process,intent);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //   for (int appWidgetId : appWidgetIds) {
        //  updateAppWidget(context, appWidgetManager, appWidgetId);
        // }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateRecipeProcess(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
        int[] appWidgetIds=appWidgetManager.getAppWidgetIds(new ComponentName(context,RecipeProcess.class));

        final String action=intent.getAction();

        if(action.equals("android.appwidget.action.APPWIDGET_UPDATE2"))
        {
            ingrediantsList=intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.recipe_process);

            RecipeProcess.updateRecipeProcess(context,appWidgetManager,appWidgetIds);
            super.onReceive(context, intent);
        }

    }
}