package your.package.name.view

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import your.package.name.R
import your.package.name.model.SecurityQuestion
import your.package.name.model.SecurityQuestionRequest
import your.package.name.services.AuthService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var answer1: EditText
    private lateinit var answer2: EditText
    private lateinit var answer3: EditText
    private lateinit var submitBtn: Button
    private lateinit var resultText: TextView

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://your-api.com/") // Replace with your real API URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val authService = retrofit.create(AuthService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        emailInput = findViewById(R.id.emailInput)
        answer1 = findViewById(R.id.answer1)
        answer2 = findViewById(R.id.answer2)
        answer3 = findViewById(R.id.answer3)
        submitBtn = findViewById(R.id.submitBtn)
        resultText = findViewById(R.id.resultText)

        submitBtn.setOnClickListener {
            val email = emailInput.text.toString()
            val questions = listOf(
                SecurityQuestion("What's your first pet name?", answer1.text.toString()),
                SecurityQuestion("What's your first school name?", answer2.text.toString()),
                SecurityQuestion("What's your cloth size?", answer3.text.toString())
            )

            val request = SecurityQuestionRequest(email, questions)

            authService.verifySecurityQuestions(request).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        resultText.text = "Verified. You can now reset your password."
                    } else {
                        resultText.text = "Verification failed. Try again."
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    resultText.text = "Error: ${t.message}"
                }
            })
        }
    }
}
