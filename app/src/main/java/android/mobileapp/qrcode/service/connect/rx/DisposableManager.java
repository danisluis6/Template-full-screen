package android.mobileapp.qrcode.service.connect.rx;


import android.mobileapp.qrcode.service.response.configure.ConfigureResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;

public class DisposableManager {

    private IDisposableListener listener;
    private Disposable disposable;

    @Inject
    public DisposableManager() {
    }

    public Disposable getConfigure(Observable<Response<ConfigureResponse>> observable, final IDisposableListener<ConfigureResponse> listener) {
        disposable = observable.subscribeWith(new DisposableObserver<Response<ConfigureResponse>>() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }

            @Override
            public void onNext(Response<ConfigureResponse> value) {
                if (value.isSuccessful()) {
                    listener.onHandleData(value.body());
                } else {
                    listener.onRequestWrongData(value.code());
                }
            }

            @Override
            public void onError(Throwable e) {
                listener.onApiFailure(e);
            }
        });
        return disposable;
    }
}