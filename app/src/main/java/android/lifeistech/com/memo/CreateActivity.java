package android.lifeistech.com.memo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import static android.lifeistech.com.memo.R.id.editText;

public class CreateActivity extends AppCompatActivity {
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ImageView imageview = (ImageView) findViewById(R.id.imageView);
        text = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        final Bitmap captureImage = (Bitmap) intent.getParcelableExtra("captureImage");

        imageview.setImageBitmap(captureImage);
        // imageviewにcaptureImageをセット

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

    }

    public void start() {
        // ボタンが押されたら
        // Edittextの文字を撮ってきて、SharedPreferencesに保存
        SharedPreferences data = getSharedPreferences("StrSave", MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();
        editor.putString(getIntent().getStringExtra("key"), text.getText().toString());
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }
}
