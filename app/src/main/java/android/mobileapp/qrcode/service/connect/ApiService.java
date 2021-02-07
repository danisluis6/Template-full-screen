package android.mobileapp.qrcode.service.connect;

import android.mobileapp.qrcode.service.response.content.ContentResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public interface ApiService {

    @GET("/v1/millionaire/mobile-app/configure")
    Observable<Response<ContentResponse>> getAllContents();

}
