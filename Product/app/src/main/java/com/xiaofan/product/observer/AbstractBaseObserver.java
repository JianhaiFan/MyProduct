package com.xiaofan.product.observer;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

/**
 * Created by fanjianhai on 2016/11/14.
 */
public class AbstractBaseObserver extends ContentObserver {

    public AbstractBaseObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
    }


}
