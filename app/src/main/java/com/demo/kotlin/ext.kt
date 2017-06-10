package com.demo.kotlin

import android.content.Context
import android.widget.Toast

/**
 * Created by Administrator on 2017/5/18 0018.
 */

fun Context.toast(message: String, length : Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message,length).show();
}