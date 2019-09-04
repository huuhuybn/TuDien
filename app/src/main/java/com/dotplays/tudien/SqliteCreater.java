package com.dotplays.tudien;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SqliteCreater {

    private Context context;

    // đường dẫn lưu file db
    private String PATH = "/data/data/com.dotplays.tudien/databases/";

    // tên file db
    private String DB_NAME = "dict_hh.db";

    private SQLiteDatabase sqLiteDatabase;

    public static final String ID = "id";
    public static final String WORD = "word";
    public static final String HTML = "html";
    public static final String DESCRIPTION = "description";
    public static final String PRONOUNCE = "pronounce";

    // tên bảng từ điển anh-việt (xem tên bảng bằng phần mềm Sqlite Browser)
    public static final String AV_TABLE = "av";


    public SqliteCreater(Context context) {
        this.context = context;
    }

    public List<Word> search(String text) {

        List<Word> words = new ArrayList<>();


        // câu lệnh LIKE để tìm kiếm từ tương tự text nhập vào

        String query = "SELECT * FROM " + AV_TABLE + " LIKE " + WORD + "= '" + text + "'";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor != null) {

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();

                while (cursor.isAfterLast()) {
                    Word word = new Word();

                    word.id = cursor.getInt(cursor.getColumnIndex(ID));

                    word.word = cursor.getString(cursor.getColumnIndex(WORD));
                    word.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    word.html = cursor.getString(cursor.getColumnIndex(HTML));
                    word.pronounce = cursor.getString(cursor.getColumnIndex(PRONOUNCE));
                    words.add(word);

                    cursor.moveToNext();
                }

                cursor.close();


            }
        }
        return words;
    }

    // mỗi lần mở ứng dụng đều kiểm tra xem đã copy chưa nếu copy rồi thì thôi.
    public boolean isExists() {

        boolean checkDB = false;
        try {
            String myPath = PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        } catch (SQLiteException e) {
        }
        return checkDB;
    }


    // copy file db tới thư mục data của dự án chứ ko truy vấn từ thư mục assets được
    public void copyDatabaseFromAssets() {

        Log.e("ABC", isExists() + "");

        if (isExists() == false) {

            try {
                InputStream mInput = context.getAssets().open(DB_NAME);
                String outFileName = PATH + DB_NAME;

                File dbFile = new File(outFileName);

                if (!dbFile.exists())
                    dbFile.createNewFile();

                OutputStream mOutput = new FileOutputStream(outFileName);
                byte[] mBuffer = new byte[2024];
                int mLength;
                while ((mLength = mInput.read(mBuffer)) > 0) {
                    mOutput.write(mBuffer, 0, mLength);
                }
                mOutput.flush();
                mOutput.close();
                mInput.close();

                this.sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(PATH + DB_NAME, null);

            } catch (Exception e) {

                Log.e("ABC", e.getMessage());

            }

        } else {
            this.sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(PATH + DB_NAME, null);
        }

    }
}
