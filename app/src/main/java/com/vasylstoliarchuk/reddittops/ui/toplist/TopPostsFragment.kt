package com.vasylstoliarchuk.reddittops.ui.toplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasylstoliarchuk.reddittops.R
import com.vasylstoliarchuk.reddittops.data.Resource
import com.vasylstoliarchuk.reddittops.ui.common.recyclerview.SpacingBetweenItemDecoration
import com.vasylstoliarchuk.reddittops.ui.common.recyclerview.paging.OnFetchNextPageListener
import com.vasylstoliarchuk.reddittops.ui.common.recyclerview.paging.PagingVerticalScrollListener
import com.vasylstoliarchuk.reddittops.ui.toplist.adapter.ErrorRecyclerAdapter
import com.vasylstoliarchuk.reddittops.ui.toplist.adapter.ProgressRecyclerAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.top_posts_fragment.*
import javax.inject.Inject

class TopPostsFragment : DaggerFragment() {

    companion object {
        fun newInstance() = TopPostsFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TopPostsViewModel
    private val topPostsAdapter: TopPostsRecyclerAdapter by lazy { TopPostsRecyclerAdapter() }
    private val errorAdapter: ErrorRecyclerAdapter by lazy { ErrorRecyclerAdapter() }
    private val progressAdapter: ProgressRecyclerAdapter by lazy { ProgressRecyclerAdapter() }
    private var errorLayout: ViewGroup? = null

    private val pagingVerticalScrollListener = PagingVerticalScrollListener().apply {
        addOnPagingDataListener(object : OnFetchNextPageListener {
            override fun onFetchNextPage() {
                viewModel.loadMoreTopPosts()
            }
        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.top_posts_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorAdapter.onErrorClickListener = {
            errorAdapter.hideError()
            setPaginationEnabled(true)
            viewModel.loadMoreTopPosts()
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(SpacingBetweenItemDecoration(resources.getDimensionPixelSize(R.dimen.materialSpacing_half)))
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = ConcatAdapter(topPostsAdapter, errorAdapter, progressAdapter)
        setPaginationEnabled(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[TopPostsViewModel::class.java]
        viewModel.topPostsLiveData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    hideLoading()
                    topPostsAdapter.swapItems(data.value)
                }
                is Resource.Failure -> {
                    hideLoading()
                    showError(data.message) {
                        hideError()
                        viewModel.refresh()
                    }
                }
                Resource.Loading -> {
                    showLoading()
                }
            }
        }

        viewModel.nextPageLiveData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    progressAdapter.hideProgress()
                    topPostsAdapter.appendItems(data.value)
                }
                is Resource.Failure -> {
                    setPaginationEnabled(false)
                    progressAdapter.hideProgress()
                    errorAdapter.showError(ErrorRecyclerAdapter.ErrorItem(data.message))
                }
                Resource.Loading -> {
                    progressAdapter.showProgress()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        errorLayout = null
    }

    private fun showError(message: String, retryAction: () -> Unit) {
        if (errorLayout == null) {
            errorLayout = errorStub.inflate() as ViewGroup?
        }

        tvErrorMessage.text = message
        btnErrorRetry.setOnClickListener { retryAction() }

        errorLayout?.visibility = View.VISIBLE
    }

    private fun hideError() {
        errorLayout?.visibility = View.GONE
    }

    private fun showLoading() {
        pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        pbLoading.visibility = View.GONE
    }

    private fun setPaginationEnabled(enabled: Boolean) {
        if (enabled) {
            recyclerView?.addOnScrollListener(pagingVerticalScrollListener)
        } else {
            recyclerView?.clearOnScrollListeners()
        }
    }
}