package com.cherry.luban

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import coil.load
import com.cherry.lib.luban.Luban
import com.cherry.lib.luban.ext.MemoryUnit
import com.cherry.luban.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val SELECT_IMAGE_CODE = 66
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            //点击回调
            var intent = Intent(Intent.ACTION_PICK, null)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, SELECT_IMAGE_CODE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SELECT_IMAGE_CODE -> {
                    var uri = data?.data
                    compressImage(uri)
                }
            }
        }
    }

    fun compressImage(uri: Uri?) {
        if (uri != null) {
            Luban.with(this)
                .load(uri)
                .ignoreBy(10)
                .maxSize(100, MemoryUnit.KB)
                .concurrent(true)
                .rename {
                    Log.d(TAG, "rename $it")
                    "$it.jpg"
                }
                .compressObserver {
                    onStart = {
                    }
                    onCompletion = {
                        Log.d(TAG, "onCompletion")
                    }
                    onSuccess = {
                        Log.e(TAG, "onSuccess-file = ${it.absolutePath}")
                        Toast.makeText(this@MainActivity, "file = ${it.absolutePath}", Toast.LENGTH_LONG).show()
                        binding.mIvImage.load(it)
                    }
                    onError = { a, _ ->
                        Log.e(TAG, a.toString())
                    }
                }.launch()
        }
    }

}