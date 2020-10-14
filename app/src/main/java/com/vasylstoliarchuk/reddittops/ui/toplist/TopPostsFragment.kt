package com.vasylstoliarchuk.reddittops.ui.toplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasylstoliarchuk.reddittops.R
import com.vasylstoliarchuk.reddittops.data.Resource
import com.vasylstoliarchuk.reddittops.ui.common.paging.OnFetchNextPageListener
import com.vasylstoliarchuk.reddittops.ui.common.paging.PagingVerticalScrollListener
import com.vasylstoliarchuk.reddittops.ui.toplist.model.TopPostsItem
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.top_posts_fragment.*
import javax.inject.Inject

class TopPostsFragment : DaggerFragment() {

    companion object {
        fun newInstance() = TopPostsFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TopPostsViewModel
    private val adapter: TopPostsRecyclerAdapter by lazy { TopPostsRecyclerAdapter() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.top_posts_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(PagingVerticalScrollListener().apply {
            addOnPagingDataListener(object : OnFetchNextPageListener {
                override fun onFetchNextPage() {
                    viewModel.loadMoreTopPosts()
                }
            })
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[TopPostsViewModel::class.java]
        viewModel.topPostsLiveData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    adapter.swapItems(data.value.map { TopPostsItem(it.id!!, it.title!!) })// TODO: 10/14/2020 Actualize
                }
                is Resource.Failure -> {

                }
                Resource.Loading -> {

                }
            }
        }

        viewModel.nextPageLiveData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    adapter.appendItems(data.value.map { TopPostsItem(it.id!!, it.title!!) }) // TODO: 10/14/2020 Actualize
                }
                is Resource.Failure -> {

                }
                Resource.Loading -> {

                }
            }
        }
    }

}