import android.app.usage.UsageStatsManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import java.text.SimpleDateFormat
import java.util.*

class ScreenTimeLiveData(private val usageStatsManager: UsageStatsManager) : LiveData<String>() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onActive() {
        super.onActive()

        // Dummy data, gantilah dengan implementasi yang sesuai
        val startTime = System.currentTimeMillis() - 2 * 60 * 1000
        val endTime = System.currentTimeMillis()

        val usageStatsList =
            usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                startTime,
                endTime
            )

        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        var totalScreenTime = 0L

        for (usageStats in usageStatsList) {
            if (usageStats.lastTimeUsed > 0) {
                totalScreenTime += usageStats.totalTimeInForeground
            }
        }

        val formattedTime = dateFormat.format(Date(totalScreenTime))
        value = formattedTime
    }
}
