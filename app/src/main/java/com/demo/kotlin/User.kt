package com.demo.kotlin

import java.io.Serializable

/**
 * Created by Administrator on 2017/5/18 0018.
 */
class User : Serializable{

    var name: String? = null

    var id: String? = null

    constructor(name: String){
        this.name = name
    }

    constructor(name: String, id: String){
        this.name = name
        this.id = id
    }

}