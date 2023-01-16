package com.a2a.app.di

import com.a2a.app.data.network.CustomApi
import com.a2a.app.data.network.SettingApi
import com.a2a.app.data.network.UserApi
import com.a2a.app.data.repository.*
import com.a2a.app.domain.repository.ProfileRepository
import com.a2a.app.domain.use_case.custom_use_case.*
import com.a2a.app.domain.use_case.user_use_case.AddressListUseCase
import com.a2a.app.domain.use_case.user_use_case.BookUseCases
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
import retrofit2.create
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

    @Provides
    @Singleton
    fun buildOkHttpClient(): OkHttpClient {
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
    fun getUserAPI(retrofit: Retrofit): UserApi = retrofit.create()

    @Singleton
    @Provides
    fun getCustomApi(retrofit: Retrofit): CustomApi = retrofit.create()

    @Singleton
    @Provides
    fun provideSettingApi(retrofit: Retrofit): SettingApi = retrofit.create()

    @Singleton
      @Provides
      fun provideSettingRepository(settingApi: SettingApi): SettingRepository {
          return SettingRepositoryImpl(settingApi)
      }

    @Singleton
    @Provides
    fun provideProfileRepository(userApi: UserApi): ProfileRepository {
        return ProfileRepositoryImpl(userApi)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepositoryImpl(userApi)
    }

    @Singleton
    @Provides
    fun provideCustomRepository(customApi: CustomApi): CustomRepository {
        return CustomRepositoryImpl(customApi)
    }

    @Singleton
    @Provides
    fun provideBookUseCases(
        profileRepository: ProfileRepository,
        customRepository: CustomRepository,
        userRepository: UserRepository
    ): BookUseCases {
        return BookUseCases(
            addressListUseCase = AddressListUseCase(profileRepository),
            categoryUseCase = CategoryUseCase(customRepository),
            checkAvailableTimeSlotsUseCase = CheckAvailableTimeSlotsUseCase(customRepository),
            estimateBookingUseCase = EstimateBookingUseCase(userRepository),
            serviceTypeUseCase = ServiceTypeUseCase(customRepository),
            subCategoryUseCase = SubCategoryUseCase(customRepository),
            cutoffTimeCheckUseCase = CutoffTimeCheckUseCase(customRepository)
        )
    }
}