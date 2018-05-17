package org.ngodingo.catatanide_android;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {

    @BindView(R.id.txt_judul)
    TextView txt_judul;

    @BindView(R.id.txt_isi)
    TextView txt_isi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);

        txt_judul.setText(getIntent().getStringExtra("judul"));
        txt_isi.setText(getIntent().getStringExtra("isi"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //buat if ketika diisi data dia akan nyimpen
        if (!txt_judul.getText().toString().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss, dd-MM-yyyy");
            String waktu = sdf.format(new Date());
            SQLiteNote db_note = new SQLiteNote(this);
            db_note.addData(waktu, txt_judul.getText().toString(),txt_isi.getText().toString());
        }
    }
}
