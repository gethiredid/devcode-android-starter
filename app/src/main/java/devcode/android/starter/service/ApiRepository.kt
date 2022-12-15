package devcode.android.starter.service

import devcode.android.starter.model.ContactModel
import devcode.android.starter.model.CreateContactModel
import devcode.android.starter.model.CreateContactRequest
import io.reactivex.Single
import okhttp3.ResponseBody

interface ApiRepository {
    fun getContactList(): Single<ContactModel>
    fun createContact(body: CreateContactRequest): Single<CreateContactModel>
    fun deleteContact(contactId: Int): Single<ResponseBody>
    fun patchContact(contactId: Int, body: CreateContactRequest): Single<CreateContactModel>
}

class ApiRepositoryImpl(private val apiServices: ApiServices) : ApiRepository {
    override fun getContactList(): Single<ContactModel> = apiServices.getContactList()

    override fun createContact(body: CreateContactRequest): Single<CreateContactModel> = apiServices.createContact(body)

    override fun deleteContact(contactId: Int): Single<ResponseBody> = apiServices.deleteContact(contactId)

    override fun patchContact(contactId: Int, body: CreateContactRequest): Single<CreateContactModel> = apiServices.patchContact(contactId, body)
}