package com.demo.kotlin

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.example.alex.dagger.R

/**
 * 跳转后的页面
 */
class KotlinSencondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_sencond)
        var user : User = intent.getSerializableExtra(SECOND_KEY) as User
        toast(user.name+", " + user.id)
    }

    /**
     * 创建伴生对象
     */
    companion object{
        //extral key
        val SECOND_KEY = "SECOND_ACTIVITY";
        fun startActivity(context: Context, user: User){
            val intent = Intent(context, KotlinSencondActivity::class.java)
            intent.putExtra(SECOND_KEY, user)
            context.startActivity(intent)
        }
    }
}
