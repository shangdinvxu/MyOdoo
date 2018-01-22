package tarce.api.api;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import tarce.model.AddworkBean;
import tarce.model.AllEmployeeBean;
import tarce.model.BuLlBean;
import tarce.model.ChangeProjectBean;
import tarce.model.ChangeStateBean;
import tarce.model.CompanyTwoBean;
import tarce.model.ComponyQueryBean;
import tarce.model.FindProductByConditionResponse;
import tarce.model.GetGroupByListresponse;
import tarce.model.GetProcessBean;
import tarce.model.GetSaleResponse;
import tarce.model.LineBean;
import tarce.model.LoadActionBean;
import tarce.model.MaterialRelationBean;
import tarce.model.OutgoingStockpickingBean;
import tarce.model.ProductLinesBean;
import tarce.model.ProjectBean;
import tarce.model.ProjectDetailBean;
import tarce.model.RequestBindUserBean;
import tarce.model.ScanBean;
import tarce.model.SearchSupplierResponse;
import tarce.model.SoOriginBean;
import tarce.model.SoQcBean;
import tarce.model.StateCountBean;
import tarce.model.TimeSheetBean;
import tarce.model.WorkTypeBean;
import tarce.model.inventory.AreaMessageBean;
import tarce.model.inventory.AutoAddworkBean;
import tarce.model.inventory.BomFramworkBean;
import tarce.model.inventory.ChangeWeightBean;
import tarce.model.inventory.CommonBean;
import tarce.model.inventory.CustomerSaleBean;
import tarce.model.inventory.DoUnreservBean;
import tarce.model.inventory.FinishPrepareMaBean;
import tarce.model.inventory.FreeWorkBean;
import tarce.model.inventory.GetDonePickBean;
import tarce.model.inventory.GetFactroyRemarkBean;
import tarce.model.inventory.GetFeedbackBean;
import tarce.model.inventory.GetReturnMaterBean;
import tarce.model.inventory.InventroyDetailBean;
import tarce.model.inventory.InventroyResultBean;
import tarce.model.inventory.LoadInspectionBean;
import tarce.model.inventory.LoadProductBean;
import tarce.model.inventory.MaterialDetailBean;
import tarce.model.inventory.NFCUserBean;
import tarce.model.inventory.NFCWorkerBean;
import tarce.model.inventory.NFcLoginBean;
import tarce.model.inventory.NewSaleBean;
import tarce.model.inventory.NewSaleListBean;
import tarce.model.inventory.NfcOrderBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.model.inventory.OutsourceBean;
import tarce.model.inventory.PickingDetailBean;
import tarce.model.inventory.ProcessDeatilBean;
import tarce.model.inventory.ProductProcessBean;
import tarce.model.inventory.QcFeedbaskBean;
import tarce.model.inventory.RejectResultBean;
import tarce.model.inventory.RetailSubBean;
import tarce.model.inventory.RukuBean;
import tarce.model.inventory.SalesOutListResponse;
import tarce.model.inventory.GetNumProcess;
import tarce.model.inventory.StartInspectBean;
import tarce.model.inventory.StartProductBean;
import tarce.model.inventory.StartReworkBean;
import tarce.model.inventory.StockListBean;
import tarce.model.inventory.StockMoveListBean;
import tarce.model.inventory.TakeDeAreaBean;
import tarce.model.inventory.TakeDelListBean;
import tarce.model.inventory.UpdateMessageBean;
import tarce.model.inventory.WorkingWorkerBean;

/**
 * Created by Daniel.Xu on 2017/2/17.
 */

public interface InventoryApi {
    /**
     * 提交工时工种
     * */
    @POST("action_assign_hour_spent")
    Call<TimeSheetBean>  actionAssinHour(@Body HashMap hashMap);
    /**
     * 获取工种
     * */
    @POST("get_work_type")
    Call<WorkTypeBean> getWorkType(@Body HashMap hashMap);
    /**
     * 分配二次加工负责人
     * */
    @POST("action_assign_secondary_operation_partner")
    Call<TimeSheetBean> action_partner(@Body HashMap hashMap);
    /**
     * 获取所有的员工
     * */
    @POST("get_all_employees")
    Call<AllEmployeeBean> getAllEmployees(@Body HashMap hashMap);
    /**
     * 扫描二维码入库
     * */
    @POST("action_transfer_from_sub")
    Call<ScanBean> actionTransfer(@Body HashMap hashMap);
    /**
     * 扫描二维码之后
     * */
    @POST("view_feedback_detail")
    Call<ScanBean> viewFeedbackDetail(@Body HashMap hashMap);
    /**
     * 获取生产状态的树木
     * */
    @POST("get_count_mrp_production")
    Call<StateCountBean> getCountMrpPro(@Body HashMap hashMap);

    /**
     * 获取品检单数量
     * */
    @POST("get_count_qc_feedback")
    Call<StateCountBean> getCountQc(@Body HashMap hashMap);
    /**
     * get_so_mrp_production
     * 得到mo源
     * */
    @POST("get_so_mrp_production")
    Call<SoOriginBean> getSoMrp(@Body HashMap hashMap);
    /**
     * get_mrp_production_new
     * 获取生产单列表new
     * */
    /**
     * test
     * */
    @POST("get_blog_list")
    Call<Object> getBlogList(@Body HashMap hashMap);
    /**
     * 外协改变状态
     * */
    @POST("change_outsourcing_order_state")
    Call<OutsourceBean> changeOutsouceOrder(@Body HashMap hashMap);
    /**
     * w外协列表
     * */
    @POST("get_outsourcing_order_by_state")
    Call<OutsourceBean> getOutOrderbyState(@Body HashMap hashMap);
    /**
     * 二次生产
     * */
    @POST("get_secondary_mos")
    Call<PickingDetailBean> getSecondMos(@Body HashMap hashMap);
    /**
     * 新的二次生产
     * */
    @POST("get_new_secondary_mos")
    Call<PickingDetailBean> getNewSecondMos(@Body HashMap hashMap);
    /**
     * 获取物料关系
     * */
    @POST("get_mrp_rule_detail")
    Call<MaterialRelationBean> getMrpRule(@Body HashMap hashMap);
    /**
     * 工程领料状态改变
     * */
    @POST("change_material_request_state")
    Call<ChangeProjectBean> packOperIds(@Body HashMap hashMap);
    /**
     * 获取工程领料详情
     * */
    @POST("get_one_material_request_show")
    Call<ProjectDetailBean> getOneMaterShow(@Body HashMap hashMap);
    /**
     * 获取工程领料列表
     * */
    @POST("get_all_material_request_show")
    Call<ProjectBean> getPickrequest(@Body HashMap hashMap);
    /**
     * 扫描后更改的库存内容提交
     * */
    @POST("create_stock_inventory")
    Call<CommonBean> createStockInventroy(@Body HashMap hashMap);
    /**
     * 单条改变数据
     * */
    @POST("new_prepare_material")
    Call<OrderDetailBean> newPrepareMater(@Body HashMap hashMap);
    /**
     * 仓库人员打卡
     * */
    @POST("auth_warehouse_manager")
    Call<NfcOrderBean> authWarehouse(@Body HashMap hashMap);
    /**
     * 切换公司
     * */
    @POST("change_company")
    Call<ComponyQueryBean> changeCompany(@Body HashMap hashMap);
    /**
     * 切换公司
     * */
    @POST("change_company")
    Call<CompanyTwoBean> changeCompanyTwo(@Body HashMap hashMap);
    /**
     * 已完成订单
     * */
    @POST("get_done_picking")
    Call<GetDonePickBean> getDonePicking(@Body HashMap hashMap);
    /**
     * 根据订单号搜索（新）
     * */
    @POST("get_picking_by_origin")
    Call<NewSaleListBean> getPickby(@Body HashMap hashMap);
    /**
     * 获取客户下的销售列表
     * */
    @POST("get_stock_picking_by_partner")
    Call<NewSaleListBean> getBypartner(@Body HashMap hashMap);
    /**
     * 根据team获取客户
     * */
    @POST("get_partner_by_team")
    Call<CustomerSaleBean> getPartnerByTeam(@Body HashMap hashMap);
    /**
     * 获取销售团队
     * */
    @POST("get_sale_team")
    Call<NewSaleBean> getSaleTeam(@Body HashMap hashMap);
    /**
     * 查询移动列表
     * */
    @POST("get_stock_moves_by_product_id")
    Call<StockMoveListBean> getStockMoves(@Body HashMap hashMap);

    /**
     * 库存查询
     * */
    @POST("get_product_list")
    Call<StockListBean> getProductList(@Body HashMap hashMap);
    /**
     * 改变某一产品重量
     * */
    @POST("change_product_weight")
    Call<ChangeWeightBean> changeProductWeight(@Body HashMap hashMap);
    /**
     * nfc登录
     * */
    @POST("get_email_by_card_num")
    Call<NFcLoginBean> getByCardnum(@Body HashMap hashMap);
    /**
     * 请求绑定员工
     */
    @POST("bind_nfc_card")
    Call<RequestBindUserBean> requestBindUser(@Body HashMap hashMap);


    /**
     * 请求所有的员工
     */
    @POST("get_employee")
    Call<NFCUserBean> getAllUser(@Body HashMap hashMap);



    /**
     * 请求员工数据
     * */
    @POST("get_department_by_parent")
    Call<NFCWorkerBean> getDepart(@Body HashMap hashMap);
    /**
     * 提交入库（位置信息）
     * */
    @POST("change_stock_picking_state")
    Call<TakeDeAreaBean> commitRuku(@Body HashMap hashMap);

    /**
     * 入库
     * */
    @POST("change_stock_picking_state")
    Call<TakeDeAreaBean> ruKu(@Body HashMap hashMap);

    /**
     * 退回
     * */
    @POST("change_stock_picking_state")
    Call<TakeDeAreaBean> reject(@Body HashMap hashMap);

    /**
     * 获取反馈原因接口
     * */
    @POST("get_material_remark")
    Call<GetFeedbackBean> getRemark(@Body HashMap hashMap);

    /**
     *添加新的反馈原因
     * */
    @POST("create_material_remark")
    Call<GetFeedbackBean> addNewRemark(@Body HashMap hashMap);

    /**
     * 选择某个原因
     * */
    @POST("add_material_remark")
    Call<CommonBean> selectRemark(@Body HashMap hashMap);

    /**
     * 收货页面初始化数据
     * */
    @POST("get_group_by_list")
    Call<GetGroupByListresponse> getGroupsByList(@Body HashMap hashMap);

    /**
     * 入库品检列表
     * */
    @POST("get_stock_picking_list")
    Call<TakeDelListBean> getstockList(@Body HashMap hashMap);

    /**
     * 收货列表
     * */
    @POST("get_incoming_outgoing_stock_picking")
    Call<TakeDelListBean> getInComingOutgoingList(@Body HashMap hashMap);

    /**
     * 搜索供应商或者客户。
     * @param hashMap
     * @return
     */
    @POST("search_supplier")
    Call<SearchSupplierResponse> searchSupplier(@Body HashMap hashMap);

    /**
     * 根据订单号搜索
     * */
    @POST("get_stock_picking_by_origin")
    Call<SalesOutListResponse> searchBySalesNumber(@Body HashMap hashMap);

    /**
     * 根据订单号搜索
     * */
    @POST("get_stock_picking_by_origin")
    Call<TakeDelListBean> searchByTakeNumber(@Body HashMap hashMap);

    /**
     *
     * */
    @POST("get_stock_picking_by_remark")
    Call<TakeDelListBean> seachByNote(@Body HashMap hashMap);
    /**
     * 根据客户全称搜索
     * */
    @POST("get_outgoing_stock_picking")
    Call<OutgoingStockpickingBean> searchByCustomName(@Body HashMap hashMap);

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
     * 获取产线
     * */
    @POST("get_new_production_lines")
    Call<LineBean> getNewProductLines(@Body HashMap hashMap);
    /**
     * 产线分类
     * */
    @POST("get_production_lines")
    Call<ProductLinesBean> getProductLines(@Body HashMap hashMap);
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
    Call<PickingDetailBean> getRecentOr(@Body HashMap hashMap);

    /**
     * 新的生产顺序排序
     * */
    @POST("get_new_mrp_production")
    Call<PickingDetailBean> getNewRecentOr(@Body HashMap hashMap);

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
//    Observable<UpdateMessageBean> commitMessage(@Body HashMap hashMap);
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
    Call<OrderDetailBean> checkOut(@Body HashMap hashMap);

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
    Call<OrderDetailBean> stopProductLine(@Body HashMap hashMap);

    /**
     * 生产完成
     * */
    @POST("produce_finish")
    Call<OrderDetailBean> finishProduct(@Body HashMap hashMap);

    /**
     * 提交产品位置信息
     * */
    @POST("upload_procure_info")
    Call<UpdateMessageBean> uploadProductArea(@Body HashMap hashMap);

    /**
     * 保存备料信息
     * */
    @POST("saving_material_data")
    Call<UpdateMessageBean> saveMaterialData(@Body HashMap hashMap);

    /**
     * 提交备料
     * */
    @POST("finish_prepare_material")
    Call<FinishPrepareMaBean> finishPrepareMa(@Body HashMap hashMap);

    /**
     * 提交备料(新的接口)
     * */
    @POST("new_finish_prepare_material")
    Call<FinishPrepareMaBean> newfinishPrepareMa(@Body HashMap hashMap);
//    Observable<FinishPrepareMaBean> finishPrepareMa(@Body HashMap hashMap);
    /**
     * 等待生产品检
     * */
    @POST("get_qc_feedback")
    Call<QcFeedbaskBean> getQcfb(@Body HashMap hashMap);

//    @POST("get_new_qc_feedback")
//    Call<QcFeedbaskBean> getNewQcfb(@Body HashMap hashMap);

    @POST("get_new_qc_feedback")
    Call<SoQcBean> getNewQcfb(@Body HashMap hashMap);
    /**
     * 开始品检
     * */
    @POST("start_quality_inspection")
    Call<StartInspectBean> startInspection(@Body HashMap hashMap);

    /**
     * 品检通过,品检不通过
     * */
    @POST("inspection_result")
    Call<StartInspectBean> resultInspec(@Body HashMap hashMap);
    @POST("inspection_result")
    Call<RejectResultBean> resultInspecFirst(@Body HashMap hashMap);

    /**
     * 入库
     * */
    @POST("produce_done")
    Call<RukuBean> produceDone(@Body HashMap hashMap);

    /**
     * 随时退料
     * */
    @POST("return_material_anytime")
    Call<OrderDetailBean> returnMaterialAny(@Body HashMap hashMap);
    /**
     * 提交退料数量
     * */
    @POST("return_material")
    Call<OrderDetailBean> commitFeedNum(@Body HashMap hashMap);

    /**
     * 获取退料数量
     * */
    @POST("get_return_detail")
    Call<GetReturnMaterBean> getReturnMater(@Body HashMap hashMap);
    /**
     * 清点退料/提交退料数量
     * */
    @POST("semi_finished_return_material")
    Call<OrderDetailBean> semiCommitReturn(@Body HashMap hashMap);

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
     * 返工中新接口
     * */
    @POST("get_new_reworking_production")
    Call<PickingDetailBean> newReworkIng(@Body HashMap hashMap);
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

    /**
     * 零售出货
     * */
    @POST("eb_order/get_eb_shop_list")
    Call<RetailSubBean> getEbShopList(@Body HashMap hashMap);

    /**
     * 盘点
     * */
    @POST("get_stock_inventory_list")
    Call<InventroyResultBean> getStockInvenList(@Body HashMap hashMap);

    /**
     * 盘点详细列表
     * */
    @POST("get_stock_inventory_detail")
    Call<InventroyDetailBean> getStockDetail(@Body HashMap hashMap);
}
