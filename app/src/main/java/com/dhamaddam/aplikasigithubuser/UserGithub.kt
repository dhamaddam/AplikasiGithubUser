package com.dhamaddam.aplikasigithubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserGithub(
    var Username: String,
    var Name: String,
    var Avatar: String,
    var company: String?,
    var location: String?,
    var repository: String?,
    var followers: String?,
    var following: String?
): Parcelable
