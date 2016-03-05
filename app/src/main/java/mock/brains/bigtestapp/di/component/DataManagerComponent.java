package mock.brains.bigtestapp.di.component;

import dagger.Component;
import mock.brains.bigtestapp.di.module.DataManagerModule;
import mock.brains.bigtestapp.ui.activity.MainActivity;
import mock.brains.bigtestapp.ui.activity.SecondActivity;
import mock.brains.bigtestapp.util.annotation.Cycles;
import mock.brains.bigtestapp.util.annotation.Lifecycle;

@Lifecycle(Cycles.ACTIVITY)
@Component(modules = {DataManagerModule.class}, dependencies = AppComponent.class)
public interface DataManagerComponent {
    void inject(MainActivity activity);
    void inject(SecondActivity activity);
}
