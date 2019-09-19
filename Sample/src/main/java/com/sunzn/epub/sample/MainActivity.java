package com.sunzn.epub.sample;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.sunzn.epub.library.EpubUtils;
import com.sunzn.epub.library.FileUtils;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button butRead, butAllRead;
    private EditText etBookName;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        butRead = findViewById(R.id.but_read);
        butAllRead = findViewById(R.id.but_allread);
        etBookName = findViewById(R.id.et_bookname);

        iv = findViewById(R.id.iv);

        butRead.setOnClickListener(this);
        butAllRead.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_read: {
//                BookModel bookModel = ReadEpubHeadInfo.getePubBook("/sdcard/epub/" + etBookName.getText().toString() + ".epub");
                String cover = EpubUtils.createCover("/sdcard/epub/" + etBookName.getText().toString() + ".epub", "/sdcard/aaaaa/", "xxx.png");

                if (cover != null) {
                    iv.setImageBitmap(BitmapFactory.decodeFile(cover));
                    Log.i("epub", (cover != null ? "有封面  " : "无封面  ") + "  书封面图片路径：" + cover);
                }
            }

            break;
            case R.id.but_allread:
                List<File> vector = FileUtils.listFilesInDir("/sdcard/epub/");
                for (int i = 0; i < vector.size(); i++) {
                    final String filePath = vector.get(i).getPath();
//                    String bookModel = EpubUtils.getePubBook(filePath);

//                    if (bookModel != null) {
//                        Log.i("epub", (bookModel.getCover() != null ? "有封面  " : "无封面  ") + bookModel.getName() + "  书封面图片路径：" + bookModel.getCover());
//                    }
                }
                break;

            default:
                break;
        }
    }

}
