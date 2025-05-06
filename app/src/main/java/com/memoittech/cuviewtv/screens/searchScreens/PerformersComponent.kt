package com.memoittech.cuviewtv.screens.searchScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun PerformersComponent( navController: NavController, q : String){

    var limit by remember { mutableStateOf(0) }
    var offset by remember { mutableStateOf(0) }
    var ordering by remember { mutableStateOf("position") }

    val performersViewModel : MembersViewModel = viewModel()

    LaunchedEffect(key1 = q) {
        performersViewModel.getPerformersList(limit, offset, ordering, q)
    }

    fun onMemberClick(id: Int){
        navController.navigate("member_details/${id}")
    }

    LaunchedEffect(q) {
        snapshotFlow{ q }
            .debounce(2000) // Wait for 2 seconds of inactivity
            .collect { value ->
                if (value.length >= 3) {
                    performersViewModel.getPerformersList(limit, offset, ordering, q)
                }
            }
    }

    Column() {
        performersViewModel.performersResponse?.let {
            LazyColumn (){
                items(items = it.results){item ->
                    MemberHorizontalItem(item, { onMemberClick(item.id) })
                }
            }
        }
    }


}









