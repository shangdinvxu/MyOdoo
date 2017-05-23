package tarce.api.api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import tarce.model.FindProductByConditionResponse;
import tarce.model.GetGroupByListresponse;
import tarce.model.GetProcessBean;
import tarce.model.GetSaleListResponse;
import tarce.model.GetSaleResponse;
import tarce.model.LoadActionBean;
import tarce.model.OutgoingStockpickingBean;
import tarce.model.SearchSupplierResponse;
import tarce.model.inventory.OrderDetailBean;
import tarce.model.inventory.PickingDetailBean;
import tarce.model.inventory.ProcessDeatilBean;
import tarce.model.inventory.SalesOutListResponse;
import tarce.model.inventory.GetNumProcess;

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
    Observable<LoadActionBean> load_action(@Body HashMap hashMap);

    @POST("load_needaction")
    Call<LoadActionBean> load_actionCall(@Body HashMap hashMap);


    @POST("get_outgoing_stock_picking")
    Observable<OutgoingStockpickingBean> getOutgoingStockpicking(@Body HashMap hashMap);

    @POST("get_outgoing_stock_picking_list")
    Observable<SalesOutListResponse> getOutgoingStockpickingList(@Body HashMap hashMap);

    /**
     *请求生产工序
     * @param hashMap
     * */
    @POST("get_process_list")
    Call<GetProcessBean> getProcess(@Body HashMap hashMap);

    /**
     * 生产工序相应延误数量
     * */
    @POST("get_order_count_by_process")
    Call<GetNumProcess> getNumProcess(@Body HashMap hashMap);

    /**
     * 某一个生产工序的详细信息
     * */
    @POST("get_date_uncomplete_orders")
    Call<ProcessDeatilBean> getDetailProcess(@Body HashMap hashMap);

    /**
     * 生产：领料详情
     * 可用6个重用
     * */
    @POST("get_mrp_production")
    Call<PickingDetailBean> getPicking(@Body HashMap hashMap);

    /**
     * 生产订单详情
     * */
    @POST("get_order_detail")
    Call<OrderDetailBean> getOrderDetail(@Body HashMap hashMap);

    /**
     * 生产入库和等待返工接口
     * */
    @POST("get_qc_feedback")
    Call<Object> getReworkRuku(@Body HashMap hashMap);
}
