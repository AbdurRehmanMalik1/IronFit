import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workoutapp.api.ApiProvider
import com.workoutapp.api.MetricsRepository

import com.workoutapp.models.MetricUI
import com.workoutapp.utils.getDrawableByName
import com.workoutapp.viewmodels.MetricSelectUI
import kotlinx.coroutines.launch

class MetricsViewModel : ViewModel() {

    private val repository: MetricsRepository = MetricsRepository(ApiProvider.metricsApi())

    // 📊 Home screen snapshot
    val metrics = MutableLiveData<List<MetricUI>>()

    // 🎯 Metrics selection screen
    val selectableMetrics = MutableLiveData<List<MetricSelectUI>>()

    val error = MutableLiveData<String>()

    // -----------------------------
    // 📊 SNAPSHOT (HOME SCREEN)
    // -----------------------------
    fun loadSnapshot() {
        println("loading snap shot")
        viewModelScope.launch {
            try {
                val result = repository.getSnapshot()
                metrics.value = result
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

    // -----------------------------
    // 🎯 LOAD SELECTION SCREEN DATA
    // -----------------------------
    fun loadMetricSelection() {
        viewModelScope.launch {
            try {
                val defs = repository.getDefinitions()
                val subs = repository.getSubscriptions()

                val selectedSet = subs.map { it.metricType }.toSet()

                val result = defs.map {
                    MetricSelectUI(
                        type = it.type,
                        title = it.name,
                        unit = it.unit,
                        iconRes = getDrawableByName(it.icon),
                        color = it.color,
                        isSelected = selectedSet.contains(it.type)
                    )
                }

                selectableMetrics.postValue(result)

            } catch (e: Exception) {
                e.printStackTrace()
                error.postValue(e.message)
            }
        }
    }

    // -----------------------------
    // 💾 SAVE USER SELECTION
    // -----------------------------
    fun saveSubscriptions(selected: List<String>) {
        viewModelScope.launch {
            try {
                repository.saveSubscriptions(selected)

                // refresh after save
                loadMetricSelection()

            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}