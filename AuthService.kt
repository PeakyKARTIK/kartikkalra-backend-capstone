package your.package.name.services

import your.package.name.model.SecurityQuestionRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("users/verify-security-questions")
    fun verifySecurityQuestions(
        @Body request: SecurityQuestionRequest
    ): Call<ResponseBody>
}
