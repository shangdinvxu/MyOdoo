package tarce.myodoo.device;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;


import tarce.myodoo.R;

public class BaseActivity extends Activity {
	private TextView tvOperationMessage;
	private N900Device n900Device;
	private String newMessage = "", message;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private Button btn_init, btn_delet;
	private SharedPreferences sharedPreferences;
	private ImageGetter imageGetter;

	public TextView getTvOperationMessage() {
		return tvOperationMessage;
	}

	public void setTvOperationMessage(TextView tvOperationMessage) {
		this.tvOperationMessage = tvOperationMessage;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


	}

	public void initView() {
//		tvOperationMessage = (TextView) findViewById(R.id.test_info);
//		btn_init = (Button) findViewById(R.id.btn_conn);
//		btn_delet = (Button) findViewById(R.id.btn_delet);
		n900Device = N900Device.getInstance(BaseActivity.this);
		sharedPreferences = this.getSharedPreferences("mac", Context.MODE_WORLD_READABLE);
		 imageGetter = new ImageGetter() {
			  @Override
			   public Drawable getDrawable(String source) {
			    int id = Integer.parseInt(source);
			    Drawable drawable = getResources().getDrawable(id);
			    if(getWindowWidth()<=drawable.getIntrinsicWidth()){
			    	drawable.setBounds(0,10,getWindowWidth(),drawable.getIntrinsicHeight());
			    }else{
			    	drawable.setBounds((getWindowWidth()-drawable.getIntrinsicWidth())/2, 10, drawable.getIntrinsicWidth()+(getWindowWidth()-drawable.getIntrinsicWidth())/2,drawable.getIntrinsicHeight());
			    }
			    
			    return drawable;
			   }
			  };
	
		processingUnLock();

	}

	// 连接设备
	public void connectDevice() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
					try {
						if (!n900Device.isDeviceAlive()) {
							n900Device.connectDevice();
						} else {
							tvOperationMessage.append("设备已经连接" + "\r\n");
						}
					} catch (Exception e) {
						tvOperationMessage.append("设备连接异常" + e + "\r\n");
					}
			}
		});
	}
	

	// 显示操作返回的信息
	public void showMessage(final String mess, final int messageType) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				switch (messageType) {
				case Const.MessageTag.NORMAL:
					message = "<font color='black'>" + mess + "</font>";
					break;
				case Const.MessageTag.ERROR:
					message = "<font color='red'>" + mess + "</font>";
					break;
				case Const.MessageTag.TIP:
					message = "<font color='green'>" + mess + "</font>";
					break;
				case Const.MessageTag.DATA:
					message = "<font color='blue'>" + mess + "</font>";
					break;
				case Const.MessageTag.WARN:
					message = "<u><font color='red'>" + mess + "</font></u>";
					break;
				default:
					break;
				}
				newMessage = message + "<br>" + newMessage;
				tvOperationMessage.setText(Html.fromHtml(newMessage,getImageGetter(),null));
			}
		});
	}
	
	public void showPicMessage(final int id) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				newMessage = "<img src='"+id+"' />" + "<br>" + newMessage;
				
				tvOperationMessage.setText(Html.fromHtml(newMessage, getImageGetter(), null));
			}
		});
		
		
	}
	public int getWindowWidth(){
		DisplayMetrics dm = new DisplayMetrics();
		// 获取屏幕信息
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		return dm.widthPixels;
	}


	public ImageGetter getImageGetter() {
		return imageGetter;
	}

	public void setImageGetter(ImageGetter imageGetter) {
		this.imageGetter = imageGetter;
	}

	// 清楚提示信息
	public void clearMessage() {
		tvOperationMessage.setText("");
		newMessage = "";
	}


	// 菜单切换
	public void switchFragment(final int viewContainer, final Fragment newFragment) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				fm = getFragmentManager();
				ft = fm.beginTransaction();
				ft.replace(viewContainer, newFragment, "tag");
				ft.commit();
			}
		}).start();
	}


	public void processingLock() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				SharedPreferences setting = getSharedPreferences("setting", 0);
				SharedPreferences.Editor editor = setting.edit();
				editor.putBoolean("PBOC_LOCK", true);
				editor.commit();
			}
		});

	}

	public boolean processingisLocked() {
		SharedPreferences setting = getSharedPreferences("setting", 0);
		if (setting.getBoolean("PBOC_LOCK", true)) {
			return true;
		} else {
			return false;
		}
	}

	public void processingUnLock() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				SharedPreferences setting = getSharedPreferences("setting", 0);
				SharedPreferences.Editor editor = setting.edit();
				editor.putBoolean("PBOC_LOCK", false);
				editor.commit();
			}
		});

	}

	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public void setSharedPreferences(SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}
	
	/**
	 * 设置成设备等待连接状态
	 * 
	 */
	public void btnStateToWaitingConn() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				btn_init.setEnabled(false);
				btn_delet.setEnabled(true);
			}
		});

	}
	/**
	 * 设置成设备等待初始化状态
	 * 
	 */
	public void btnStateToWaitingInit() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				btn_init.setEnabled(true);
				btn_delet.setEnabled(false);
			}
		});
	}
}
