package com.joaoovf.jobsity.domain.model

data class Show(
    val id: Int,
    val _links: Links,
    val name: String,
    val image: Image?,
    val schedule: Schedule,
    val genres: List<String>,
    val summary: String,
//    val averageRuntime: Int,
//    val dvdCountry: Any,
//    val ended: String,
//    val externals: Externals,

//    val language: String,
//    val network: Network,
//    val officialSite: String,
//    val premiered: String,
//    val rating: Rating,
//    val runtime: Int,
//    val status: String,
//    val type: String,
//    val updated: Int,
//    val url: String,
//    val webChannel: Any,
//    val weight: Int
)