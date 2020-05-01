package com.dd.conqazaqstan.sqlite.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.dd.conqazaqstan.sqlite.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WidgetView()
    : AppWidgetProvider() {
    companion object {
        private const val SYNC_CLICKED = "automaticWidgetSyncButtonClick"
    }
    /**
     * Default variables
     */
    /**
     * Custom variables
     */
    lateinit var widgetViewModel: WidgetViewModel

    /**
     * Default functions
     */
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        widgetViewModel = WidgetViewModel(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        widgetViewModel = WidgetViewModel(context)
        CoroutineScope(Dispatchers.Main).launch {
            if (SYNC_CLICKED == intent.action) {
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
                val watchWidget = ComponentName(context, WidgetView::class.java)
                val randomMakal = widgetViewModel.getRandomMakal()
                remoteViews.setTextViewText(R.id.appwidget_text, randomMakal)
                appWidgetManager.updateAppWidget(watchWidget, remoteViews)
            }
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        widgetViewModel = WidgetViewModel(context)
        CoroutineScope(Dispatchers.Main).launch {
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    /**
     * Custom functions
     */
    private fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    private suspend fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        // Construct the RemoteViews object
        val remoteViews = RemoteViews(context.packageName, R.layout.new_app_widget)
        //setting text to widget TextView
        val randomMakal = widgetViewModel.getRandomMakal()
        remoteViews.setTextViewText(R.id.appwidget_text, randomMakal)
        remoteViews.setOnClickPendingIntent(R.id.appwidget_text, getPendingSelfIntent(context, SYNC_CLICKED))
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }
}