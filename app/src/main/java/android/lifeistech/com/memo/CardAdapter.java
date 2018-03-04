package android.lifeistech.com.memo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by suemitsujun on 2017/08/24.
 */

public class CardAdapter extends ArrayAdapter<Card> {

    List<Card> mCards;


    public CardAdapter(Context context, int layoutResourceId, List<Card> objects) {
        super(context, layoutResourceId, objects);
        mCards = objects;
    }

    public interface OnListClickListener {
        void OnListClick(Bitmap image);
    }

    OnListClickListener mListener;

    @Override
    public int getCount() {
        return mCards.size();
    }

    @Override
    public Card getItem(int position) {
        return mCards.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHoler viewHoler;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card, null);
            viewHoler = new ViewHoler(convertView);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }

        final Card item = getItem(position);

        if (item != null) {
            //ここでImageViewとかTextViewに値をセット
            // comment

            viewHoler.titleTextView.setText(item.title);
            viewHoler.iconImageView.setImageBitmap(item.image);
        }

        return convertView;

    }


    private class ViewHoler {
        ImageView iconImageView;
        TextView titleTextView;
        View itemView;

        public ViewHoler(View view) {
            iconImageView = (ImageView) view.findViewById(R.id.icon);
            titleTextView = (TextView) view.findViewById(R.id.title_textview);
            itemView = view;
        }


    }

    public void setmListener(OnListClickListener listener) {
        this.mListener = listener;
    }
}


