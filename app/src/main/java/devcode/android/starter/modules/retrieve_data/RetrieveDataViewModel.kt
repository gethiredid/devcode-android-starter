package devcode.android.starter.modules.retrieve_data

import androidx.lifecycle.MutableLiveData
import devcode.android.starter.base.BaseViewModel
import devcode.android.starter.model.ContactItem
import devcode.android.starter.model.ContactModel
import devcode.android.starter.model.CreateContactRequest
import devcode.android.starter.service.ApiRepository
import devcode.android.starter.utils.RequestStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RetrieveDataViewModel(private var apiRepository: ApiRepository): BaseViewModel() {
    var contactStatus = MutableLiveData(RequestStatus.IDLE)
    var createContactStatus = MutableLiveData(RequestStatus.IDLE)

    var contactData: ContactModel? = null
    var newContact: ContactItem? = null

    // Form data
    var fullname = ""
    var phoneNumber = ""
    var email = ""

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

    fun createContact() {
        if (createContactStatus.value == RequestStatus.LOADING) return

        createContactStatus.value = RequestStatus.LOADING

        // TODO: Panggil function [createContact] yang terdapat pada class ApiRepository seperti pada function [getContactList] di atas.

        // TODO: Masukkan parameter kedalam function [createContact] yang berisi class [CreateContactRequest].

        // TODO: Masukkan parameter [fullname, phoneNumber, email] sesuai urutan dari class CreateContactRequest.

        // TODO: Copy code berikut ini kedalam lambda [onSuccess] pada function subscribe seperti contoh function [getContactList]
        /*
        newContact = it.data
        createContactStatus.value = RequestStatus.SUCCESS
        */
    }
}