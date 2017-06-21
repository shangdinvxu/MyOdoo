package tarce.myodoo.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.myodoo.R;
import tarce.support.ViewUtils;

/**
 * Created by rose.zou  on 2017/6/21.
 */

public class TakeDeliverActivity extends BaseActivity {
    @InjectView(R.id.edit_search_custom)
    EditText editSearchCustom;
    @InjectView(R.id.edit_search_order)
    EditText editSearchOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_deliver);
        ButterKnife.inject(this);

        editCustomListener();
        editOrderListener();
    }

    private void editOrderListener() {
        editSearchCustom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    ViewUtils.collapseSoftInputMethod(TakeDeliverActivity.this, editSearchCustom);
                }
                return false;
            }
        });
    }

    private void editCustomListener() {
        editSearchOrder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    ViewUtils.collapseSoftInputMethod(TakeDeliverActivity.this, editSearchOrder);
                }
                return false;
            }
        });
    }

    /**
     *
     * */
}
