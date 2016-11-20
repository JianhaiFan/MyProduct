package com.xiaofan.product.activity.test;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaofan.product.R;
import com.xiaofan.product.activity.AbstractBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 范建海
 * @createTime: 2016/11/8 20:31
 * @className:  RecycleViewActivity
 * @description: 测试RecycleView的界面
 * @changed by:
 */
public class RecycleViewActivity extends AbstractBaseActivity {
    // recycleView控件
    private RecyclerView mRecyclerView;
    // List集合
    private List<String> mDatas;
    //  上下文
    private AbstractBaseActivity mActivity;

    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test_recycle_view);

        mActivity = RecycleViewActivity.this;

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        mAdapter = new HomeAdapter();

        mRecyclerView.setAdapter(mAdapter);
    }


    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    mActivity).inflate(R.layout.item_recycle_view, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }


}
