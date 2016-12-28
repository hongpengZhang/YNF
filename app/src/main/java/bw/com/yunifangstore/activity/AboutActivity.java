package bw.com.yunifangstore.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;

import bw.com.yunifangstore.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_er_code;
    private ImageView iv_title_back;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //生成二维码
            Bitmap qrBitmap = generateBitmap("http://user.qzone.qq.com/1204304747/infocenter?ptsig=jFLjnIakOKcPxQ*gsMKMV*y3ozgUcAYmpWOY*Qrervg_", 500, 500);
            iv_er_code.setImageBitmap(qrBitmap);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        iv_er_code = (ImageView) findViewById(R.id.iv_er_code);
        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_er_code.setOnClickListener(this);
        iv_title_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.aboutUs);
        handler.sendEmptyMessageDelayed(0, 1000);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_title_back:
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
        }
    }

    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


}
