package devcode.android.starter.model

data class CreateContactRequest(
    val full_name: String,
    val phone_number: String,
    val email: String
)
