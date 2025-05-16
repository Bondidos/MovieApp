package com.bondidos.exceptions

sealed class CacheExceptions: Exception() {
    data object TrendingMovieEmptyCache: CacheExceptions() {
        private fun readResolve(): Any = TrendingMovieEmptyCache
    }
    data object AnticipatedMovieEmptyCache: CacheExceptions() {
        private fun readResolve(): Any = AnticipatedMovieEmptyCache
    }
}