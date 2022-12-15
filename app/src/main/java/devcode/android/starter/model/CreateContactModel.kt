package devcode.android.starter.model

import com.google.gson.annotations.SerializedName

data class CreateContactModel(
    @SerializedName("data")
    val data: ContactItem?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)