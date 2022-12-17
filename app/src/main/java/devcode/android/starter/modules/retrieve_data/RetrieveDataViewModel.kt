package devcode.android.starter.modules.retrieve_data

import androidx.lifecycle.MutableLiveData
import devcode.android.starter.base.BaseViewModel
import devcode.android.starter.model.ContactItem
import devcode.android.starter.model.ContactModel
import devcode.android.starter.model.CreateContactRequest
import devcode.android.starter.service.ApiRepository
import devcode.android.starter.utils.RequestStatus
import devcode.android.starter.utils.getApiError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

enum class CreateEditContactResult {
    DUPLICATE, ERROR, IDLE
}

class RetrieveDataViewModel(private var apiRepository: ApiRepository): BaseViewModel() {
    var contactStatus = MutableLiveData(RequestStatus.IDLE)
    var createContactStatus = MutableLiveData(RequestStatus.IDLE)
    var updateContactStatus = MutableLiveData(RequestStatus.IDLE)
    var deleteContactStatus = MutableLiveData(RequestStatus.IDLE)
    var createEditContactResult = MutableLiveData(CreateEditContactResult.IDLE)

    var contactData: ContactModel? = null
    var newContact: ContactItem? = null
    var editedContact: ContactItem? = null
    var deletedContactIndex: Int? = null

    // Form data
    var fullname = ""
    var phoneNumber = ""
    var email = ""

    var onEditing = false

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

        apiRepository.createContact(CreateContactRequest(fullname, phoneNumber, email))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    newContact = it.data

                    createContactStatus.value = RequestStatus.SUCCESS
                },
                { cause ->
                    createContactStatus.value = RequestStatus.ERROR

                    val errorResponse = cause.getApiError()?.message ?: ""

                    createEditContactResult.value = if (errorResponse == "full_name, phone_number, and email is duplicate")
                        CreateEditContactResult.DUPLICATE else CreateEditContactResult.ERROR
                }
            ).addToDisposable()
    }

    fun updateContact() {
        if (updateContactStatus.value == RequestStatus.LOADING) return

        updateContactStatus.value = RequestStatus.LOADING

        apiRepository.updateContact(editedContact?.id ?: 0, CreateContactRequest(fullname, phoneNumber, email))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    editedContact = it.data

                    updateContactStatus.value = RequestStatus.SUCCESS
                },
                { cause ->
                    updateContactStatus.value = RequestStatus.ERROR

                    val errorResponse = cause.getApiError()?.message ?: ""

                    createEditContactResult.value = if (errorResponse == "full_name, phone_number, and email is duplicate")
                        CreateEditContactResult.DUPLICATE else CreateEditContactResult.ERROR
                }
            ).addToDisposable()
    }

    fun deleteContact(contactItem: ContactItem, index: Int) {
        if (deleteContactStatus.value == RequestStatus.LOADING) return

        deleteContactStatus.value = RequestStatus.LOADING

        apiRepository.deleteContact(contactItem.id ?: 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    deletedContactIndex = index
                    deleteContactStatus.value = RequestStatus.SUCCESS
                },
                { deleteContactStatus.value = RequestStatus.ERROR }
            ).addToDisposable()
    }
}