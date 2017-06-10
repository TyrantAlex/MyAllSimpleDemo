package com.example.alex.dagger.moudle;

import android.util.Log;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
public class TestShop {
    public String pen;

    public TestShop(String pen) {
        this.pen = pen;
    }

    public String write(){
        String writeContent = "开始写字...";
        Log.d("TestShop中的",writeContent);
        return writeContent;
    }

    public void printPenName(String penName){
        Log.d("TestShop.class","当前笔的名字为: " + penName);
    }

    public String getPen() {
        return pen;
    }

    public void setPen(String pen) {
        this.pen = pen;
    }
}
