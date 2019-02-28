package ir.beigirad.remote.service

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ir.beigirad.remote.utils.AuthenticationInterceptor
import ir.beigirad.remote.utils.Const
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceFactory {

    fun makeRemoteService(isDebug: Boolean, context: Context): ApiService {
        val stethoInterceptor = StethoInterceptor()
        val authenticationInterceptor = makeAuthenticationInterceptor()
        val okHttpClient = makeOkHttpClient(stethoInterceptor, authenticationInterceptor)
        val gson = makeGson()
        return makeRemoteService(okHttpClient, gson)
    }

    private fun makeRemoteService(okHttp: OkHttpClient, gson: Gson): ApiService {
        val retrofit = Retrofit.Builder()
            .client(okHttp)
            .baseUrl(Const.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
//            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
    }

    private fun makeOkHttpClient(
        stetho: StethoInterceptor,
        auth: AuthenticationInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(auth)
            .addInterceptor(stetho)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun makeAuthenticationInterceptor(): AuthenticationInterceptor {
        return AuthenticationInterceptor(Const.clientId, Const.clientSecret)
    }

}