package com.vasylstoliarchuk.reddittops.ui.toplist

import androidx.lifecycle.*
import com.vasylstoliarchuk.reddittops.data.Repository
import com.vasylstoliarchuk.reddittops.data.Resource
import com.vasylstoliarchuk.reddittops.data.model.RedditPost
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


class TopPostsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _refresh: MutableLiveData<Boolean> = MutableLiveData(true)
    private var fetchNextPageJob: Job? = null
    val topPostsLiveData: LiveData<Resource<List<RedditPost>>> = _refresh.switchMap { refresh ->
        liveData {
            emit(Resource.Loading)
            emit(repository.getTopPosts(refresh))
        }
    }

    private val _nextPageLiveData = MutableLiveData<Resource<List<RedditPost>>>()
    val nextPageLiveData: LiveData<Resource<List<RedditPost>>> = _nextPageLiveData

    fun loadMoreTopPosts() {
        if (fetchNextPageJob == null) {
            fetchNextPageJob = viewModelScope.launch {
                _nextPageLiveData.value = repository.loadMoreTopPosts()
                fetchNextPageJob = null
            }
        }
    }


    fun refresh() {
        _refresh.value = true
    }
}