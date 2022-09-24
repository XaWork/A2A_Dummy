package com.a2a.app.di

import androidx.fragment.app.Fragment
import com.a2a.app.common.BaseFragment
import com.a2a.app.common.BaseFragment1
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FragmentModule {

    @Provides
    fun provideBaseFragment(fragment: Fragment): BaseFragment1 {
        check(fragment is BaseFragment1) { "Every Activity is expected to extend BaseActivity" }
        return fragment
    }
}