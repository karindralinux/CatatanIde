package org.ngodingo.catatanide_android.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.ngodingo.catatanide_android.R;

/**
 * Created by Karindra Linux on 31/12/2017.
 */

public class BaseHolder extends RecyclerView.ViewHolder {
    //fungsi BaseHolder untuk inisialisasi view untuk item listnya

    public TextView txt_waktu;
    public TextView txt_judul;
    public CardView card_item_note;

    public BaseHolder(View itemView) {
        super(itemView);
        txt_waktu = (TextView) itemView.findViewById(R.id.txt_waktu);
        txt_judul = (TextView) itemView.findViewById(R.id.txt_judul);
        card_item_note = (CardView) itemView.findViewById(R.id.card_item_note);
    }


}
