package com.example.project_1081741;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Second extends AppCompatActivity {
    private int[] imageIds={
            R.drawable.a1,R.drawable.a2,R.drawable.a3,
            R.drawable.a4,R.drawable.a5,R.drawable.a6
    };
    private ImageView imgShow;
    private Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images);
        //取得介面元件
        imgShow=(ImageView)findViewById(R.id.imgShow);
        GridView gridView=(GridView)findViewById(R.id.GridView01);
        //建立自訂的Adapter
        MyAdapter adapter=new MyAdapter(this);
        //設定GridView的資料來源
        gridView.setAdapter(adapter);
        //建立GridView的ItemClick事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                imgShow.setImageResource(imageIds[position]);
            }
        });
        //取得介面元件
        button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(mylistener);
    }

    //當按下mylistener
    private  Button.OnClickListener mylistener = new Button.OnClickListener()
    {
        public  void onClick(View v)
        {
            finish();//結束應用程式
        }
    };
    //自訂的MyAdapter類別，繼承BaseAdapter類別
    class MyAdapter extends BaseAdapter {
        private Context mContext;
        public MyAdapter(Context c){
            mContext=c;
        }
        @Override
        public int getCount(){
            return imageIds.length;//圖片有多少張
        }
        @Override
        public Object getItem(int position){
            return position;
        }
        @Override
        public long getItemId(int position){
            return position;  //目前圖片索引
        }
        //定義顯示的圖片
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView iv=new ImageView(mContext);
            iv.setImageResource(imageIds[position]);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setLayoutParams(new ViewGroup.LayoutParams(280,210));//顯示圖片大小
            return iv;
        }
    }
}

