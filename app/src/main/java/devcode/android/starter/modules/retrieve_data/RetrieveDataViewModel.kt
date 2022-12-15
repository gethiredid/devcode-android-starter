package devcode.android.starter.modules.retrieve_data

import androidx.lifecycle.MutableLiveData
import devcode.android.starter.base.BaseViewModel
import devcode.android.starter.model.ContactModel
import devcode.android.starter.service.ApiRepository
import devcode.android.starter.utils.RequestStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RetrieveDataViewModel(private var apiRepository: ApiRepository): BaseViewModel() {
    var contactStatus = MutableLiveData(RequestStatus.IDLE)
    var contactData: ContactModel? = null

    fun getContactList() {
        if (contactStatus.value == RequestStatus.LOADING) return

        contactStatus.value = RequestStatus.LOADING

        apiRepository.getContactList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    contactData = it

                    contactStatus.value = RequestStatus.SUCCESS
                },
                { contactStatus.value = RequestStatus.ERROR }
            ).addToDisposable()
    }
}