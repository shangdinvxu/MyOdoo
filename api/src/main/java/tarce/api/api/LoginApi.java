package tarce.api.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import tarce.model.GetMenuListResponse;
import tarce.model.LoginDatabase;
import tarce.model.LoginResponse;
import tarce.model.LoginBean;

/**
 * Created by Daniel.Xu on 2017/1/5.
 */

public interface LoginApi {
    @GET("get_db_list")
    Observable<LoginDatabase> getDatabase();

    @POST("login")
    Call<LoginResponse> toLogin(@Body LoginBean bean);

    @POST("login")
    Call<String> toLoginstring(@Body LoginBean bean);




    @GET("get_menu_list")
    Call<GetMenuListResponse> getMenuList();

/*    @Headers({
            "Content-Type: application/json; charset=UTF-8",
    })*/


}
