package mock.brains.bigtestapp.ui.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import mock.brains.bigtestapp.R;
import mock.brains.bigtestapp.model.User;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button)
    public void onButtonClick() {
        getRxBus().post(new User());
        getRxBus().post("Hello world");
    }

    @Override
    protected void initDiComponent() {

    }
}
