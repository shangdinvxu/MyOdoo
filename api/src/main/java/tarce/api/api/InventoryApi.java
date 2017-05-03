package tarce.api.api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import tarce.model.FindProductByConditionResponse;
import tarce.model.GetGroupByListresponse;
import tarce.model.GetSaleListResponse;
import tarce.model.GetSaleResponse;
import tarce.model.LoadActionBean;
import tarce.model.SearchSupplierResponse;
import tarce.model.greendaoBean.LoadResponse;

/**
 * Created by Daniel.Xu on 2017/2/17.
 */

public interface InventoryApi {
    @POST("get_group_by_list")
    Call<GetGroupByListresponse> getGroupsByList(@Body HashMap hashMap);



    @POST("get_incoming_outgoing_stock_picking")
    Call<GetSaleListResponse> getInComingOutgoingList(@Body HashMap hashMap);

    /**
     * 搜索供应商或者客户。
     * @param hashMap
     * @return
     */
    @POST("search_supplier")
    Call<SearchSupplierResponse> searchSupplier(@Body HashMap hashMap);

    @POST("get_stock_picking_by_origin")
    Call<GetSaleListResponse> searchBySalesNumber(@Body HashMap hashMap);

    /**
     * 检查可用性
     * @param hashMap
     * @return
     */
    @POST("action_assign_stock_picking")
    Call<GetSaleResponse> checkIsCanUse(@Body HashMap hashMap );

    /**
     * 取消保留
     * @param hashMap
     * @return
     */
    @POST("do_unreserve_action")
    Call<GetSaleResponse> doUnreserveAction(@Body HashMap hashMap );

    @POST("find_product_by_condition")
    Call<FindProductByConditionResponse> findProductByCondition(@Body HashMap findProductByConditionRequest);


    @POST("change_stock_picking_state")
    Call<GetSaleResponse> changeStockPicking(@Body HashMap hashMap );


    @POST("load_needaction")
    Observable<Object> load_action(@Body HashMap hashMap);

    @POST("load_needaction")
    Call<LoadActionBean> load_actionCall(@Body HashMap hashMap);


}
