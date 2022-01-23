package org.techtown.plogging_android.Archive.Retrofit

data class ArchiveResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ArchivceResult
)