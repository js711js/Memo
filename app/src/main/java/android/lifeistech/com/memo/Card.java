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

    public String meaning;

    public Card(Bitmap image, String tittle) {
        this.image = image;
        this.tittle = tittle;
    }

}
