package devcode.android.starter.service

import devcode.android.starter.model.ContactModel
import devcode.android.starter.model.CreateContactModel
import devcode.android.starter.model.CreateContactRequest
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiServices {
    @GET("/contacts")
    fun getContactList(): Single<ContactModel>

    @POST("/contacts")
    fun createContact(
        @Body body: CreateContactRequest
    ): Single<CreateContactModel>

    @DELETE("/contacts/{contactId}")
    fun deleteContact(
        @Path("contactId") contactId: Int,
    ): Single<ResponseBody>

    @PATCH("/contacts/{contactId}")
    fun patchContact(
        @Path("contactId") contactId: Int,
        @Body body: CreateContactRequest
    ): Single<CreateContactModel>
}