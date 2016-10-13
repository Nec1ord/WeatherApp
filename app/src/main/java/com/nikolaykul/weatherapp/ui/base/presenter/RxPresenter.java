package com.nikolaykul.weatherapp.ui.base.presenter;

import android.support.annotation.CallSuper;

import com.nikolaykul.weatherapp.ui.base.view.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RxPresenter<T extends MvpView> extends Presenter<T> {
    private CompositeSubscription mSubscriptions;

    @CallSuper
    @Override
    public void onResume() {
    }

    @CallSuper
    @Override
    public void onDestroy() {
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
