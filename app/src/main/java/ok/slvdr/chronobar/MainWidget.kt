package ok.slvdr.chronobar

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [MainWidgetConfigureActivity]
 */
class MainWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
            Logger.getGlobal().log(Level.INFO,"on Update called.")
        }





    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.

    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.main_widget)

    // Update the percentage

    val currentDate: LocalDateTime = LocalDateTime.now()
    val percentageYear: Int = (currentDate.dayOfYear) * 100 / LocalDate.now().lengthOfYear()

    Logger.getGlobal().log(Level.INFO,"Porcentaje: $percentageYear")


    views.setProgressBar(R.id.yearBar,100,percentageYear,false)
    views.setTextViewText(R.id.textYear, "Año: $percentageYear%")

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}