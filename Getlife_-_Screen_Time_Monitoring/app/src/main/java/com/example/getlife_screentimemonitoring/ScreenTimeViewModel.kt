import android.app.Application
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*

class ScreenTimeViewModel(application: Application) : AndroidViewModel(application) {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun getScreenTime(): LiveData<String> {
        val usageStatsManager =
            getApplication<Application>().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

        val screenTimeLiveData = MutableLiveData<String>()

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
        screenTimeLiveData.value = formattedTime

        return screenTimeLiveData
    }
}
