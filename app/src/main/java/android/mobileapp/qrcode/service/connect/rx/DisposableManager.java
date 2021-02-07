package android.mobileapp.qrcode.service.connect.rx;


import android.mobileapp.qrcode.service.response.content.ContentResponse;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DisposableManager {

    private IDisposableListener listener;
    private Disposable disposable;

    @Inject
    public DisposableManager() {
    }

    public Disposable contentAll(Observable<Response<ContentResponse>> observable) {
        disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<ContentResponse>>() {
                    @Override
                    public void onComplete() {
                        listener.onComplete();
                    }

                    @Override
                    public void onNext(Response<ContentResponse> value) {
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

    public void contentInterface(IDisposableListener<ContentResponse> _interface) {
        this.listener = _interface;
    }
}