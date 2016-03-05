package mock.brains.bigtestapp.ui.activity;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import butterknife.ButterKnife;
import mock.brains.bigtestapp.R;
import mock.brains.bigtestapp.api.ApiManager;
import mock.brains.bigtestapp.di.component.ApiManagerComponent;
import mock.brains.bigtestapp.di.component.DaggerApiManagerComponent;
import mock.brains.bigtestapp.di.module.ApiManagerModule;


public class MainActivity extends BaseActivity {

    @Inject
    ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.e("TAG", "onCreate: ");
    }

    @Override
    protected void initDiComponent() {
        ApiManagerComponent apiManagerComponent = DaggerApiManagerComponent.builder()
                .apiManagerModule(new ApiManagerModule())
                .appComponent(getAppComponent())
                .build();
        apiManagerComponent.inject(this);
    }
}

