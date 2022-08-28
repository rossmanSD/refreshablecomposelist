package net.rossmanges.refreshablecomposelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import net.rossmanges.refreshablecomposelist.ui.theme.RefreshableComposeListTheme
import net.rossmanges.refreshablecomposelist.viewmodels.MainViewModel

/**
 * Example project to implement a scrollable, refreshable, multi-selectable list
 * of tv-show episodes using JetPack Compose.
 *
 * Largely based off of these internet resources:
 *
 *  Philipp Lackner's Compose MultiSelect project on YT/GitHub:
 *  * https://youtu.be/pvNcJXprrKM
 *  * https://github.com/philipplackner/ComposeMultiSelect
 *
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RefreshableComposeListTheme {
                val viewModel: MainViewModel = viewModel()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RefreshableListWithMultiSelect()
                }
            }
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.blue_gray)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Pull down to refresh.",
            modifier = Modifier.padding(start = 16.dp)
        )
        Button(
            onClick = { /* TODO - retrieve list of selected episodes */ },
            modifier = Modifier.padding(end = 16.dp)
        ) {
            Text("Next")
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun RefreshableListWithMultiSelect(viewModel: MainViewModel = viewModel()) {
    var episodes by remember { mutableStateOf(viewModel.getEpisodes()) }
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(1000)
            refreshing = false
        }
    }
    SwipeRefresh(
        state = rememberSwipeRefreshState(refreshing),
        onRefresh = {
            refreshing = true
            episodes = viewModel.getEpisodes()
        }
    ) {
        LazyColumn(
            Modifier.fillMaxSize()
        ) {
            stickyHeader {
                Header()
            }

            items(episodes.size) { i ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            episodes = episodes.mapIndexed { j, item ->
                                if (i == j) {
                                    item.copy(isSelected = !item.isSelected)
                                } else item
                            }
                        }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = episodes[i].name)
                    if (episodes[i].isSelected)
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Selected",
                            modifier = Modifier.size(20.dp)
                        )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview(viewModel: MainViewModel = viewModel()) {
    RefreshableComposeListTheme {
        RefreshableListWithMultiSelect()
    }
}