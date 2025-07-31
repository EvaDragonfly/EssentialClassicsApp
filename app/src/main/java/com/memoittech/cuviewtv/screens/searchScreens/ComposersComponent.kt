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
import androidx.navigation.NavHostController
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
fun ComposersComponent(navController: NavHostController, appViewModel: AppViewModels){

    var ordering by remember { mutableStateOf("-created_at") }

    val composersViewModel : MembersViewModel = viewModel()

    val listState = rememberLazyListState()

    var isFirstLaunch by remember { mutableStateOf(true) }

    var page by remember { mutableStateOf(0) }

    fun onMemberClick(id: Int){
        navController.navigate("member_details/${id}")
    }


    LaunchedEffect(appViewModel.query) {
        page = 0
        if (appViewModel.query.length >= 2) {
            composersViewModel.getSearchComposerList(0, ordering, appViewModel.query, 0)
            isFirstLaunch = false
        } else if (appViewModel.query.isEmpty()) {
            if(composersViewModel.searchComposers.size == 0 || !isFirstLaunch){
                composersViewModel.getSearchComposerList(0, ordering, "", 0)
                isFirstLaunch = false
            }
        }
    }

    LaunchedEffect(page) {
        if (page > 0){
            composersViewModel.getSearchComposerList(0, ordering, appViewModel.query, 1)
            isFirstLaunch = false
        }
    }

    LaunchedEffect(Unit) {

        // Scroll-based pagination trigger
        launch {
            snapshotFlow { listState.firstVisibleItemIndex }
                .distinctUntilChanged()
                .collect { index ->
                    if (
                        index >= composersViewModel.searchComposers.size - 15 &&
                        !composersViewModel.isSearchComposerLoading
                    ) {
                        page += 1
                    }
                }
        }

    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        composersViewModel.searchComposers.let {
            LazyColumn (
                state = listState
            ){
                items(items = it){item ->
                    MemberHorizontalItem(item) { onMemberClick(item.id) }
                }
                item {
                    if (composersViewModel.isSearchComposerLoading){
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






