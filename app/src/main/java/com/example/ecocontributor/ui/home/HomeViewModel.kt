package com.example.ecocontributor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
// SciStarter private API key 5XdLfZoqS0vlaczCGl5qXkJuvriLO9D4UXNbPLMB10PAEFt-KgwkRqniTTrgeUO-5PAwyHUhWfDbDAMQ3nYb-Q
//Credit Scistarter: <a href="https://scistarter.org/">Powered by SciStarter</a>
// Thank SciStarter <p>Special thanks to <a href="https://scistarter.org/">SciStarter</a> for sharing their project database. Visit their site for advanced searches.<p>
class HomeViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _projects = MutableStateFlow(listOf<Project>())

    val projects = searchText
        .debounce(1000L) //time to wait before showing search results so not calling api each keystroke
        .onEach { _isSearching.update {true}}
        .combine(_projects) { text, projects ->
        if(text.isBlank()) {
            projects
        } else {
            projects.filter {
                it.doesMatchSearchQuery(text)
            }

        }

    }
        .onEach { _isSearching.update{ false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _projects.value
        )
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    private val _text = MutableLiveData<String>().apply {
        value = "EcoContributor Home Screen" //Text that appears on home before complete.


    }
    val text: LiveData<String> = _text

    data class Project(
        val projectName: String,
        val projectSubject: String,

    ) {
        fun doesMatchSearchQuery(query: String): Boolean {
            val matchingCombinations = listOf(
                "$projectName",
                "$projectSubject",
                "$projectName$projectSubject",
                "$projectName $projectSubject",
                "${projectName.first()}",
                "${projectSubject.first()}",
            )
            return matchingCombinations.any {
                it.contains(query, ignoreCase = true)
            }
        }
    }
    private val allProjects = listOf(
    //This will turn into the project list from Scistarter API
    //but I wanted to have something to search for until then.
    Project(
        projectName = "Plant Finder",
        projectSubject = "rare plant identification"

    ),
    Project(
        projectName = "Grunion Greeters",
        projectSubject = "Grunion spawning on the coast"

    )
)
}