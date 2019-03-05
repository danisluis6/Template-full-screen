package android.mobileapp.qrcode.service.connect.rx;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public interface IDisposableListener<T> {
    void onComplete();

    void onHandleData(T t);

    void onRequestWrongData(int code);

    void onApiFailure(Throwable error);
}
