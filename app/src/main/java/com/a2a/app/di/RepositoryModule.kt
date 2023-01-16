package com.a2a.app.di

import com.a2a.app.data.repository.*
import com.a2a.app.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    /*@Binds
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindCustomRepository(
        customRepository: CustomRepositoryImpl
    ): CustomRepository*/

    /*@Binds
    abstract fun bindProfileRepository(
        profileRepositoryImpl: ProfileRepositoryImpl
    ): ProfileRepository*/

    /*@Binds
    abstract fun bindSettingRepositorys(
        baseRepository: BaseRepository
    ): SettingRepositorys*/
}