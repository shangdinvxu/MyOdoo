package tarce.api;


import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Daniel.Xu on 2016/12/15.
 */

public class RetrofitClient {
    private  static  Retrofit retrofit;
    private  static  Retrofit retrofitAno;
    public static String Url ;

    private RetrofitClient(Context context) {
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(context).getOkHttpClient())

                //baseUrl
//                .baseUrl("http://192.168.2.111:8069/linkloving_app_api/")
                .baseUrl(Url+"/linkloving_app_api/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static Retrofit getInstance(Context context) {
            new RetrofitClient(context);
        return retrofit;
    }

    public static Retrofit getInstanceAno(Context context, String another) {
        new RetrofitClient(context,another);
        return retrofitAno;
    }

    private RetrofitClient(Context context, String another) {
        retrofitAno = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(context).getOkHttpClient())

                //baseUrl
//                .baseUrl("http://192.168.2.111:8069/linkloving_app_api/")
                .baseUrl(Url+"/eb_order/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
