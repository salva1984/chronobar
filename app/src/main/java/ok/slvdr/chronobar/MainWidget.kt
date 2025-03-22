package ok.slvdr.chronobar

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import androidx.core.content.ContextCompat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters
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

    // Update the bars
    updateYearBar(context, views)
    updateMonthBar(context, views)


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun updateYearBar(context: Context, views: RemoteViews) {
    val yearPercentage = calcYearPercentage()
    val textYear = ContextCompat.getString(context, R.string.year)
    views.setProgressBar(R.id.yearBar, 100, yearPercentage, false)
    views.setTextViewText(R.id.textYear, "$textYear: $yearPercentage%")
}

private fun updateMonthBar(context: Context, views: RemoteViews) {
    val monthPercentage = calcMonthPercentage()
    val textMonth = ContextCompat.getString(context, R.string.month)
    views.setProgressBar(R.id.monthBar, 100, monthPercentage.toInt(), false)
    views.setTextViewText(R.id.textMonth, "$textMonth: $monthPercentage%")
}

private fun updateDayBar(context: Context, views: RemoteViews) {
    val textDay = ContextCompat.getString(context,R.string.day)
    views.setProgressBar(R.id.dayBar,100,calcDayPercentage().toInt(),false)
    views.setTextViewText(R.id.textDay,"$textDay: ${calcDayPercentage().toInt()}%")
}



internal fun calcYearPercentage(): Int {
    return (LocalDateTime.now().dayOfYear) * 100 / LocalDate.now().lengthOfYear()
}

internal  fun calcMonthPercentage(): Long {
    // Create a LocalDateTime object that is exactly 00:00 at the start of the current month
    val nextMonthLocalDateTime = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())
                                                    .withHour(0)
                                                    .withMinute(0)
                                                    .withSecond(0)
                                                    .withNano(0)
    // Divide the hours that have passed since the start of the month over the total month hours (rule of 3)
    return Duration.between(nextMonthLocalDateTime, LocalDateTime.now()).toHours() * 100 / (LocalDate.now().lengthOfMonth() * 24)
}

internal fun calcDayPercentage(): Long {
    val startOfToday = LocalDateTime.now()
        .withHour(0)
        .withMinute(0)
        .withSecond(0)
        .withNano(0)

    // Percentage of minutes since the start of today.
    return Duration.between(startOfToday,LocalDateTime.now()).toMinutes() * 100 / (1440)
}