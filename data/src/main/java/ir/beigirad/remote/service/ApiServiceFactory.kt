package ir.beigirad.remote.service

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import ir.beigirad.remote.utils.Const
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceFactory {

    fun makeRemoteService(isDebug: Boolean, context: Context): ApiService {
        val chuckInterceptor = makeChuckInterceptor(isDebug, context)
        val stethoInterceptor = StethoInterceptor()
        val okHttpClient = makeOkHttpClient(chuckInterceptor, stethoInterceptor)
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
        interceptor: ChuckInterceptor,
        stetho: StethoInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(stetho)
            .addInterceptor(interceptor)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun makeChuckInterceptor(isDebug: Boolean, context: Context): ChuckInterceptor {
        return ChuckInterceptor(context)
            .showNotification(isDebug)
    }

}