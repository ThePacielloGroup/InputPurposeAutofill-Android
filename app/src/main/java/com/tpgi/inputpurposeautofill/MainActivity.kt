package com.tpgi.inputpurposeautofill

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        checkPermission(android.Manifest.permission.POST_NOTIFICATIONS, 5)
        var autofillButton = findViewById<Button>(R.id.autofillButton)
        autofillButton.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE)
            intent.setData(Uri.parse("package:com.tpgi.inputpurposeautofill"))
            startActivityForResult(intent, 5)
        })
    }
    private fun checkPermission(permission: String, requestCode: Int) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }
}