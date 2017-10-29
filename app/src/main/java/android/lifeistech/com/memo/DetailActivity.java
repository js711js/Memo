package android.lifeistech.com.memo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Bitmap captureImage = (Bitmap) intent.getParcelableExtra("captureImage");
        ImageView imageView = (ImageView)findViewById(R.id.captureImage);
        imageView.setImageBitmap(captureImage);


    }
}
