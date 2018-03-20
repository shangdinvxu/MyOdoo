package tarce.myodoo.activity.salesout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.SearchView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.CustomerSaleBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.LoginActivity;
import tarce.myodoo.adapter.NewSaleCustomAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by zws on 2017/8/9.
 */

public class NewSaleActivity extends Activity {
    private static final int Refresh_Move = 1;//下拉动作
    private static final int Load_Move = 2;//上拉动作
    private static final int LOAD_NUM = 30;//每次加载的数量

    @InjectView(R.id.sale_title)
    TextView saleTitle;
    /*@InjectView(R.id.back_no_left)
    ImageView backNoLeft;*/
    /*@InjectView(R.id.right_detail)
    ImageView rightDetail;*/
//    @InjectView(R.id.search_newsale)
//    SearchView searchNewsale;
    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    @InjectView(R.id.tv_diy_update)
    TextView tvDiyUpdate;
    @InjectView(R.id.wait_num)
    TextView waitNum;
    @InjectView(R.id.can_num)
    TextView canNum;
    @InjectView(R.id.search_newsale)
    AutoCompleteTextView searchNewsale;
    private int team_id;
    private InventoryApi inventoryApi;
    private ProgressDialog progressDialog;
    private NewSaleCustomAdapter newSaleCustomAdapter;
    private List<CustomerSaleBean.ResultBean.ResDataBean> res_data;
    private List<CustomerSaleBean.ResultBean.ResDataBean> allList;
    private int loadTime = 0;
    private String from;
    private String name;//搜索的单号
    private ArrayList<String> searchList;
    private ListPopupWindow mListPop;
    private Myadapter stringArrayAdapter;
    private String textAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsale);
        ButterKnife.inject(this);

        progressDialog = new ProgressDialog(NewSaleActivity.this);
        progressDialog.setMessage("努力加载中...");
        progressDialog.setCancelable(false);
        inventoryApi = RetrofitClient.getInstance(NewSaleActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if ("firstPage".equals(from)) {
            name = intent.getStringExtra("name");
            serchData(name);
        } else {
            team_id = intent.getIntExtra("team_id", -1);
            String team_name = intent.getStringExtra("team_name");
            saleTitle.setText(team_name);
            getData(LOAD_NUM, 0, Refresh_Move);
            initView();
        }
        swipeTarget.setLayoutManager(new LinearLayoutManager(NewSaleActivity.this));
        swipeTarget.addItemDecoration(new DividerItemDecoration(NewSaleActivity.this,
                DividerItemDecoration.VERTICAL));
        initAutoText();
    }

    private void initView() {
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);
        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(LOAD_NUM, 0, Refresh_Move);
                if (newSaleCustomAdapter != null) {
                    newSaleCustomAdapter.notifyDataSetChanged();
                }
                swipeToLoad.setRefreshing(false);
            }
        });
        swipeToLoad.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                swipeToLoad.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*loadTime++;
                        getData(LOAD_NUM, LOAD_NUM*loadTime, Load_Move);*/
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 500);
            }
        });
    }

    /**
     * 设置autocompleteTextview
     * */
    private void initAutoText(){
        searchList = new ArrayList<>();
        searchList.add("搜索：按客户简称");
        searchList.add("搜索：按SO源单据");
        searchNewsale.setThreshold(1);
        searchNewsale.setSingleLine(true);
        stringArrayAdapter = new Myadapter(NewSaleActivity.this, android.R.layout.simple_list_item_1, searchList);
        searchNewsale.setAdapter(stringArrayAdapter);
        searchNewsale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textAuto = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                textAuto = editable.toString();
            }
        });
        searchNewsale.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    serchData(textAuto);
                } else if (position == 1) {
                    Intent intent = new Intent(NewSaleActivity.this, NewSaleListActivity.class);
                    intent.putExtra("from", "fistPage");
                    intent.putExtra("danhao", textAuto);
                    startActivity(intent);
                }
                ViewUtils.collapseSoftInputMethod(NewSaleActivity.this, searchNewsale);
                searchNewsale.setText(textAuto);
                searchNewsale.setSelection(searchNewsale.getText().length());
            }
        });
    }
    private class Myadapter extends ArrayAdapter{

        public Myadapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }

        public Myadapter(@NonNull Context context, @LayoutRes int resource, List<String> stringList){
            super(context, resource, stringList);
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return new ArrayFliter();
        }
    }
    private class ArrayFliter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            filterResults.values = searchList;
            filterResults.count = searchList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults.count>0){
                stringArrayAdapter.notifyDataSetChanged();
            }else {
                stringArrayAdapter.notifyDataSetInvalidated();
            }
        }
    }
    //设置listpopwindow
//    private void initListPop() {
//        searchList.add("搜索：按客户简称");
//        searchList.add("搜索：按SO源单据");
//        mListPop = new ListPopupWindow(this);
//        mListPop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, searchList));
//        mListPop.setWidth(RecyclerView.LayoutParams.WRAP_CONTENT);
//        mListPop.setHeight(RecyclerView.LayoutParams.WRAP_CONTENT);
//        mListPop.setAnchorView(searchNewsale);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
//        mListPop.setModal(true);//设置是否是模式
////        mListPop.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
//        mListPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                if (position == 0) {
//                    serchData(searchNewsale.getQuery().toString());
//                } else if (position == 1) {
//                    Intent intent = new Intent(NewSaleActivity.this, NewSaleListActivity.class);
//                    intent.putExtra("from", "fistPage");
//                    intent.putExtra("danhao", searchNewsale.getQuery().toString());
//                    startActivity(intent);
//                }
//                mListPop.dismiss();
//            }
//        });
//    }

    @OnClick(R.id.tv_diy_update)
    void updateData(View view) {
        if ("firstPage".equals(from)) {
            serchData(name);
        } else {
            getData(LOAD_NUM, 0, Refresh_Move);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getData(final int limit, final int offset, final int move) {
        progressDialog.show();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("team_id", team_id);
        /*hashMap.put("limit", limit);
        hashMap.put("offset", offset);*/
        Call<CustomerSaleBean> partnerByTeam = inventoryApi.getPartnerByTeam(hashMap);
        partnerByTeam.enqueue(new MyCallback<CustomerSaleBean>() {
            @Override
            public void onResponse(Call<CustomerSaleBean> call, Response<CustomerSaleBean> response) {
                progressDialog.dismiss();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(NewSaleActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1) {
                    res_data = response.body().getResult().getRes_data();
                    allList = res_data;
                    int sum_wait = 0;
                    int sum_can = 0;
                    if (res_data != null) {
                        for (int i = 0; i < res_data.size(); i++) {
                            sum_wait = sum_wait + res_data.get(i).getWaiting_data();
                            sum_can = sum_can + res_data.get(i).getAble_to_data();
                        }
                    }
                    waitNum.setText("" + sum_wait);
                    canNum.setText("" + sum_can);
                    newSaleCustomAdapter = new NewSaleCustomAdapter(R.layout.item_customsale, res_data);
                    swipeTarget.setAdapter(newSaleCustomAdapter);
                    initListner();
                } else {
                    ToastUtils.showCommonToast(NewSaleActivity.this, "加载失败，请稍后重试");
                }
            }

            @Override
            public void onFailure(Call<CustomerSaleBean> call, Throwable t) {
                progressDialog.dismiss();
                ToastUtils.showCommonToast(NewSaleActivity.this, "加载失败，请稍后重试");
            }
        });
    }

//    private void searchLinst() {
//        searchNewsale.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                mListPop.show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                if (StringUtils.isNullOrEmpty(newText)) {
//                    mListPop.dismiss();
//                    ViewUtils.collapseSoftInputMethod(NewSaleActivity.this, searchNewsale);
//                    res_data = allList;
//                    newSaleCustomAdapter.setNewData(res_data);
//                }
////                else {
////                    mListPop.show();
////                }
////                soOriginAdapter.setNewData(res_data);
////                soOriginAdapter.notifyDataSetChanged();
////                if (StringUtils.isNullOrEmpty(newText)) {
////                    res_data = allList;
////                    newSaleCustomAdapter.setNewData(res_data);
////                }
//                return false;
//            }
//        });
//    }

    //云搜索数据
    private void serchData(String query) {
        progressDialog.show();
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (!"firstPage".equals(from)) {
            hashMap.put("team_id", team_id);
        }
        hashMap.put("name", query);
        Call<CustomerSaleBean> partnerByTeam = inventoryApi.getPartnerByTeam(hashMap);
        partnerByTeam.enqueue(new MyCallback<CustomerSaleBean>() {
            @Override
            public void onResponse(Call<CustomerSaleBean> call, Response<CustomerSaleBean> response) {
                progressDialog.dismiss();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(NewSaleActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code() == 1) {
                    final List<CustomerSaleBean.ResultBean.ResDataBean> data = response.body().getResult().getRes_data();
                    if (data == null) {
                        ToastUtils.showCommonToast(NewSaleActivity.this, "未找到相关单据");
                        return;
                    }
                    if (newSaleCustomAdapter != null) {
                        newSaleCustomAdapter.setNewData(data);
                    } else {
                        newSaleCustomAdapter = new NewSaleCustomAdapter(R.layout.item_customsale, data);
                        swipeTarget.setAdapter(newSaleCustomAdapter);
                    }
                    newSaleCustomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter listAdapter, View view, int position) {
                            Intent intent = new Intent(NewSaleActivity.this, NewSaleListActivity.class);
                            intent.putExtra("partner_id", data.get(position).getPartner_id());
                            intent.putExtra("partner_name", data.get(position).getName());
                            startActivity(intent);
                        }
                    });
                } else if (response.body().getResult().getRes_code() == -1) {
                    //   ToastUtils.showCommonToast();
                }
            }

            @Override
            public void onFailure(Call<CustomerSaleBean> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.back_no_left)
    void finishThis(View view) {
        finish();
    }

    private void initListner() {
        newSaleCustomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter listAdapter, View view, int position) {
                List<CustomerSaleBean.ResultBean.ResDataBean> data = listAdapter.getData();
                Intent intent = new Intent(NewSaleActivity.this, NewSaleListActivity.class);
                intent.putExtra("partner_id", data.get(position).getPartner_id());
                intent.putExtra("partner_name", data.get(position).getName());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViewUtils.collapseSoftInputMethod(NewSaleActivity.this, searchNewsale);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
