package com.xiaofan.product.activity.other;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaofan.product.R;
import com.xiaofan.product.activity.AbstractBaseActivity;
import com.xiaofan.product.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author: 范建海
 * @createTime: 2016/11/22 20:36
 * @className:  OtherLanguageSwitchActivity
 * @description: 语言切换页面
 * @changed by:
 */
public class OtherLanguageSwitchActivity extends AbstractBaseActivity {

    private Context mContext;

    private TextView tv_title_bar_title;

    private ListView  lv_language;

    private List<String> mLanguages;

    private LanguageSwitchAdapter mLanguageSwitchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_language_switch);
        mContext = OtherLanguageSwitchActivity.this;

        tv_title_bar_title = (TextView) findViewById(R.id.tv_title_bar_title);
        tv_title_bar_title.setText(ResourceUtil.getString(mContext,R.string.more_language));
        lv_language = (ListView ) findViewById(R.id.lv_language);

        mLanguages = new ArrayList<String>();
        mLanguages.add(ResourceUtil.getString(mContext,R.string.auto));
        mLanguages.add(ResourceUtil.getString(mContext,R.string.chinese_simple));
        mLanguages.add(ResourceUtil.getString(mContext,R.string.english));

        mLanguageSwitchAdapter = new LanguageSwitchAdapter();
        lv_language.setAdapter(mLanguageSwitchAdapter);


        lv_language.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    @Override
    public boolean backCode() {
        return false;
    }


    public void onBack(View view) {
        finish();
    }



    public void onSave(View view) {
        ResourceUtil.switchLanguage(mContext,Locale.ENGLISH,false);
    }

    class LanguageSwitchAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mLanguages.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_other_switch_language,null);
            TextView tv_language = (TextView) convertView.findViewById(R.id.tv_language);
            tv_language.setText(mLanguages.get(i));


            View view = convertView.findViewById(R.id.view);
            // 动态设置底部边距
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            if (i == getCount() - 1) {
                params.leftMargin = 0;
                params.rightMargin = 0;
                view.setBackgroundColor(ResourceUtil.getColor(mContext,R.color.color_ebebeb));

            }else {
                params.leftMargin = (int) ResourceUtil.getDimension(mContext,R.dimen.yms_dimens_30_0_px);
                params.rightMargin = (int) ResourceUtil.getDimension(mContext,R.dimen.yms_dimens_30_0_px);
                view.setBackgroundColor(ResourceUtil.getColor(mContext,R.color.color_dadada));
            }
            view.setLayoutParams(params);

            return convertView;
        }
    }

}
