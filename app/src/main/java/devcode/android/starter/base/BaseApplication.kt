package devcode.android.starter.base

import android.app.Application
import devcode.android.starter.di.apiRepositoryModule
import devcode.android.starter.di.networkModule
import devcode.android.starter.di.viewControllerModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class BaseApplication: Application() {
    companion object {
        val modules = listOf(
            viewControllerModule,
            apiRepositoryModule,
            networkModule,
        )
    }

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() = startKoin {
        androidContext(this@BaseApplication)

        val moduleList = arrayListOf<Module>().apply {
            addAll(modules)
        }

        modules(moduleList)
    }
}
