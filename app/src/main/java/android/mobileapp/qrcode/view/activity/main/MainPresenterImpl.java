package android.mobileapp.qrcode.view.activity.main;

public class MainPresenterImpl implements MainPresenter {

    private MainModel mMainModel;
    private MainView mMainView;

    public MainPresenterImpl(MainView mainView, MainModel mainModel) {
        mMainView = mainView;
        mMainModel = mainModel;
        mMainModel.attachPresenter(this);
    }
}
