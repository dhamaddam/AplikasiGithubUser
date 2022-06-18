package com.dhamaddam.aplikasigithubuser

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        WifiService.instance.initializeWithApplicationContext(WifiService.mContext)
        if (!WifiService.instance.isOnline()) {
            throw IOException("No internet connection")
        } else {
            return chain.proceed(chain.request())
        }
    }
}