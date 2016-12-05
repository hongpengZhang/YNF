package bw.com.yunifangstore.base;

import android.text.TextUtils;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.MD5Encoder;
import bw.com.yunifangstore.view.ShowingPage;

/**
 * @author : 张鸿鹏
 * @date : 2016/11/29.
 */

public abstract class BaseData {

    private final File fileDir;
    public static final int  NORMALTIME = 3 * 24 * 60 * 60 * 1000;
    public static final int  NOTIME = 0;
    public BaseData() {
        //缓存路径
        File cacheDir = CommonUtils.getContext().getCacheDir();
        fileDir = new File(cacheDir, "yunifang");
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
    }

    public void getData(String path, String args, int index, int validTime) {

        if (validTime == 0) {
            getDataFromNet(path, args, index, validTime);
        } else {
            String data = getDataFromLocal(path, args, index, validTime);
            if (TextUtils.isEmpty(data)) {
                getDataFromNet(path, args, index, validTime);
            } else {
                setResultData(data);
            }
        }
    }

    public abstract void setResultData(String data);


    private String getDataFromLocal(String path, String args, int index, int validTime) {
        try {
            File file = new File(fileDir, MD5Encoder.encode(path) + index);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String writeTime = bufferedReader.readLine();
            long time = Long.parseLong(writeTime);
            //与之前时间作比较
            if (System.currentTimeMillis() < time) {
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                bufferedReader.close();
                return builder.toString();
            } else {
                //无效
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 从网络获取
     */
    private void getDataFromNet(final String path, final String args, final int index, final int validTime) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(path +"?"+ args)
                .build();
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //设置error
                setResulttError(ShowingPage.StateType.STATE_LOAD_ERROR);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String data = response.body().string();
                CommonUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        //设置数据
                        setResultData(data);

                    }
                });
                writeDataToLocal(path, args, index, validTime, data);
            }
        });
    }

    protected abstract void setResulttError(ShowingPage.StateType stateLoadError);

    /**
     * 写入本地
     */
    private void writeDataToLocal(String path, String args, int index, int validTime, String data) {
        try {
            File file = new File(fileDir, MD5Encoder.encode(path) + index);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(System.currentTimeMillis() + validTime + "\r\n");
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
