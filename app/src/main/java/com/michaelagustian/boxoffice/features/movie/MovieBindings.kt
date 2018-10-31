package com.michaelagustian.boxoffice.features.movie

import android.arch.lifecycle.MutableLiveData
import android.databinding.BindingAdapter
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.michaelagustian.boxoffice.data.model.Movie

/**
 * Created by Michael Agustian on 31/10/18.
 */

object MovieBindings {

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: XRecyclerView, items: MutableLiveData<List<Movie>>) {
        with(recyclerView.adapter as MovieAdapter) {
            items.value?.let {
                replaceData(it)
            }
        }
    }

    @BindingAdapter("refreshComplete")
    @JvmStatic
    fun setRefresh(recyclerView: XRecyclerView, refresh: MutableLiveData<Boolean>) {
        refresh.value?.let {
            if (it) recyclerView.refreshComplete()
        }
    }

    @BindingAdapter("loadMoreComplete")
    @JvmStatic
    fun setLoadMore(recyclerView: XRecyclerView, loadMore: MutableLiveData<Boolean>) {
        loadMore.value?.let {
            if (it) recyclerView.loadMoreComplete()
        }
    }
}