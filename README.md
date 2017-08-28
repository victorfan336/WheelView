# WheelView
当初写这个控件基于三个原因：
* 想找个控件来练练手，再次熟悉熟悉自定义view
* 最近开始流行学习kotlin语言了，我也已经学习了一段时间，想练练手
* 最近发现公司的滚轮控件出现了一个bug


## 特点
* 使用Kotlin语言编写
* 完全使用自定义的view编写完成，我看过有些人不是完全基于view写的
* 实现了三种滚轮模式：  
	    * 循环模式:  
        * 居中显示模式；    
        * 从头开始显示
* 自己处理了滚动事件和快速滑动事件
* 处理了边界检测和弹性效果
* 通过adapter快速添加数据


## 效果图
![github](https://github.com/victorfan336/WheelView/blob/master/wheelview.gif)  


### 由于kotlin语言写的库上传不方便，所以该kotlin库只用于学习kotlin语言，和了解自定义view。
    一下内入均是以Java版本为准的，Import的地址是java版本的,不影响使用。

## 使用

* Import(java版本的)   
    compile 'com.victor.library:wheelview:1.0.8@aar'  
    version1.0.8:      

        1.新增itemHeight属性配置；      
    	   
    	2.解决UI拖出可见范围后，有时回弹不准的问题，是由于没有做四舍五入的问题导致的；    
    	   
    	3.拓展滚动监听方法，回传wheelview；     
    	
    	4.新增设置当前选择位置和获取当前选择位置方法：     
    	
    		public void setCurrItem(int index)；    
    		  
    		public int getSelectedItem()；   
    		
    java版本已在公司APP中使用！ 


* 定义了三个可配置属性：
	``` java
    <attr name="textColor" format="color"/>
    <attr name="textSize" format="dimension" />
    <attr name="dragOut" format="boolean" />
    <attr name="itemheight" format="dimension" />
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
* 自定义Adapter
	只需要实现IWheelviewAdapter即可     
	``` java    
	public interface IWheelviewAdapter<T> {
    	String getItemeTitle(int i);       
    	int getCount();              
    	T get(int index);            
    	void clear();             
	}	
	```  
	只要实现以上接口方法即可，下面示范一个自定义的Adapter:
	```java 

	public class WheelviewAdapter implements IWheelviewAdapter {

	    private List<String> mList;

	    public WheelviewAdapter(List<String> list) {
	        mList = list;
	    }

	    @Override
	    public String getItemeTitle(int i) {
	        if (mList != null) {
	            return mList.get(i);
	        } else {
	            return "";
	        }
	    }

	    @Override
	    public int getCount() {
	        if (mList != null) {
	            return mList.size();
	        } else {
	            return 0;
	        }

	    }

	    @Override
	    public String get(int index) {
	        if (mList != null && index >= 0 && index < mList.size()) {
	            return mList.get(index);
	        } else {
	            return null;
	        }
	    }

	    @Override
	    public void clear() {
	        if (mList != null) {
	            mList.clear();
	        }
	    }
	}

	```  
* 在代码中配置：
	``` java
	private String[] provides = {"天津市", "北京市", "黑龙江省", "江苏省", "浙江省", "安徽省",
            "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省"};     
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> provider = new ArrayList<>();
        for (String name : provides) {
            provider.add(name);
        }
        wheelView1 = (WheelView) findViewById(R.id.wheelview1);
        IWheelviewAdapter providerAdapter = new WheelviewAdapter(provider);
        wheelView1.setAdapter(providerAdapter);    
        // 设置滚动选择监听
        wheelView1.setWheelScrollListener(new WheelView.WheelScrollListener() {

            @Override
            public void onChanged(WheelView wheelView, int selected, Object bean) {
                Toast.makeText(DemoActivity.this, bean + "被选中了第" + selected, Toast.LENGTH_SHORT).show();
            }

        });      

	}       
	```   
	设置对齐模式：默认是WheelViewCenterMode居中显示     
	```java   
		wheelView1.setMode(WheelView.getStartModeInstance(wheelView1));           
		wheelView1.setMode(WheelView.getCenterModeInstance(wheelView1));     
		wheelView1.setMode(WheelView.getRecycleModeInstance(wheelView1));       
	```

    ## 详细说明     
        可以[下载demo APK](https://github.com/victorfan336/WheelView/blob/master/app-demo.apk)试试      
            
        请参考博客：[文韬_武略](http://blog.csdn.net/fwt336/article/details/76086360)    
          

    欢迎star或fork
