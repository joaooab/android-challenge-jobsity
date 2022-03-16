package com.joaoovf.jobsity.mapper

internal inline fun <I, O> List<I>.mapList(mapSingle: (I.() -> O)): List<O> {
	return this.map { mapSingle(it) }
}

internal inline fun <I, O> List<I>?.mapFromNetwork(mapSingle: (I.() -> O)): List<O> {
	return this?.map { mapSingle(it) } ?: emptyList()
}

internal inline fun <I, O> List<I>.mapToRequest(mapSingle: (I.() -> O)): List<O>? {
	return if (this.isEmpty()) null else this.map { mapSingle(it) }
}