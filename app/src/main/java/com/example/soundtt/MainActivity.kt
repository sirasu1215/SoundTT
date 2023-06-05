package com.example.soundtt

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.soundtt.ui.main.MainFragment
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        // todo 権限
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO
        )

        //許可したいpermissionを許可できるように
        if (!EasyPermissions.hasPermissions(this, *permissions)) {
            EasyPermissions.requestPermissions(this, "パーミッションに関する説明", 1, *permissions)
        }

    }
}