package android.lifeistech.com.memo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.lifeistech.com.memo.R.id.editText;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    CardAdapter mCardAdapter;
    List<Card> mCard;

    SharedPreferences strdata;
    SharedPreferences imagedata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);

        mCard = new LinkedList<>();
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

            File file = new File(imagePath);
            Bitmap bitmap = null;
            try {
                InputStream stream = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(stream);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // カードの追加
            mCard.add(new Card(bitmap, memoStr, key));
        }


        mCardAdapter = new CardAdapter(this, R.layout.card, mCard);
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
                mCard.remove(position);
                mCardAdapter.notifyDataSetChanged();
                // ここ復習
                Card card = mCard.get(position);
                String key = card.getKey();
                strdata.edit().remove(key).apply();
                imagedata.edit().remove(key).apply();
                // ここまで
                return true;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int size = mCard.size();
                Card card = mCard.get(position);
                mCard.add(size, card);
                mCard.remove(position);
                mCardAdapter.notifyDataSetChanged();
                String key = card.getKey();
                strdata.edit().remove(key).apply();
                imagedata.edit().remove(key).apply();
            }
        });


    }



}


