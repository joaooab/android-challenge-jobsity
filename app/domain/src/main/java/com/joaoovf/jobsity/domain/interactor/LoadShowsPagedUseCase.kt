package com.joaoovf.jobsity.domain.interactor

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.joaoovf.jobsity.domain.repository.ShowRepository
import com.joaoovf.jobsity.domain.model.Show
import com.joaoovf.jobsity.domain.source.ShowListSource

interface LoadShowsPagedUseCase {

	operator fun invoke(query: String?): Pager<Int, Show>

}

class LoadShowsPagedUseCaseImpl(
	private val repository: ShowRepository,
) : LoadShowsPagedUseCase {

	override fun invoke(query: String?): Pager<Int, Show> {
		return Pager(
			PagingConfig(pageSize = ShowListSource.PAGE_SIZE),
		) {
			if (query.isNullOrEmpty()) {
				ShowListSource { position ->
					repository.fetchAll(position)
				}
			} else {
				ShowListSource {
					repository.fetchByQuery(query)
				}
			}
		}
	}

}