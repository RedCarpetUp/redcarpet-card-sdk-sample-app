package com.example.redcptsdksample

import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import com.redcpt.sdk.RedCpt
import com.redcpt.sdk.otp.Otp
import com.redcpt.sdk.otp.models.SignUpResponse
import com.redcpt.sdk.otp.models.VerifyResponse
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var sdk: RedCpt
    private lateinit var otp: Otp
    private var signupRetryCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sdk = (application as SampleApplication).sdk
        otp = sdk.getOtpInstance()

        requestPhoneNumberState()

        btSignup.setOnClickListener {
            val phoneNumber = etPhone.text.toString()
            waitingState()
            isOtpVerified()
            otp.signup(phoneNumber, ++signupRetryCount, object : Otp.SignupCallback {

                override fun onError(response: SignUpResponse?, error: Throwable?) {
                    when {
                        error != null -> phoneNumberErrorState(error.message)
                        response != null -> phoneNumberErrorState(response.message)
                    }
                }

                override fun onSuccess(response: SignUpResponse) {
                    requestOtpState()
                }

            })
        }

        btVerify.setOnClickListener {
            val phoneNumber = etPhone.text.toString()
            val password = etOtp.text.toString()
            waitingState()

            otp.verify(phoneNumber, password, object : Otp.VerifyCallback {
                override fun onError(response: VerifyResponse?, error: Throwable?) {
                    when {
                        error != null -> otpErrorState(error.message)
                        response != null -> otpErrorState(response.message)
                    }
                }

                override fun onSuccess(verifyResponse: VerifyResponse) {
                    loginSuccessfulState()
                    loginRoot.postDelayed(1000) {
                        finish()
                    }
                }
            })
        }

    }


    private fun isOtpVerified(): Boolean {
        Log.e("isOtpVerified", sdk.isOtpVerified().toString())
        return sdk.isOtpVerified()
    }

    private fun waitingState() {
        pbProgress.visibility = View.VISIBLE
        etPhone.isEnabled = false
        etOtp.isEnabled = false
        btSignup.isEnabled = false
        btVerify.isEnabled = false
    }

    private fun requestPhoneNumberState() {
        TransitionManager.beginDelayedTransition(loginRoot)
        pbProgress.visibility = View.GONE
        etOtp.visibility = View.GONE
        btVerify.visibility = View.GONE
        etPhone.visibility = View.VISIBLE
        btSignup.visibility = View.VISIBLE
        etPhone.isEnabled = true
        etOtp.isEnabled = false
        btSignup.isEnabled = true
        btVerify.isEnabled = false
    }

    private fun requestOtpState() {
        TransitionManager.beginDelayedTransition(loginRoot)
        pbProgress.visibility = View.GONE
        etOtp.visibility = View.VISIBLE
        btVerify.visibility = View.VISIBLE
        btSignup.visibility = View.GONE
        etPhone.visibility = View.GONE
        etPhone.isEnabled = false
        etOtp.isEnabled = true
        btSignup.isEnabled = false
        btVerify.isEnabled = true
    }

    private fun phoneNumberErrorState(error: String? = null) {
        pbProgress.visibility = View.GONE
        etPhone.visibility = View.VISIBLE
        btSignup.visibility = View.VISIBLE
        etOtp.visibility = View.GONE
        btVerify.visibility = View.GONE
        etPhone.isEnabled = true
        etOtp.isEnabled = false
        btSignup.isEnabled = true
        btVerify.isEnabled = false

        error?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
    }

    private fun otpErrorState(error: String? = null) {
        pbProgress.visibility = View.GONE
        etOtp.visibility = View.VISIBLE
        btVerify.visibility = View.VISIBLE
        etPhone.visibility = View.GONE
        btSignup.visibility = View.GONE
        etPhone.isEnabled = false
        etOtp.isEnabled = true
        btSignup.isEnabled = false
        btVerify.isEnabled = true

        error?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
    }

    private fun loginSuccessfulState() {
        pbProgress.visibility = View.GONE
        etPhone.isEnabled = false
        etOtp.isEnabled = false
        btSignup.isEnabled = false
        btVerify.isEnabled = false
        Toast.makeText(this, "Signup and Verification successful", Toast.LENGTH_LONG).show()
    }

}