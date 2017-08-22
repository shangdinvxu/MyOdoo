package tarce.myodoo.utils;

import android.app.ProgressDialog;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import tarce.myodoo.activity.OrderDetailActivity;
import tarce.support.ToastUtils;

/**
 * Created by zws on 2017/8/18.
 */

public class FileUtil {

    /**
     * 传入文件 url  文件路径  和 弹出的dialog  进行 下载文档
     */
    public static File downLoad(String serverpath, String savedfilepath, ProgressDialog pd) {
        try {
            URL url = new URL(serverpath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Encoding", "identity");
            if (conn.getResponseCode() == 200) {
                int max = conn.getContentLength();
                Log.e("zws", "长度为="+max);
                pd.setMax(max);
                InputStream is = conn.getInputStream();
                File file = new File(savedfilepath);
                FileOutputStream fos = new FileOutputStream(file);
                int len = 0;
                byte[] buffer = new byte[1024];
                int total = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    total += len;
                    pd.setProgress(total);
                }
                fos.flush();
                fos.close();
                is.close();
                return file;
            } else {
                //ToastUtils.showCommonToast(OrderDetailActivity.this, "link some error");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
