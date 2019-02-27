package ir.beigirad.remote.utils

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Farhad Beigirad on 2/27/19.
 */
class AuthenticationInterceptor(
    private val clientId: String,
    private val clientSecret: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()

        val newUrl = originRequest.url().newBuilder()
            .addQueryParameter("client_id", clientId)
            .addQueryParameter("client_secret", clientSecret)
            .addQueryParameter("v", Const.apiVersion)
            .build()

        val newRequest = originRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

}