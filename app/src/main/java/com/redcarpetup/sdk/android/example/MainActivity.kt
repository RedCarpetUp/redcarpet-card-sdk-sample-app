package com.redcarpetup.sdk.android.example

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.redcarpetup.sdk.android.RedCarpetUpSdk
import com.redcarpetup.sdk.android.example.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RedCarpetUpSdk.initialize(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            getCurrentUserStatus()
            checkCardEligibility()

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    fun getCurrentUserStatus() {
        GlobalScope.launch(Dispatchers.Main) {
            var map = mapOf<Any?, Any?>()
            val response = RedCarpetUpSdk.call("getUserStatus", map)
            println("Response: $response")
        }
    }

    fun pinCodeVerify() {
        GlobalScope.launch(Dispatchers.Main) {
            var map = mapOf<Any?, Any?>("pincode" to 110091)
            val response = RedCarpetUpSdk.call("verifyPincode", map)
            println("Response: $response")
        }
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


    fun checkCardEligibility() {

        GlobalScope.launch(Dispatchers.Main) {
            val map: MutableMap<Any?, Any?> = mutableMapOf()
            map["name"] = "name"
            map["email"] = "email"
            map["building"] = "building"
            map["addressType"] = "address_type"
            map["pincode"] = "pincode"
            map["gender"] = "gender"
            map["dateOfBirth"] = "dob"
            map["occupation"] = "occupation"
            map["locality"] = "locality"
            map["pan"] = "pan"
            val response = RedCarpetUpSdk.call("setInitialData", map)
            println("Response: $response")
        }

        fun checkEligibleCardsList() {
            GlobalScope.launch(Dispatchers.Main) {
                var map = mapOf<Any?, Any?>()
                val response = RedCarpetUpSdk.call("getAppCardDescription", map)
                println("Response: $response")
            }
        }


    }
}