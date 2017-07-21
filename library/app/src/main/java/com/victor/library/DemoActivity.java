package com.victor.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.victor.library.wheelview.WheelView;
import com.victor.library.wheelview.mode.WheelViewCenterMode;
import com.victor.library.wheelview.mode.WheelViewRecycleMode;
import com.victor.library.wheelview.mode.WheelViewStartMode;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/7/20.
 */

public class DemoActivity extends AppCompatActivity {

    private String[] provides = {"天津市", "北京市", "黑龙江省", "江苏省", "浙江省", "安徽省",
            "福建省", "江西省", "山东省", "河南省", "湖北省", "湖南省", "广东省"};
    private String[] citys = {"广州", "深圳", "东莞", "惠州", "中山", "佛山", "韶关", "珠海", "梅州",
            "汕头", "汕尾", "江门", "肇庆", "潮州", "揭阳", "湛江", "茂名", "阳江", "清远", "云浮", "河源"};
    private String[] districts = {"越秀区", "荔湾区", "海珠区", "天河区", "白云区", "黄埔区", "花都区", "番禺区", "南沙区", "增城区", "从化区"};

    private WheelView wheelView1 = null;
    private WheelView wheelView2 = null;
    private WheelView wheelView3 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ArrayList<String> provider = new ArrayList<>();
        for (String name : provides) {
            provider.add(name);
        }
        wheelView1 = (WheelView) findViewById(R.id.wheelview1);
        wheelView1.setText(provider);

        ArrayList<String> city = new ArrayList<>();
        for (String name : citys) {
            city.add(name);
        }
        wheelView2 = (WheelView) findViewById(R.id.wheelview2);
        wheelView2.setText(city);

        ArrayList<String> district = new ArrayList<>();
        for (String name : districts) {
            district.add(name);
        }
        wheelView3 = (WheelView) findViewById(R.id.wheelview3);
        wheelView3.setText(district);

        wheelView1.setWheelScrollListener(new WheelView.WheelScrollListener() {

            @Override
            public void changed(int selected, String name) {
                Toast.makeText(DemoActivity.this, name + "被选中了第" + selected, Toast.LENGTH_SHORT).show();
            }
        });
        wheelView2.setWheelScrollListener(new WheelView.WheelScrollListener() {

            @Override
            public void changed(int selected, String name) {
                Toast.makeText(DemoActivity.this, name + "被选中了第" + selected, Toast.LENGTH_SHORT).show();
            }
        });
        wheelView3.setWheelScrollListener(new WheelView.WheelScrollListener() {

            @Override
            public void changed(int selected, String name) {
                Toast.makeText(DemoActivity.this, name + "被选中了第" + selected, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public void changeMode(View view) {
        WheelView wheelView = null;
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    wheelView = wheelView1;
                    break;
                case 1:
                    wheelView = wheelView2;
                    break;
                case 2:
                    wheelView = wheelView3;
                    break;
            }
            if (!(wheelView.getMode() instanceof WheelViewCenterMode)) {
                wheelView.setMode(WheelView.getCenterModeInstance(wheelView));
            } else if (!(wheelView.getMode() instanceof WheelViewStartMode)) {
                wheelView.setMode(WheelView.getStartModeInstance(wheelView));
            } else if (!(wheelView.getMode() instanceof WheelViewRecycleMode)) {
                wheelView.setMode(WheelView.getRecycleModeInstance(wheelView));
            }
        }
    }
}
