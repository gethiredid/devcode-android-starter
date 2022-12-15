package devcode.android.starter.model

import com.google.gson.annotations.SerializedName

data class ContactModel(
    @SerializedName("data")
    val data: List<ContactItem>?,
    @SerializedName("status")
    val status: String?
)

data class ContactItem(
    @SerializedName("email")
    val email: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("phone_number")
    val phoneNumber: String?
)