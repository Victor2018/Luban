package com.cherry.luban

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cherry.lib.luban.Luban
import com.cherry.lib.luban.ext.MemoryUnit
import com.cherry.luban.ui.theme.LubanTheme

class MainActivity : ComponentActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LubanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                    ) {
                        Button(
                            modifier = Modifier.padding(bottom = 10.dp),
                            border = BorderStroke(width = 1.dp, color = Color.Blue),
                            onClick = {
                                //点击回调
                                var intent = Intent(Intent.ACTION_PICK, null)
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                                startActivityForResult(intent, 2)
                            }
                        ) {
                            //单单一个Button是没有内容的，这里需要在Button里添加一个Text
                            Text(text = "打开图片")
                        }
                    }

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                var uri = data.data
                Toast.makeText(this, "path = ${uri?.path}", Toast.LENGTH_SHORT).show()
                compressImage(uri)
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
    //                                mIv!!.setImageURI(Uri.fromFile(it))
                    }
                    onError = { a, _ ->
                        Log.e(TAG, a.toString())
                    }
                }.launch()
        }
    }
}
