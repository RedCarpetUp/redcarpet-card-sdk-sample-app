package com.example.redcptsdksample

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    private var signupRetryCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        requestPhoneNumberState()





    }


    private fun isOtpVerified(): Boolean {
        return true
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