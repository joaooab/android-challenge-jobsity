package com.joaoovf.jobsity.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.repository.ShowRepository

class ShowListSource(
	private val repository: ShowRepository,
	private val pageSize: Int
) : PagingSource<Int, Show>() {

	override fun getRefreshKey(state: PagingState<Int, Show>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
				?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
		}
	}

	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Show> {
		val position = params.key ?: STARTING_PAGE_INDEX
		return try {
			val data = repository.fetchAll(position)
			val nextKey = position + (params.loadSize / pageSize)
			LoadResult.Page(
				data = data,
				prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
				nextKey = nextKey
			)
		} catch (e: Exception) {
			LoadResult.Error(e)
		}
	}

	companion object {
		private const val STARTING_PAGE_INDEX = 1
	}

}