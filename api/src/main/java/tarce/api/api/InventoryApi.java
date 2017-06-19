package tarce.api.api;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import tarce.model.AddworkBean;
import tarce.model.BuLlBean;
import tarce.model.ChangeStateBean;
import tarce.model.FindProductByConditionResponse;
import tarce.model.GetGroupByListresponse;
import tarce.model.GetProcessBean;
import tarce.model.GetSaleListResponse;
import tarce.model.GetSaleResponse;
import tarce.model.LoadActionBean;
import tarce.model.OutgoingStockpickingBean;
import tarce.model.SearchSupplierResponse;
import tarce.model.inventory.AreaMessageBean;
import tarce.model.inventory.AutoAddworkBean;
import tarce.model.inventory.BomFramworkBean;
import tarce.model.inventory.CheckOutProductBean;
import tarce.model.inventory.CommitNumFeedBean;
import tarce.model.inventory.CommonBean;
import tarce.model.inventory.DoUnreservBean;
import tarce.model.inventory.DoneCommitNumBean;
import tarce.model.inventory.FinishPrepareMaBean;
import tarce.model.inventory.FinishProductBean;
import tarce.model.inventory.FreeWorkBean;
import tarce.model.inventory.GetFactroyRemarkBean;
import tarce.model.inventory.LoadInspectionBean;
import tarce.model.inventory.LoadProductBean;
import tarce.model.inventory.MaterialDetailBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.model.inventory.PickingDetailBean;
import tarce.model.inventory.ProcessDeatilBean;
import tarce.model.inventory.ProductProcessBean;
import tarce.model.inventory.QcFeedbaskBean;
import tarce.model.inventory.RejectResultBean;
import tarce.model.inventory.RukuBean;
import tarce.model.inventory.SalesOutListResponse;
import tarce.model.inventory.GetNumProcess;
import tarce.model.inventory.StartInspectBean;
import tarce.model.inventory.StartProductBean;
import tarce.model.inventory.StartReworkBean;
import tarce.model.inventory.StopProductlineBean;
import tarce.model.inventory.UpdateMessageBean;
import tarce.model.inventory.WorkingWorkerBean;

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
    Call<DoUnreservBean> doUnreserveAction(@Body HashMap hashMap );

    @POST("find_product_by_condition")
    Call<FindProductByConditionResponse> findProductByCondition(@Body HashMap findProductByConditionRequest);


    @POST("change_stock_picking_state")
    Call<GetSaleResponse> changeStockPicking(@Body HashMap hashMap );


    /**
     * 检测红色圈圈数字
     * */
    @POST("load_needaction")
    Call<LoadProductBean> load_action(@Body HashMap hashMap);

    @POST("load_needaction")
    Call<LoadActionBean> load_actionCall(@Body HashMap hashMap);

    @POST("load_needaction")
    Call<LoadInspectionBean> load_actionInspec(@Body HashMap hashMap);


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

    /**
     * 生产备料-》延误-》详情
     * */
    @POST("get_recent_production_order")
    Call<MaterialDetailBean> getRecentOr(@Body HashMap hashMap);

    /**
     * 点击开始备料-》
     * */
    @POST("prepare_material_ing")
    Call<OrderDetailBean> checkPrepare(@Body HashMap hashMap);

    /**
     * 搜索位置信息
     * */
    @POST("get_area_list")
    Call<AreaMessageBean> getAreaMessage(@Body HashMap hashMap);

    /**
     * 提交物料信息
     * */
    @POST("upload_note_info")
    Call<UpdateMessageBean> commitMessage(@Body HashMap hashMap);

    /**
     * 核实 领料登记
     * */
    @POST("already_picking")
    Call<OrderDetailBean> checkPickRegister(@Body HashMap hashMap);

    /**
     * 待工员工接口
     * */
    @POST("find_free_workers")
    Call<FreeWorkBean> getFreeWorkers(@Body HashMap hashMap);

    /**
     * 工作中员工接口
     * */
    @POST("find_worker_lines")
    Call<WorkingWorkerBean> getWorking(@Body HashMap hashMap);

    /**
     * 扫描二维码自动添加员工
     * */
    @POST("add_worker")
    Call<AutoAddworkBean> addWorker(@Body HashMap hashMap);

    /**
     * 点击添加按钮添加员工
     * */
    @POST("add_worker")
    Call<AddworkBean> addWork_id(@Body HashMap hashMap);

    /**
     * 产出确认（符合条件）
     * */
    @POST("do_produce")
    Call<CheckOutProductBean> checkOut(@Body HashMap hashMap);

    /**
     *确认开始生产
     * */
    @POST("start_produce")
    Call<StartProductBean> startProduct(@Body HashMap hashMap);

    /**
     * 补领料确认
     * */
    @POST("over_picking")
    Call<BuLlBean> buLl(@Body HashMap hashMap);

    /**
     * 改变员工状态
     * */
    @POST("change_worker_state")
    Call<ChangeStateBean> changeState(@Body HashMap hashMap);

    /**
     * 产线的暂停与恢复
     * */
    @POST("change_worker_state")
    Call<StopProductlineBean> stopProductLine(@Body HashMap hashMap);

    /**
     * 生产完成
     * */
    @POST("produce_finish")
    Call<FinishProductBean> finishProduct(@Body HashMap hashMap);

    /**
     * 提交产品位置信息
     * */
    @POST("upload_procure_info")
    Call<UpdateMessageBean> uploadProductArea(@Body HashMap hashMap);

    /**
     * 提交备料
     * */
    @POST("finish_prepare_material")
    Call<FinishPrepareMaBean> finishPrepareMa(@Body HashMap hashMap);

    /**
     * 等待生产品检
     * */
    @POST("get_qc_feedback")
    Call<QcFeedbaskBean> getQcfb(@Body HashMap hashMap);

    /**
     * 开始品检
     * */
    @POST("start_quality_inspection")
    Call<StartInspectBean> startInspection(@Body HashMap hashMap);

    /**
     * 品检通过,品检不通过
     * */
    @POST("inspection_result")
    Call<RejectResultBean> resultInspec(@Body HashMap hashMap);

    /**
     * 入库
     * */
    @POST("produce_done")
    Call<RukuBean> produceDone(@Body HashMap hashMap);

    /**
     * 提交退料数量
     * */
    @POST("return_material")
    Call<CommitNumFeedBean> commitFeedNum(@Body HashMap hashMap);

    /**
     * 清点退料/提交退料数量
     * */
    @POST("semi_finished_return_material")
    Call<DoneCommitNumBean> semiCommitReturn(@Body HashMap hashMap);

    /**
     * 开始返工
     * */
    @POST("start_rework")
    Call<StartReworkBean> startRework(@Body HashMap hashMap);

    /**
     * 返工中
     * */
    @POST("get_rework_ing_production")
    Call<PickingDetailBean> reworkIng(@Body HashMap hashMap);

    /**
     * bom结构
     * */
    @POST("get_bom_detail")
    Call<BomFramworkBean> getBomDetail(@Body HashMap hashMap);

    /**
     * 等待生产
     * */
    @POST("get_already_picking_orders_count")
    Call<ProcessDeatilBean> getPickCount(@Body HashMap hashMap);

    @POST("get_recent_alredy_picking_order")
    Call<PickingDetailBean> getListWait(@Body HashMap hashMap);

    /**
     * 生产中
     * */
    @POST("get_progress_mo_group_by_process")
    Call<ProductProcessBean> processGroup(@Body HashMap hashMap);

    @POST("get_mrp_production")
    Call<Object> mrpProduction(@Body HashMap hashMap);

    /**
     * 出库信息查询
     * */
    @POST("get_outgoing_stock_picking")
    Call<OutgoingStockpickingBean> getOutgoingStockpicking(@Body HashMap hashMap);

    /**
     * 出库详情
     * */
    @POST("get_outgoing_stock_picking_list")
    Call<SalesOutListResponse> getOutgoingStockpickingList(@Body HashMap hashMap);

    /**
     * 获取反馈，提交反馈
     * */
    @POST("get_factory_remark")
    Call<GetFactroyRemarkBean> getFactroyRemark(@Body HashMap hashMap);

    @POST("update_factory_remark")
    Call<CommonBean> updateFactroyRemark(@Body HashMap hashMap);
}
