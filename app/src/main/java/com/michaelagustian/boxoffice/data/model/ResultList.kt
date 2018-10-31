package com.michaelagustian.boxoffice.data.model

/**
 * Created by Michael Agustian on 31/10/18.
 */

data class ResultList<T>(
        var page: Int? = 0,
        var totalResults: Int? = 0,
        var totalPages: Int? = 0,
        var results: List<T>? = null
)