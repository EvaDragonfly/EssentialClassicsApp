package com.memoittech.cuviewtv.screens.searchScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.memoittech.cuviewtv.components.MemberHorizontalItem
import com.memoittech.cuviewtv.viewModel.MembersViewModel
import kotlinx.coroutines.flow.debounce

@Composable
fun ComposersComponent(navController: NavController, q : String){

    val composersViewModel : MembersViewModel = viewModel()

    var ordering by remember { mutableStateOf("-created_at") }

    val listState = rememberLazyListState()

//    composersViewModel.getComposerList(0, ordering, q, 1)

    fun memberClickHandler(id: Int){
        navController.navigate("member_details/${id}")
    }

    LaunchedEffect(q) {
        snapshotFlow{ q }
            .debounce(2000) // Wait for 2 seconds of inactivity
            .collect { value ->
                if (value.length >= 3) {
                    composersViewModel.getComposerList(0, ordering, q, 0)
                }
            }
        listState.scrollToItem(0)
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index >= composersViewModel.composers.size - 10 && !composersViewModel.isComposerLoading) {
                    composersViewModel.getComposerList(0, ordering, q, 1)
                }
            }
    }

    Column() {
        composersViewModel.composers?.let {
            LazyColumn (
                state = listState
            ){
                items(items = it){item ->
                    MemberHorizontalItem(item, {memberClickHandler(item.id)})
                }
            }
        }
    }


}






