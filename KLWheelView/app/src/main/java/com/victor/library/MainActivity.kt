package com.victor.library

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.victor.library.wheelview.WheelView
import com.victor.library.wheelview.WheelViewCenterMode
import com.victor.library.wheelview.WheelViewRecycleMode
import com.victor.library.wheelview.WheelViewStartMode
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var wheelView1: WheelView? = null
    var wheelView2: WheelView? = null
    var wheelView3: WheelView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wheelView1 = find(R.id.wheelview1)
        var provider: ArrayList<String> = ArrayList()
        provider.addAll(listOf("天津市", "北京市", "黑龙江省", "江苏省", "浙江省", "安徽省",
                "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省"))
        wheelView1?.setText(provider)
        var city: ArrayList<String> = ArrayList()
        city.addAll(listOf("广州", "深圳", "东莞", "惠州", "中山", "佛山", "韶关", "珠海", "梅州",
                "汕头", "汕尾", "江门", "肇庆", "潮州", "揭阳", "湛江", "茂名", "阳江", "清远", "云浮", "河源"))
        wheelView2 = find(R.id.wheelview2)
        wheelView2?.setText(city)
        var dist: ArrayList<String> = ArrayList()
        dist.addAll(listOf("越秀区", "荔湾区", "海珠区", "天河区", "白云区", "黄埔区", "花都区", "番禺区", "南沙区", "增城区", "从化区"))
        wheelView3 = find(R.id.wheelview3)
        wheelView3?.setText(dist)
        wheelView1?.setWheelScrollListener(object : WheelView.WheelScrollListener {
            override fun changed(selected: Int, name: String) {
                toast("$name:被选中了第" + selected)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                }
            }
        })
        wheelView2?.setWheelScrollListener(object : WheelView.WheelScrollListener {
            override fun changed(selected: Int, name: String) {
                toast("$name:被选中了第" + selected)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                }
            }
        })
        wheelView3?.setWheelScrollListener(object : WheelView.WheelScrollListener {
            override fun changed(selected: Int, name: String) {
                toast("$name:被选中了第" + selected)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                }
            }
        })

        findViewById(R.id.recycle_mode).setOnClickListener(this)
        findViewById(R.id.center_mode).setOnClickListener(this)
        findViewById(R.id.start_mode).setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when(view.id) {
            R.id.recycle_mode -> {
                wheelView1?.setMode(WheelView.getRecycleModeInstance(wheelView1!!))
                wheelView2?.setMode(WheelView.getRecycleModeInstance(wheelView2!!))
                wheelView3?.setMode(WheelView.getRecycleModeInstance(wheelView3!!))
            }
            R.id.center_mode -> {
                wheelView1?.setMode(WheelView.getCenterModeInstance(wheelView1!!))
                wheelView2?.setMode(WheelView.getCenterModeInstance(wheelView2!!))
                wheelView3?.setMode(WheelView.getCenterModeInstance(wheelView3!!))
            }
            R.id.start_mode -> {
                wheelView1?.setMode(WheelView.getStartModeInstance(wheelView1!!))
                wheelView2?.setMode(WheelView.getStartModeInstance(wheelView2!!))
                wheelView3?.setMode(WheelView.getStartModeInstance(wheelView3!!))
            }
        }
    }

}
