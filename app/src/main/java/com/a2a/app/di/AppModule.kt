package com.a2a.app.di

import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.network.UserApi
import com.a2a.app.utils.Constant.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        //set timeout
        httpClientBuilder
            .connectTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)

        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClientBuilder.addInterceptor(loggingInterceptor)
        httpClientBuilder.addNetworkInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("lang", "")
            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }

    //interface
    @Singleton
    @Provides
     fun getUserAPI(retrofit: Retrofit) = retrofit.create(UserApi::class.java)

    @Singleton
    @Provides
    fun getCustomApi(retrofit: Retrofit): CustomApi{
        return retrofit.create(CustomApi::class.java)
    }
}