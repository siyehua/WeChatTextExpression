package com.siyehua.wechattextexpression;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    String patch;
    TextView textView;
    File rootPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText) findViewById(R.id.et_content);
        textView = (TextView) findViewById(R.id.tv_result);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        rootPath = new File(Environment.getExternalStorageDirectory().getPath() + "/360");
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }

        textView.setDrawingCacheEnabled(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setEnabled(false);
                Toast.makeText(MainActivity.this, "正在保存...", Toast.LENGTH_SHORT).show();
                try {
                    patch = rootPath.getPath() + "/" + "emoji_" + System.currentTimeMillis() + "" +
                            ".png";
                    textView.buildDrawingCache();
                    Bitmap bitmap = textView.getDrawingCache();
                    OutputStream outputStream = new FileOutputStream(patch);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    scanPhotos(patch, MainActivity.this);
                    textView.destroyDrawingCache();
                    Toast.makeText(MainActivity.this, "保存成功:\n" + patch, Toast.LENGTH_SHORT).show();

                    if (shareFlag) {
                        shareFlag = false;
                        shareImage(patch);
                    }
                    textView.setEnabled(true);
                    patch = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void downTextSize(View view) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() - 4);
    }

    public void upTextSize(View view) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize() + 4);
    }

    public void openWeChat(View view) {
        view.setEnabled(false);
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        startActivity(intent);
        view.setEnabled(true);
    }

    private boolean shareFlag = false;

    public void shareImage(View view) {
        view.setEnabled(false);
        if (patch == null) {
            shareFlag = true;
            textView.performClick();
        } else {
            shareImage(patch);
        }
        view.setEnabled(true);
    }

    public void clearImage(View view) {
        view.setEnabled(false);
        File[] tmpArr = rootPath.listFiles();
        for (File file : tmpArr) {
            file.delete();
        }
        Toast.makeText(this, "已清理", Toast.LENGTH_SHORT).show();
        view.setEnabled(true);
    }

    public void help(View view) {
        view.setEnabled(false);
        startActivity(new Intent(this, HelpActivity.class));
        view.setEnabled(true);
    }

    private void shareImage(String bitmap) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools" + "" +
                ".ShareImgUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("image/*");
        Uri uri = Uri.parse("file://" + bitmap);
        intent.putExtra(Intent.EXTRA_STREAM, uri);//uri为你要分享的图片的uri
        startActivity(intent);
    }

    private void scanPhotos(String filePath, Context context) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(filePath));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    public static Bitmap optimizeBitmap(String pathName, int maxWidth, int maxHeight) {
        Bitmap result = null;
        try {
            // 图片配置对象，该对象可以配置图片加载的像素获取个数
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 表示加载图像的原始宽高
            options.inJustDecodeBounds = true;
            result = BitmapFactory.decodeFile(pathName, options);
            // Math.ceil表示获取与它最近的整数（向上取值 如：4.1->5 4.9->5）
            int widthRatio = (int) Math.ceil(options.outWidth / maxWidth);
            int heightRatio = (int) Math.ceil(options.outHeight / maxHeight);
            // 设置最终加载的像素比例，表示最终显示的像素个数为总个数的
            if (widthRatio > 1 || heightRatio > 1) {
                if (widthRatio > heightRatio) {
                    options.inSampleSize = widthRatio;
                } else {
                    options.inSampleSize = heightRatio;
                }
            }
            // 解码像素的模式，在该模式下可以直接按照option的配置取出像素点
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inJustDecodeBounds = false;
            result = BitmapFactory.decodeFile(pathName, options);

        } catch (Exception e) {
        }
        return result;
    }
}
//ic_launcher
