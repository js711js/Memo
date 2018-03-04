package android.lifeistech.com.memo;

import android.graphics.Bitmap;

/**
 * Created by suemitsukyo on 2017/08/24.
 */

public class Card {
    public Bitmap image;
    public String title;
    public String content;
    public int likeNum = 0;
    public String key;

    public String meaning;

    public Card(Bitmap image, String title, String key) {
        this.image = image;
        this.title = title;
        this.key = key;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getKey() {
        return key;
    }

}
