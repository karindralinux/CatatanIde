package org.ngodingo.catatanide_android.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ngodingo.catatanide_android.AddActivity;
import org.ngodingo.catatanide_android.ItemList;
import org.ngodingo.catatanide_android.R;
import org.ngodingo.catatanide_android.SQLiteNote;
import org.ngodingo.catatanide_android.holder.BaseHolder;

import java.util.List;

/**
 * Created by Karindra Linux on 31/12/2017.
 */
//buat BaseHolder dulu
public class Adapter extends RecyclerView.Adapter<BaseHolder> {

    Context context;
    List<ItemList> list;

    public Adapter(Context context, List<ItemList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
        BaseHolder holder = new BaseHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, final int position) {
        holder.txt_waktu.setText(list.get(position).waktu);
        holder.txt_judul.setText(list.get(position).judul);

        holder.card_item_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddActivity.class);
                intent.putExtra("judul", list.get(position).judul);
                intent.putExtra("isi", list.get(position).isi);
                view.getContext().startActivity(intent);
            }
        });

        //untuk ngedelete item
        holder.card_item_note.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case DialogInterface.BUTTON_POSITIVE:
                                SQLiteNote db_note = new SQLiteNote(context);
                                db_note.deleteItemSelected(list.get(position).waktu);
                                db_note.getData();

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Mau dihapus?").setPositiveButton("Iya aja", dialogClickListener)
                        .setNegativeButton("Enggak sih", dialogClickListener).show();

                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
