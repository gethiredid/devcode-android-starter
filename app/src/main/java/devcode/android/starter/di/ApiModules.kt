package devcode.android.starter.di

import devcode.android.starter.service.ApiRepository
import devcode.android.starter.service.ApiRepositoryImpl
import org.koin.dsl.module

val apiRepositoryModule = module {
    single<ApiRepository> {
        ApiRepositoryImpl(apiServices = get())
    }
}