package devcode.android.starter.utils

import com.google.gson.Gson
import devcode.android.starter.model.ErrorModel
import retrofit2.HttpException
import java.util.regex.Pattern

fun Throwable.getApiError(): ErrorModel? {
    if (this is HttpException) {
        return try {
            Gson().fromJson(this.response()?.errorBody()?.charStream(), ErrorModel::class.java)
        } catch (exception: Exception) {
            null
        }
    }

    return null
}