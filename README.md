# WheelView
当初写这个控件基于三个原因：
* 想找个控件来练练手，再次熟悉熟悉自定义view
* 最近开始流行学习kotlin语言了，我也已经学习了一段时间，想练练手
* 最近发现公司的滚轮控件出现了一个bug


## 特点
* 使用最新语言Kotlin编写
* 完全使用自定义的view编写完成，我看过有些人不是完全基于view去些写的
* 实现了三种滚轮模式： 
	    *  1）循环模式； 
	    *  2）居中显示模式； 
	    *  3）从头开始显示
* 自己处理了滚动事件和快速滑动事件
* 处理了边界检测和弹性效果


## 效果图
![github](https://github.com/victorfan336/WheelView/blob/master/wheelview.gif)  

## 使用
	* 定义了三个可配置属性：
		```
		<attr name="textColor" format="color"/>
        <attr name="textSize" format="dimension" />
        <attr name="dragOut" format="boolean" />
        ```
    * 在xml中配置：
    ``` java
    	<com.victor.library.wheelview.WheelView
            android:id="@+id/wheelview"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1.0"
            android:focusable="true"
            android:gravity="center"
            app:dragOut="true"
            app:textColor="@color/black"
            app:textSize="12sp"
            />
    ```
    * 在代码中配置：
   	``` java
   		var dist: ArrayList<String> = ArrayList()
        dist.addAll(listOf("越秀区", "荔湾区", "海珠区", "天河区", "白云区", "黄埔区", "花都区", "番禺区", "南沙区", "增城区", "从化区"))
        wheelView = find(R.id.wheelview)
        wheelView?.setText(dist)
        // 设置滚动监听
        wheelView?.setWheelScrollListener(object : WheelView.WheelScrollListener {
            override fun changed(selected: Int, name: String) {
                toast("$name:被选中了第" + selected)
            }
        })
   	```
