package com.example.redcptsdksample

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.redcpt.appwisesdk.AppWise
import com.redcpt.onboardingsdk.VerificationCallback
import com.redcpt.onboardingsdk.models.pincode.PincodeDataResponse
import com.redcpt.onboardingsdk.models.userStatus.UserStatusResponse
import com.redcpt.sdk.RedCpt
import com.redcpt.sdk.otp.Otp
import com.redcpt.sdkservices.docupload.FileUploadParams
import com.redcpt.sdkservices.docupload.uploadservice.DocUploadCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var otp: Otp
    private lateinit var sdk: RedCpt
    private lateinit var verificationCallback: VerificationCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sdk = (application as SampleApplication).sdk
        verificationCallback = sdk.getVerficationInstance()
        sdk.setDevMode(true)

        btLogin.setOnClickListener {
            logData()
            Log.e("IS OTP VERIFIED", sdk.isOtpVerified().toString())
            if (sdk.isOtpVerified()) {
                getVerificationStatus()
                AppWise.getInstance(applicationContext).uploadData()
                uploadData()
                pincodeVerify()
            }
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btLoginRx.setOnClickListener {
            /*  val intent = Intent(this, RxLoginActivity::class.java)
              startActivity(intent)*/
        }

        btExpenseDash.setOnClickListener {
            sdk.getExpenseManagerInstance().showExpenseDash(this)
        }

        btAddExpense.setOnClickListener {
            sdk.getExpenseManagerInstance().showAddExpense(this)
        }

        btPermissions.setOnClickListener {
            sdk.getPermissionsInstance().startActivity(this) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_LONG).show()
            }
        }
    }


    fun getVerificationStatus() {
        verificationCallback.getCurrentStatus(object :
            VerificationCallback.StatusCallback<UserStatusResponse> {
            override fun onFail(t: UserStatusResponse?, throwable: Throwable?) {
                Log.e("error response ", t.toString())
                Log.e("error response ", throwable.toString())
            }

            override fun onSuccess(t: UserStatusResponse, s: String, c: String) {
                if (t.result.equals("success")) {
                    Log.e("Funnel", t.data?.funnel_status!!)
                    Log.e("isUser Approved", t.data!!.onboarding_status!!)
                }
            }

        })
    }


    fun pincodeVerify() {
        verificationCallback.pincodeVerify("110011", object :
            VerificationCallback.Callback<PincodeDataResponse> {
            override fun onFail(t: PincodeDataResponse?, throwable: Throwable?) {
                Log.e("error response ", t.toString())
                Log.e("error response ", throwable.toString())
            }

            override fun onSuccess(t: PincodeDataResponse) {

                if (t.result.equals("success")) {
                    Log.e("Funnel", t.data.toString())
                }
            }

        })
    }


    fun logData() {
        Log.e("Address Type", sdk.utils.getAddressType().toString())
        Log.e("OccupationList ", sdk.utils.getOccupationList().toString())
        Log.e("OccupationList ", sdk.utils.doctype.AADHAAR_CARD_FRONT)
    }

    fun uploadDoc() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent, "klj"
            ), 1234
        )
    }


    fun uploadData() {
        verificationCallback.savePersonalDetails(
            "Balram Pandey",
            "balram@redcarpetup.com",
            "28-06-1992",
            "male",
            "",
            object :
                VerificationCallback.Callback<com.redcpt.onboardingsdk.models.GenericResponseModel> {
                override fun onFail(
                    t: com.redcpt.onboardingsdk.models.GenericResponseModel?,
                    throwable: Throwable?
                ) {
                    Log.e("error response ", t.toString())
                    Log.e("error response ", throwable.toString())
                }

                override fun onSuccess(t: com.redcpt.onboardingsdk.models.GenericResponseModel) {
                    if (t.result.equals("success")) {
                        Log.e("Funnel", t.message.toString())
                    }
                }


            })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1234) {
            var filePath: String = ""
            if (data == null) {
                //handle null data
            } else {
                val uri = data.data
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && uri != null) {
//                    filePath = getPathFromUri(this, uri)!!
                }
            }
            if (filePath.isEmpty()) {
                //hande empty path
                return
            }
            val dochelper = sdk.getDocUploadHelper()
            dochelper?.requestUpload(
                FileUploadParams(
                    arrayOf(filePath),
                    hashMapOf(Pair("type", sdk.utils.doctype.AADHAAR_CARD_FRONT))
                ), object : DocUploadCallback.Callback {
                    override fun onFail(throwable: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onSuccess() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
        }
    }
}
