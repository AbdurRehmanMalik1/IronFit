import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.workoutapp.api.ApiProvider
import com.workoutapp.api.MetricsRepository

import com.workoutapp.models.MetricUI
import com.workoutapp.utils.getDrawableByName
import com.workoutapp.models.MetricsSelectUI
import kotlinx.coroutines.launch

class MetricsViewModel : ViewModel() {

    private val repository: MetricsRepository = MetricsRepository(ApiProvider.metricsApi())

    val metrics = MutableLiveData<List<MetricUI>>()

    val selectableMetrics = MutableLiveData<List<MetricsSelectUI>>()

    val error = MutableLiveData<String>()

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

    fun loadMetricSelection() {
        viewModelScope.launch {
            try {
                val definitions = repository.getDefinitions()
                val subs = repository.getSubscriptions()

                val selectedSet = subs.map { it.metricType }.toSet()

                val result = definitions.map {
                    MetricsSelectUI(
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