package android.lifeistech.com.memo;

import android.graphics.Bitmap;

/**
 * Created by suemitsukyo on 2017/08/24.
 */

public class Card {
    public Bitmap image;
    public String tittle;
    public String content;
    public int likeNum = 0;
    public String fileName;

    public String meaning;

    public Card(Bitmap image, String tittle, String fileName) {
        this.image = image;
        this.tittle = tittle;
        this.fileName = fileName;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getKey() {
        return fileName;
    }

}
