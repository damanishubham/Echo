package com.example.echo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.widget.Toast

class SplashActivity : AppCompatActivity() {
    var permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.MODIFY_AUDIO_SETTINGS,
    Manifest.permission.READ_PHONE_STATE,
    Manifest.permission.KILL_BACKGROUND_PROCESSES,
    Manifest.permission.RECORD_AUDIO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        supportActionBar?.hide()
        if(checkPermissions(this,*permission))
        {
            runSplash(this)
        }
        else
        {
            ActivityCompat.requestPermissions(this,permission,131)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            131 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED
                    && grantResults[4] == PackageManager.PERMISSION_GRANTED
                ) {
                    runSplash(this@SplashActivity)
                    return
                } else {
                    Toast.makeText(this, "Grant All permissions to continue", Toast.LENGTH_SHORT)
                        .show()
                    this.finish()
                }
            }
            else -> {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                this.finish()
            }

        }
    }

    fun checkPermissions(context: Context, vararg permission:String): Boolean
    {
        var flag= true
        for(i in permission)
        {
            if(context.checkCallingOrSelfPermission(i)!= PackageManager.PERMISSION_GRANTED)
            {
                flag=false
            }
        }
        return flag
    }
    fun runSplash(context: Context)
    {
        Handler().postDelayed({
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },500)
    }
}