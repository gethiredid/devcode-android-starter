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

//    TODO: Uncomment code di bawah untuk melakukan DELETE contact request.
//    @DELETE("/contacts/{contactId}")
//    fun deleteContact(
//        @Path("contactId") contactId: Int,
//    ): Single<ResponseBody>

//    TODO: Uncomment code di bawah untuk melakukan PUT contact request.
//    @PUT("/contacts/{contactId}")
//    fun updateContact(
//        @Path("contactId") contactId: Int,
//        @Body body: CreateContactRequest
//    ): Single<CreateContactModel>
}