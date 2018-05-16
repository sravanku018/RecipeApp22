package com.example.subramanyam.recipeapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class UpdateBakingService extends IntentService{

    public static String FROM_ACTIVITY_INGREDIENTS_LIST="FROM_ACTIVITY_INGREDIENTS_LIST";

    public UpdateBakingService()
    {
        super("UpdateBakingService");

    }

    public static void startBakingService(Context context, ArrayList<String> fromActivityIngrediantList)
    {
        Intent intent=new Intent(context,UpdateBakingService.class);
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngrediantList);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(intent !=null)
        {
            ArrayList<String> fromActivityIngrediantList=intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            handleActionUpdateBakingWidgets(fromActivityIngrediantList);
        }
    }

    public void handleActionUpdateBakingWidgets(ArrayList<String> fromActivityIngrediantList)
    {
        Intent intent=new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngrediantList);
        sendBroadcast(intent);
    }
}
