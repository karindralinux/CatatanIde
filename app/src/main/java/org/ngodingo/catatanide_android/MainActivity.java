package org.ngodingo.catatanide_android;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;

import org.ngodingo.catatanide_android.adapter.Adapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.btn_add_note)
    FloatingActionButton btnAddNote;

    @BindView(R.id.list_note)
    RecyclerView listNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //setting recyclerview
        //pakai grid layout dengan 2 column
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        listNote.setLayoutManager(gridLayoutManager);

        //untuk ngeload item listnya
        loadItem();

        btnAddNote.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItem();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                loadItem();
                break;
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_note:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void loadItem() {
        //butuh SQLite sebagai database list
        SQLiteNote db_note = new SQLiteNote(this);
        //buat Adapter yang nantinya akan jadi Adapter RecyclerView nya
        Adapter adapter = new Adapter(this, db_note.getData());
        listNote.setAdapter(adapter);
        //kalau udah sekarang lanjut ke AddActivity.java
    }

}
