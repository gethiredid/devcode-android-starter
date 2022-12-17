package devcode.android.starter.modules.retrieve_data

import android.util.Patterns
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
    var validateInput = MutableLiveData(false)

    var contactData: ContactModel? = null
    var newContact: ContactItem? = null
    var editedContact: ContactItem? = null
    var deletedContactIndex: Int? = null

    // Form data
    var fullname = ""
    var phoneNumber = ""
    var email = ""

    var onEditing = false

    fun errorInput(key: String): String? {
        if (validateInput.value == false) return null

//        TODO: Uncomment code di bawah yang berfungsi untuk menampilkan pesan error berdasarkan ketentuan validasi
//        return when (key) {
//            "fullname" -> if (fullname.trim().isEmpty()) "Field nama tidak boleh kosong" else null
//            "phone" -> if (phoneNumber.trim().isEmpty()) "Field nomor telephone tidak boleh kosong" else if (!Patterns.PHONE.matcher(phoneNumber.trim()).matches()) "Format nomor telepon tidak sesuai" else null
//            "email" -> if (email.trim().isEmpty()) "Field email tidak boleh kosong" else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) "Format email tidak sesuai" else null
//            else -> null
//        }
    }

    fun checkForm(): Boolean {
        validateInput.value = true

//        TODO: Code di bawah masih kurang validasi untuk email, tolong tulis code yang berfungsi untuk validasi email
//         seperti pada code di bawah
        return  errorInput("fullname") != null || errorInput("phone") != null
    }

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

        if (checkForm()) return

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

        if (checkForm()) return

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
                { cause ->
                    deleteContactStatus.value = RequestStatus.ERROR
                }
            ).addToDisposable()
    }
}