package android.lifeistech.com.memo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Key;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;
import static android.R.attr.key;
import static android.R.attr.textColorTertiary;
import static android.lifeistech.com.memo.R.attr.title;
import static android.lifeistech.com.memo.R.id.editText;
import static android.lifeistech.com.memo.R.id.image;
import static android.lifeistech.com.memo.R.id.text;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    CardAdapter mCardAdapter;
    List<Card> mCards;
    EditText texttext;


    SharedPreferences strdata;
    SharedPreferences imagedata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);

        mCards = new LinkedList<>();
        imagedata = getSharedPreferences("DataSave", MODE_PRIVATE);
        strdata = getSharedPreferences("StrSave", MODE_PRIVATE);

        ArrayList<String> list = new ArrayList<String>();
        Map prefAll = imagedata.getAll();
        prefAll.isEmpty();

        list.addAll(imagedata.getAll().keySet());


        for (String key : list) {
            Log.d("reading", key);
            String imagePath = imagedata.getString(key, null);
            String memoStr = strdata.getString(key, null);

            Bitmap bitmap = getBitmap(imagePath);

            // カードの追加
            mCards.add(new Card(bitmap, memoStr, key));
        }


        mCardAdapter = new CardAdapter(this, R.layout.card, mCards);
        mListView.setAdapter(mCardAdapter);
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
//                intent.putExtra("captureImage",captureImage);
//                startActivity(intent);
//            }
//        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                mCardAdapter.notifyDataSetChanged();
                // ここ復習
                Card card = mCards.get(position);
                String key = card.getKey();
                strdata.edit().remove(key).apply();
                imagedata.edit().remove(key).apply();
                // ここまで
                mCards.remove(position);
                return true;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = mCards.get(position);
//                String key = card.key;
                String text = card.title;
                Bitmap image = card.image;

                //card.image これ画像ファイル
//               これをeditに渡す
                edit(text, image, position);
            }
        });


    }

    @Nullable
    public static Bitmap getBitmap(String imagePath) {
        File file = new File(imagePath);
        Bitmap bitmap = null;
        try {
            InputStream stream = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(stream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public void edit(String text, Bitmap image ,int position) {
        Intent intent = new Intent(this, EditingActivity.class);
        intent.putExtra("Image", image);
        intent.putExtra("Text", text);
        intent.putExtra("positon", position);
        int requestCode = 1000;
//        bitmapをintentで渡す
        startActivityForResult(intent, requestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode , Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("onActivityResult", "in");
        Bundle bundle = intent.getExtras();

        // 受け取るためのコード
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
            Log.d("returnobject", bundle.getString("key.StringData"));
                int position = bundle.getInt("position");
                String edittext = bundle.getString("key.StringData");


                Card c = mCards.get(position);
                c.title = edittext;
                mCards.set(position, c);
                mCardAdapter.notifyDataSetChanged();
                SharedPreferences.Editor editor = strdata.edit();
                editor.putString(c.key, edittext);
                editor.apply();



            }
        }
    }
}







