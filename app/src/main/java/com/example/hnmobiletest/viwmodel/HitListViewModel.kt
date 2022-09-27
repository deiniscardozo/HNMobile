package com.example.hnmobiletest.viwmodel

import androidx.lifecycle.ViewModel
import com.example.hnmobiletest.model.database.HitListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HitListViewModel @Inject constructor(
    repository:HitListRepository
) : ViewModel() {
    val hits = repository.getHits()
}