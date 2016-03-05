package mock.brains.bigtestapp.ui.activity;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import butterknife.ButterKnife;
import mock.brains.bigtestapp.R;
import mock.brains.bigtestapp.api.ApiManager;
import mock.brains.bigtestapp.briteDb.DbManager;
import mock.brains.bigtestapp.di.component.DaggerDataManagerComponent;
import mock.brains.bigtestapp.di.component.DataManagerComponent;
import mock.brains.bigtestapp.di.module.DataManagerModule;

public class MainActivity extends BaseActivity {

    @Inject
    ApiManager apiManager;

    @Inject
    DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.e("TAG", "onCreate: ");
    }

    @Override
    protected void initDiComponent() {
        DataManagerComponent dataManagerComponent = DaggerDataManagerComponent.builder()
                .dataManagerModule(new DataManagerModule())
                .appComponent(getAppComponent())
                .build();
        dataManagerComponent.inject(this);
    }
}

