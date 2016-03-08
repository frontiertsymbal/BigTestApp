package mock.brains.bigtestapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mock.brains.bigtestapp.BigTestApp;
import mock.brains.bigtestapp.di.component.AppComponent;
import mock.brains.bigtestapp.rx.RxBus;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDiComponent();
    }

    abstract protected void initDiComponent();

    protected AppComponent getAppComponent() {
        return ((BigTestApp) getApplication()).getAppComponent();
    }

    protected <T> RxBus getRxBus() {
        return getAppComponent().rxBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }
}
