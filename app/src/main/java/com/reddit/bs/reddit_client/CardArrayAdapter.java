package com.reddit.bs.reddit_client;

/**
 * Created by Gustavo Puche on 1/08/16.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardArrayAdapter  extends ArrayAdapter<Card> {
    private static final String TAG = "CardArrayAdapter";
    private List<Card> cardList = new ArrayList<Card>();

    static class CardViewHolder {
        ImageView thumbnail;
        TextView date;
        TextView title;
        TextView author;
        TextView score;
        TextView comments;

    }

    public CardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(Card object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Card getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.thumbnail = (ImageView) row.findViewById(R.id.thumbnail);
            viewHolder.date = (TextView) row.findViewById(R.id.date);
            viewHolder.title = (TextView) row.findViewById(R.id.title);
            viewHolder.author = (TextView) row.findViewById(R.id.author);
            viewHolder.score = (TextView) row.findViewById(R.id.score);
            viewHolder.comments = (TextView) row.findViewById(R.id.comments);

            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Card card = getItem(position);
        String author = "Author: "+card.getAuthor();
        String comments = "Comments: "+card.getComments();
        String score = "Score: "+card.getScore();

        viewHolder.date.setText(card.getDate());
        viewHolder.title.setText(card.getTitle());
        viewHolder.author.setText(author);
        viewHolder.score.setText(score);
        viewHolder.comments.setText(comments);

        viewHolder.thumbnail.setImageBitmap(card.getImage());

        return row;
    }

    public List<Card> getCardList(){return cardList;}

    public void removeList(){cardList.clear();}

}
