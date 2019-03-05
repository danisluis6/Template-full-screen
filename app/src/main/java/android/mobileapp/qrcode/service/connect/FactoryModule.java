package android.mobileapp.qrcode.service.connect;

import android.content.Context;
import android.mobileapp.qrcode.app.Application;
import android.mobileapp.qrcode.scan.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

@Module
public class FactoryModule {

    private Application mApplication;
    private Context mContext;

    public FactoryModule(Application application, Context context) {
        mApplication = application;
        mContext = context;
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
    }

    @Singleton
    @Provides
    TrustHtppS provideTrustHttpS(OkHttpClient.Builder client) {
        return new TrustHtppS(client);
    }

    @Singleton
    @Provides
    String provideBaseURL() {
        return BuildConfig.BASE_URL;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(TrustHtppS trustHtppS, OkHttpClient.Builder client, String baseUrl) {
        trustHtppS.intializeCertificate();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiService provideVogoApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
