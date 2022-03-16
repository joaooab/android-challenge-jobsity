package com.joaoovf.jobsity.domain.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.joaoovf.jobsity.domain.model.Show

class ShowListSource(
	private val fetch: suspend (position: Int) -> List<Show>,
) : PagingSource<Int, Show>() {

	override fun getRefreshKey(state: PagingState<Int, Show>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
				?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
		}
	}

	override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, Show> {
		val position = params.key ?: STARTING_PAGE_INDEX
		return try {
			val data = fetch(position)
			val nextKey = position + (params.loadSize / PAGE_SIZE)
			PagingSource.LoadResult.Page(
				data = data,
				prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
				nextKey = nextKey
			)
		} catch (e: Exception) {
			PagingSource.LoadResult.Error(e)
		}
	}

	companion object {
		private const val STARTING_PAGE_INDEX = 1
		const val PAGE_SIZE = 250
	}

}