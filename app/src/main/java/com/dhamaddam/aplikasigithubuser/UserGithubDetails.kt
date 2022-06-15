package com.dhamaddam.aplikasigithubuser

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class UserGithubDetails(

    var Username: String,
    var Name: String,
    var Avatar: Int,

    var company: String?,
    var location: String?,
    var repository: String?,
    var followers: String?,
    var following: String?,

): Parcelable
