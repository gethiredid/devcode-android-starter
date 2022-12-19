package devcode.android.starter.modules.retrieve_data

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import devcode.android.starter.base.BaseViewModel
import devcode.android.starter.model.ContactItem
import devcode.android.starter.model.ContactModel
import devcode.android.starter.model.CreateContactRequest
import devcode.android.starter.model.ErrorModel
import devcode.android.starter.service.ApiRepository
import devcode.android.starter.utils.RequestStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

enum class CreateEditContactResult {
    DUPLICATE, ERROR, IDLE
}

class RetrieveDataViewModel(private var apiRepository: ApiRepository): BaseViewModel() {
    var contactStatus = MutableLiveData(RequestStatus.IDLE)
    var createContactStatus = MutableLiveData(RequestStatus.IDLE)
    var createEditContactResult = MutableLiveData(CreateEditContactResult.IDLE)

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
//        TODO: Uncomment code di bawah untuk melakukan request POST create contact
//        if (createContactStatus.value == RequestStatus.LOADING) return
//
//        createContactStatus.value = RequestStatus.LOADING
//
//        apiRepository.createContact(CreateContactRequest(full_name = fullname, email = email, phone_number = phoneNumber))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    newContact = it.data
//
//                    createContactStatus.value = RequestStatus.SUCCESS
//                },
//                { cause ->
//                    createContactStatus.value = RequestStatus.ERROR
//
//                    // Catch error response from Retrofit
//                    if (cause is HttpException) {
//                        try {
//                            val errorResponse = Gson().fromJson(cause.response()?.errorBody()?.charStream(), ErrorModel::class.java)
//
//                            if (errorResponse.message == "full_name, phone_number, and email is duplicate") {
//                                createEditContactResult.value = CreateEditContactResult.DUPLICATE
//                            }
//                        } catch (e: Exception) {
//                            createEditContactResult.value = CreateEditContactResult.ERROR
//                        }
//                    } else {
//                        createEditContactResult.value = CreateEditContactResult.ERROR
//                    }
//                }
//            ).addToDisposable()
    }
}