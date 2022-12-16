package devcode.android.starter.di

import devcode.android.starter.modules.retrieve_data.ContactAdapter
import devcode.android.starter.modules.retrieve_data.RetrieveDataActivity
import devcode.android.starter.modules.retrieve_data.RetrieveDataViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewControllerModule = module {
    scope(named<RetrieveDataActivity>()) {
        scoped { RetrieveDataViewModel(apiRepository = get()) }
        scoped { (activity: RetrieveDataActivity) -> ContactAdapter(context = androidContext(), activity) }
    }
}