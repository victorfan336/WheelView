package com.victor.library

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.victor.library.wheelview.WheelView
import com.victor.library.wheelview.WheelviewAdapter
import com.victor.library.wheelview.mode.WheelViewCenterMode
import com.victor.library.wheelview.mode.WheelViewRecycleMode
import com.victor.library.wheelview.mode.WheelViewStartMode
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var wheelView1: WheelView<String>
    lateinit var wheelView2: WheelView<String>
    lateinit var wheelView3: WheelView<MarketAdapter.MarketBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wheelView1 = find(R.id.wheelview1)
        var provider: ArrayList<String> = ArrayList()
        provider.addAll(listOf("天津市", "北京市", "黑龙江省", "江苏省", "浙江省", "安徽省",
                "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省"))
        val providerAdapter = WheelviewAdapter(provider)
        wheelView1?.setAdapter(providerAdapter)
        var city: ArrayList<String> = ArrayList()
        city.addAll(listOf("广州", "深圳", "东莞", "惠州", "中山", "佛山", "韶关", "珠海", "梅州",
                "汕头", "汕尾", "江门", "肇庆", "潮州", "揭阳", "湛江", "茂名", "阳江", "清远", "云浮", "河源"))
        wheelView2 = find(R.id.wheelview2)
        val cityAdapter = WheelviewAdapter(city)
        wheelView2?.setAdapter(cityAdapter)
//        var dist: ArrayList<String> = ArrayList()
//        dist.addAll(listOf("越秀区", "荔湾区", "海珠区", "天河区", "白云区", "黄埔区", "花都区", "番禺区", "南沙区", "增城区", "从化区"))
        val marketBeanList = java.util.ArrayList<MarketAdapter.MarketBean>()
        marketBeanList.add(MarketAdapter.MarketBean("桃子"))
        marketBeanList.add(MarketAdapter.MarketBean("李子"))
        marketBeanList.add(MarketAdapter.MarketBean("梅子"))
        marketBeanList.add(MarketAdapter.MarketBean("西瓜"))
        marketBeanList.add(MarketAdapter.MarketBean("葡萄"))
        marketBeanList.add(MarketAdapter.MarketBean("桔子"))
        marketBeanList.add(MarketAdapter.MarketBean("橙子"))
        marketBeanList.add(MarketAdapter.MarketBean("梨子"))
        marketBeanList.add(MarketAdapter.MarketBean("苹果"))
        wheelView3 = find(R.id.wheelview3)
        var marketAdapter = MarketAdapter(marketBeanList)
        wheelView3?.setAdapter(marketAdapter)
        wheelView1?.setWheelScrollListener(object : WheelView.WheelScrollListener {
            override fun changed(selected: Int, name: Any?) {
                toast("$name:被选中了第" + selected)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                }
            }
        })
        wheelView2?.setWheelScrollListener(object : WheelView.WheelScrollListener {
            override fun changed(selected: Int, name:Any?) {
                toast("$name:被选中了第" + selected)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                }
            }
        })
        wheelView3?.setWheelScrollListener(object : WheelView.WheelScrollListener {
            override fun changed(selected: Int, name:Any?) {
                var bean: MarketAdapter.MarketBean = name as MarketAdapter.MarketBean
                toast("${bean.name}:被选中了第" + selected)
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
                wheelView1.setMode(WheelViewRecycleMode(wheelView1.eachItemHeight, wheelView1.getContentSize()))
                wheelView2.setMode(WheelViewRecycleMode(wheelView2.eachItemHeight, wheelView2.getContentSize()))
                wheelView3.setMode(WheelViewRecycleMode(wheelView3.eachItemHeight, wheelView3.getContentSize()))
            }
            R.id.center_mode -> {
                wheelView1.setMode(WheelViewCenterMode(wheelView1.eachItemHeight, wheelView1.getContentSize()))
                wheelView2.setMode(WheelViewCenterMode(wheelView2.eachItemHeight, wheelView2.getContentSize()))
                wheelView3.setMode(WheelViewCenterMode(wheelView3.eachItemHeight, wheelView3.getContentSize()))
            }
            R.id.start_mode -> {
                wheelView1.setMode(WheelViewStartMode(wheelView1.eachItemHeight, wheelView1.getContentSize()))
                wheelView2.setMode(WheelViewStartMode(wheelView2.eachItemHeight, wheelView2.getContentSize()))
                wheelView3.setMode(WheelViewStartMode(wheelView3.eachItemHeight, wheelView3.getContentSize()))
            }
        }
    }

}
