package com.nikolaykul.weatherapp.ui.base;

import android.support.annotation.CallSuper;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class RxPresenter<T extends MvpView> extends Presenter<T> {
    private CompositeSubscription mSubscriptions;

    @CallSuper
    @Override
    protected void onDestroy() {
        clearSubscriptions();
    }

    protected final void addSubscription(Subscription sub) {
        if (null == mSubscriptions || mSubscriptions.isUnsubscribed()) {
            mSubscriptions = new CompositeSubscription();
        }
        mSubscriptions.add(sub);
    }

    protected final void clearSubscriptions() {
        if (null != mSubscriptions && !mSubscriptions.isUnsubscribed()) {
            mSubscriptions.unsubscribe();
        }
    }

}
