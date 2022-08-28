package net.rossmanges.refreshablecomposelist.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import net.rossmanges.refreshablecomposelist.Episode

class MainViewModel : ViewModel() {
    val isRefreshing = MutableStateFlow(false)

    fun getEpisodes(): List<Episode> {
        // TODO - the items should be generated dynamically
        // temporarily define static list of items
        val episodes = (0..19).map {
            Episode(id = it, name = "Episode ${(1..1000).random()}", isSelected = false)
        }
        return episodes
    }
}