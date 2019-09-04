package com.dotplays.tudien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtText;
    private RecyclerView lvList;


    private SqliteCreater sqliteCreater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtText = findViewById(R.id.edtText);
        lvList = findViewById(R.id.lvList);

        sqliteCreater = new SqliteCreater(this);

        // Đừng quên xin quyền ghi bộ nhớ trong file manifest em nhé

        sqliteCreater.copyDatabaseFromAssets();


    }

    public void search(View view) {

        String word = edtText.getText().toString().trim();

        // kiểm tra nếu người dùng chưa nhập gì thì dừng lại và thông báo lỗi
        if (word.isEmpty()) {
            edtText.setError("Vui lòng nhập dữ liệu !!!");
            return;
            // nếu chữ ko empty thì tiếp tục tìm kiếm và hiển thị danh sách kết quả lên list nếu có
        } else {


            List<Word> results = sqliteCreater.search(
                    word);

            Log.e("SIZE", results.size() + "");

        }


    }
}
