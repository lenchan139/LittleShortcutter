package org.lenchan139.littleshortcutter

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.media.AudioManager
import android.content.Intent
import android.os.Build
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat


class MainActivity : Activity() {
    var REQUEST_CODE_NOTIFICAOIN_POLICY = 12
    private lateinit var dialog : AlertDialog.Builder
    private var arrDialogItem = arrayOf("\u2328   ️Switch IME","\uD83D\uDD15  Silent Mode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main);

        dialog = AlertDialog.Builder(this)
        dialog.setItems(arrDialogItem, DialogInterface.OnClickListener { dialogInterface, i ->
           if (i==0){
               onSwitchIme()
           }else if(i==1){
               onSilentMode()
           }
        })
        dialog.setOnDismissListener {

            finish()

        }
        if(permissionGrant(this)){
        dialog.show()
        }else{
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
    fun permissionGrant(activity: Activity) : Boolean{
        val notificationManager = baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted) {

            val intent = Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)

            startActivity(intent)
            Toast.makeText(activity,"Please grant to Little Shortcutter!",Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }

    }
    fun onSwitchIme(){
        val imeManager = baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imeManager != null) {
            imeManager!!.showInputMethodPicker()
        } else {
            Toast.makeText(baseContext, "Error", Toast.LENGTH_LONG).show()
        }
    }

    fun onSilentMode(){
        val am: AudioManager
        am = baseContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if(am.ringerMode == AudioManager.RINGER_MODE_NORMAL) {
            am.ringerMode = AudioManager.RINGER_MODE_SILENT
        }else if(am.ringerMode == AudioManager.RINGER_MODE_SILENT){
            am.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }else{
            am.ringerMode = AudioManager.RINGER_MODE_NORMAL
        }

    }
}
