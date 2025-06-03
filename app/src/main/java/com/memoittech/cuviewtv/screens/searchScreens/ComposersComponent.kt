package com.memoittech.cuviewtv.screens.searchScreens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.components.MemberHorizontalItem
import com.memoittech.cuviewtv.ui.theme.GrayBlue
import com.memoittech.cuviewtv.ui.theme.Violet
import com.memoittech.cuviewtv.viewModel.AppViewModels
import com.memoittech.cuviewtv.viewModel.MembersViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@Composable
fun ComposersComponent(navController: NavController, appViewModel: AppViewModels){

    val composersViewModel : MembersViewModel = viewModel()

    var ordering by remember { mutableStateOf("-created_at") }

    val listState = rememberLazyListState()

    fun memberClickHandler(id: Int){
        navController.navigate("member_details/${id}")
    }

    var isFirstLaunch by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        // Scroll-based pagination trigger
        launch {
            snapshotFlow { listState.firstVisibleItemIndex }
                .distinctUntilChanged()
                .collect { index ->
                    if (
                        index >= composersViewModel.composers.size - 15 &&
                        !composersViewModel.isComposerLoading
                    ) {
                        composersViewModel.getComposerList(0, ordering, appViewModel.query, 1)
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
                        composersViewModel.getComposerList(0, ordering, value, 0)
                    } else if (value.isEmpty()) {
                        composersViewModel.getComposerList(0, ordering, "", 0)
                    }

                    listState.scrollToItem(0)
                }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        composersViewModel.composers.let {
            LazyColumn (
                state = listState
            ){
                items(items = it){item ->
                    MemberHorizontalItem(item) { memberClickHandler(item.id) }
                }
                item {
                    if (composersViewModel.isComposerLoading){
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






