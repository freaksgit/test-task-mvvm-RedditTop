package com.vasylstoliarchuk.reddittops.ui.toplist

import androidx.lifecycle.*
import com.vasylstoliarchuk.reddittops.data.Repository
import com.vasylstoliarchuk.reddittops.data.Resource
import com.vasylstoliarchuk.reddittops.data.map
import com.vasylstoliarchuk.reddittops.data.model.RedditPost
import com.vasylstoliarchuk.reddittops.ui.toplist.adapter.TopPostsItem
import kotlinx.coroutines.launch
import javax.inject.Inject


class TopPostsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var isLoadingNextPage = false
    private val _refresh: MutableLiveData<Boolean> = MutableLiveData(false)
    val topPostsLiveData: LiveData<Resource<List<TopPostsItem>>> = _refresh.switchMap { refresh ->
        liveData {
            emit(Resource.Loading)
            emit(mapToUiModels(repository.getTopPosts(refresh)))
        }
    }

    private val _nextPageLiveData = MutableLiveData<Resource<List<TopPostsItem>>>()
    val nextPageLiveData: LiveData<Resource<List<TopPostsItem>>> = _nextPageLiveData

    fun loadMoreTopPosts() {
        if (!isLoadingNextPage) {
            isLoadingNextPage = true
            viewModelScope.launch {
                _nextPageLiveData.value = Resource.Loading
                _nextPageLiveData.value = mapToUiModels(repository.loadMoreTopPosts())
                isLoadingNextPage = false
            }
        }
    }

    private fun mapToUiModels(resource: Resource<List<RedditPost>>): Resource<List<TopPostsItem>> {
        return resource.map { posts -> posts.map { TopPostsItem.from(it) } }
    }

    override fun onCleared() {
        super.onCleared()
        isLoadingNextPage = false
    }

    fun refresh() {
        _refresh.value = true
    }
}