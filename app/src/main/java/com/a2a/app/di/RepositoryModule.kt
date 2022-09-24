package com.a2a.app.di

import com.a2a.app.common.BaseRepository
import com.a2a.app.data.repository.SettingRepository
import com.a2a.app.data.repository.SettingRepositoryImpl
import com.a2a.app.data.repository.UserRepository1
import com.a2a.app.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository1

    /*@Binds
    abstract fun bindSettingRepositorys(
        baseRepository: BaseRepository
    ): SettingRepositorys*/
}