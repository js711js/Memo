package android.lifeistech.com.memo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;


public class TopActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 777;

    static final int REQUEST_CAPTURE_IMAGE = 100;
    Button button1;
    ImageView imageView1;
    Button button2;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        findViews();
        setListeners();

        if (PermissionChecker.checkSelfPermission(TopActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("permission check", "not granted");
            requestPermission();
        }
    }

    protected void findViews() {
        button2 = (Button) findViewById(R.id.button2);
        button1 = (Button) findViewById(R.id.button1);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
    }

    protected void setListeners() {
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(
                        intent,
                        REQUEST_CAPTURE_IMAGE);
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {
        if (REQUEST_CAPTURE_IMAGE == requestCode
                && resultCode == TopActivity.RESULT_OK) {
            Bitmap capturedImage =
                    (Bitmap) data.getExtras().get("data");
            saveImage(capturedImage);
            imageView1.setImageBitmap(capturedImage);
            Intent intent = new Intent(getApplication(), CreateActivity.class);
            intent.putExtra("captureImage", capturedImage);
            intent.putExtra("key", fileName);
            startActivity(intent);

        }
    }

    public void start() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveImage(Bitmap bm) {
        try {
            File disDir = Environment.getExternalStorageDirectory();
            fileName = getFileName();
            File file = new File(
                    disDir.getAbsolutePath()
                            + "/" + Environment.DIRECTORY_DCIM,
                    fileName);

            SharedPreferences data = getSharedPreferences("DataSave", MODE_PRIVATE);
            SharedPreferences.Editor editor = data.edit();
            editor.putString(fileName, file.getPath());
            editor.apply();

            FileOutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String getFileName() {
        Calendar c = Calendar.getInstance();
        String s = c.get(Calendar.YEAR)
                + "_" + (c.get(Calendar.MONTH) + 1)
                + "_" + c.get(Calendar.DAY_OF_MONTH)
                + "_" + c.get(Calendar.HOUR_OF_DAY)
                + "_" + c.get(Calendar.MINUTE)
                + "_" + c.get(Calendar.SECOND)
                + "_" + c.get(Calendar.MILLISECOND)
                + ".jpg";
        return s;
    }

    private void requestPermission() {
        Log.d("request permission.", "");
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                ||
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("パーミッションで追加説明")
                    .setMessage("このアプリで写真を撮るにはパーミッションが必要です")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(TopActivity.this,
                                    new String[]{
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    },
                                    REQUEST_CODE_PERMISSION);
                        }

                    });

        }
    }


}