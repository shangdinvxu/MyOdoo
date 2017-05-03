package tarce.myodoo.device;

import android.os.Handler;

import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.Device;
import com.newland.mtype.ExModuleType;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.cardreader.CardReader;
import com.newland.mtype.module.common.emv.EmvModule;
import com.newland.mtype.module.common.iccard.ICCardModule;
import com.newland.mtype.module.common.light.IndicatorLight;
import com.newland.mtype.module.common.pin.K21Pininput;
import com.newland.mtype.module.common.printer.Printer;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.scanner.BarcodeScanner;
import com.newland.mtype.module.common.scanner.BarcodeScannerManager;
import com.newland.mtype.module.common.security.SecurityModule;
import com.newland.mtype.module.common.serialport.SerialModule;
import com.newland.mtype.module.common.sm.SmModule;
import com.newland.mtype.module.common.storage.Storage;
import com.newland.mtype.module.common.swiper.K21Swiper;
import com.newland.mtypex.nseries.NSConnV100ConnParams;

//import com.newland.mtype.ExModuleType;
//import com.newland.mtype.module.common.serialport.SerialModule;6


public class N900Device extends AbstractDevice {
	private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
	private static BaseActivity baseActivity;
	private static N900Device n900Device=null;
	private static DeviceManager deviceManager ;

	private N900Device(BaseActivity baseactivity) {
		N900Device.baseActivity = baseactivity;
	}

	public static N900Device getInstance(BaseActivity baseactivity) {
		if (n900Device == null) {
			synchronized (N900Device.class) {
				if (n900Device == null) {
					n900Device = new N900Device(baseactivity);
				}
			}
		}                                                 
		N900Device.baseActivity = baseactivity;
		return n900Device;
	}

	@Override
	public void connectDevice() {
		baseActivity.showMessage("设备连接中..", Const.MessageTag.TIP);
		try {
			deviceManager = ConnUtils.getDeviceManager();
			deviceManager.init(baseActivity, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
//			deviceManager.init(baseActivity, K21_DRIVER_NAME, new NS3ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
				@Override

				public void onEvent(ConnectionCloseEvent event, Handler handler) {
					if (event.isSuccess()) {
						baseActivity.showMessage("设备被客户主动断开！", Const.MessageTag.NORMAL);
					}
					if (event.isFailed()) {
						baseActivity.showMessage("设备链接异常断开！", Const.MessageTag.ERROR);
					}
				}

				@Override
				public Handler getUIHandler() {
					return null;
				}
			});
			baseActivity.showMessage("N900设备控制器已初始化!", Const.MessageTag.TIP);
			deviceManager.connect();
			deviceManager.getDevice().setBundle(new NSConnV100ConnParams());
			baseActivity.showMessage("设备连接成功.", Const.MessageTag.TIP);
			baseActivity.btnStateToWaitingConn();
		} catch (Exception e1) {
			e1.printStackTrace();
			baseActivity.showMessage("链接异常,请检查设备或重新连接..."+e1, Const.MessageTag.ERROR);
		}
	}

	@Override
	public void disconnect() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (deviceManager != null) {
						deviceManager.disconnect();				
						deviceManager = null;
						baseActivity.showMessage("设备断开成功...", Const.MessageTag.TIP);
						baseActivity.btnStateToWaitingInit();
					}
				} catch (Exception e) {
					baseActivity.showMessage("设备断开异常:" + e, Const.MessageTag.TIP);
				}
			}
		}).start();
	}

	@Override
	public boolean isDeviceAlive() {
		boolean ifConnected = ( deviceManager== null ? false : deviceManager.getDevice().isAlive());
        return ifConnected;
	}

	@Override
	public CardReader getCardReaderModuleType() {
		CardReader cardReader=(CardReader) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_CARDREADER);
		return cardReader;
	}

	@Override
	public EmvModule getEmvModuleType() {
		EmvModule emvModule=(EmvModule) deviceManager.getDevice().getExModule("EMV_INNERLEVEL2");
		return emvModule;
	}

	@Override
	public ICCardModule getICCardModule() {
		ICCardModule iCCardModule=(ICCardModule) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_ICCARDREADER);
		return iCCardModule;
	}

	@Override
	public IndicatorLight getIndicatorLight() {
		IndicatorLight indicatorLight=(IndicatorLight) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_INDICATOR_LIGHT);
		return indicatorLight;
	}

	@Override
	public K21Pininput getK21Pininput() {
		K21Pininput k21Pininput=(K21Pininput) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PININPUT);
		return k21Pininput;
	}

	@Override
	public Printer getPrinter() {
		Printer printer=(Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
		printer.init();
		return printer;
	}

	@Override
	public RFCardModule getRFCardModule() {
		RFCardModule rFCardModule=(RFCardModule) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_RFCARDREADER);
		return rFCardModule;
	}

	@Override
	public BarcodeScanner getBarcodeScanner() {
		BarcodeScannerManager barcodeScannerManager=(BarcodeScannerManager) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_BARCODESCANNER);
		BarcodeScanner scanner = barcodeScannerManager.getDefault();
		return scanner;
	}

	@Override
	public SecurityModule getSecurityModule() {
		SecurityModule securityModule=(SecurityModule) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_SECURITY);
		return securityModule;
	}

	@Override
	public Storage getStorage() {
		Storage storage=(Storage) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_STORAGE);
		return storage;
	}

	@Override
	public K21Swiper getK21Swiper() {
		K21Swiper k21Swiper=(K21Swiper) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_SWIPER);
		return k21Swiper;
	}

	@Override
	public Device getDevice() {
		return deviceManager.getDevice();
	}
	
	@Override
	public SerialModule getUsbSerial() {
		SerialModule k21Serial=(SerialModule) deviceManager.getDevice().getExModule(ExModuleType.USBSERIAL);
		return k21Serial;
	}

	@Override
	public SmModule getSmModule() {
		SmModule smModule = (SmModule)deviceManager.getDevice().getStandardModule(ModuleType.COMMON_SM);
		return smModule;
	}
	
}
