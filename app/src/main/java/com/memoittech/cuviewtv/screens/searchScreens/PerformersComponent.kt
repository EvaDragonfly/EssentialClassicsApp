package com.memoittech.cuviewtv.screens.searchScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.memoittech.cuviewtv.components.MemberHorizontalItem
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.viewModel.AppViewModels
import com.memoittech.cuviewtv.viewModel.MembersViewModel
import kotlinx.coroutines.FlowPreview

import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@Composable
fun PerformersComponent( navController: NavHostController, appViewModel: AppViewModels){

    var ordering by remember { mutableStateOf("-created_at") }

    val performersViewModel : MembersViewModel = viewModel()

    val listState = rememberLazyListState()

    var isFirstLaunch by remember { mutableStateOf(true) }

    fun onMemberClick(id: Int){
        navController.navigate("member_details/${id}")
    }

    LaunchedEffect(Unit) {
        // Scroll-based pagination trigger
        launch {
            snapshotFlow { listState.firstVisibleItemIndex }
                .distinctUntilChanged()
                .collect { index ->
                    if (
                        index >= performersViewModel.performers.size - 15 &&
                        !performersViewModel.isPerformerLoading
                    ) {
                        performersViewModel.getPerformersList(0, ordering, appViewModel.query, 1)
                    }
                }
        }

        // Query change trigger
        launch {
            snapshotFlow { appViewModel.query }
                .debounce(500)
                .distinctUntilChanged()
                .collect { value ->
                    if (isFirstLaunch) {
                        isFirstLaunch = false
                        return@collect
                    }

                    if (value.length >= 3) {
                        performersViewModel.getPerformersList(0, ordering, value, 0)
                    } else if (value.isEmpty()) {
                        performersViewModel.getPerformersList(0, ordering, "", 0)
                    }

                    listState.scrollToItem(0)
                }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        performersViewModel.performers.let {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = it) { item ->
                    MemberHorizontalItem(item, { onMemberClick(item.id) })
                }
                item {
                    if (performersViewModel.isPerformerLoading){
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Loading...",
                            textAlign = TextAlign.Center,
                            color = GrayBlue
                        )
                    }
                }
            }
        }
    }


}









