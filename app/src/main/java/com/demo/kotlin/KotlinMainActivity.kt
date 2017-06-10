package com.demo.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.alex.dagger.R
import kotlinx.android.synthetic.main.activity_kotlin_main.*


/**
 * Kotlin第一个Activity
 */
class KotlinMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_main)
        tv_hello.text = "Hello Kotlin!"
        //Lambda表达式写法
//        btn_toast.setOnClickListener {
//            v: View -> Toast.makeText(this,"Hello Kotlin Lambda!",Toast.LENGTH_SHORT).show()
//        }
        //函数扩展
        btn_toast.setOnClickListener { toast("Hello Kotlin ext",Toast.LENGTH_LONG) }

        btn_jump.setOnClickListener {
            val user = User("Alex")
            user.id = "100"
            KotlinSencondActivity.startActivity(this,user)
        }
    }
}
