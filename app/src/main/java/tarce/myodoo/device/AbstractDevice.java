package tarce.myodoo.device;

import com.newland.mtype.Device;
import com.newland.mtype.module.common.cardreader.CardReader;
import com.newland.mtype.module.common.emv.EmvModule;
import com.newland.mtype.module.common.iccard.ICCardModule;
import com.newland.mtype.module.common.light.IndicatorLight;
import com.newland.mtype.module.common.pin.K21Pininput;
import com.newland.mtype.module.common.printer.Printer;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.scanner.BarcodeScanner;
import com.newland.mtype.module.common.security.SecurityModule;
import com.newland.mtype.module.common.serialport.SerialModule;
import com.newland.mtype.module.common.sm.SmModule;
import com.newland.mtype.module.common.storage.Storage;
import com.newland.mtype.module.common.swiper.K21Swiper;

/**
 * Created by YJF on 2015/8/11 0011.
 */
public abstract class AbstractDevice {

	/**
	 * 断开连接
	 */
	public abstract void disconnect();

	/**
	 * 设备是否连接
	 * @return
	 */
	public abstract boolean isDeviceAlive();

	/**
	 * 连接设备
	 */
	public abstract void connectDevice();
	
	public abstract Device getDevice();
	/**
	 * 获取读卡器模块
	 * @return CardReader
	 */
	public abstract CardReader getCardReaderModuleType();
	/**
	 * 获取emv模块
	 * @return EmvModule
	 */
	public abstract EmvModule getEmvModuleType();
	/**
	 * 获取ic卡模块
	 * @return
	 */
	public abstract ICCardModule getICCardModule();
	/**
	 * 获取指示灯模块
	 * @return
	 */
	public abstract IndicatorLight getIndicatorLight();
	
	/**
	 * 密码输入模块
	 * @return
	 */
	public abstract K21Pininput getK21Pininput();
	/**
	 * 打印模块
	 * @return
	 */
	public abstract Printer getPrinter();
	/**
	 * 非接卡模块
	 * @return
	 */
	public abstract RFCardModule getRFCardModule();
	/**
	 * 扫描模块
	 * @return
	 */
	public abstract  BarcodeScanner getBarcodeScanner();
	/**
	 * 设备安全认证模块
	 * @return
	 */
	public abstract SecurityModule getSecurityModule();
	/**
	 * 存储模块
	 * @return
	 */
	public abstract Storage getStorage();
	/**
	 * 刷卡模块
	 * @return
	 */
	public abstract K21Swiper getK21Swiper();
	
	/**
	 * 串口通信
	 * @return
	 */
	public abstract SerialModule getUsbSerial();
	
	/**
	 * 国密模块
	 * @return
	 */
	public abstract SmModule getSmModule();
}
