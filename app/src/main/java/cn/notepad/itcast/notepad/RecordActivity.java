package cn.notepad.itcast.notepad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.notepad.itcast.notepad.database.SQLiteHelper;
import cn.notepad.itcast.notepad.utils.DBUtils;

public class RecordActivity extends Activity implements View.OnClickListener {
    ImageView note_back, delete, note_save;
    TextView note_time, noteName;
    SQLiteHelper mSQLiteHelper;
    String id;
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        note_back = (ImageView) findViewById(R.id.note_back);
        note_time = (TextView) findViewById(R.id.tv_time);
        content = (EditText) findViewById(R.id.note_content);
        delete = (ImageView) findViewById(R.id.delete);
        note_save = (ImageView) findViewById(R.id.note_save);
        noteName = (TextView) findViewById(R.id.note_name);
        note_back.setOnClickListener(this);
        delete.setOnClickListener(this);
        note_save.setOnClickListener(this);
        initData();
    }

    protected void initData() {
        mSQLiteHelper = new SQLiteHelper(this);
        noteName.setText("添加记录");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        if (id != null) {
            noteName.setText("修改记录");
            content.setText(intent.getStringExtra("content"));
            note_time.setText(intent.getStringExtra("time"));
            note_time.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_back:
                finish();
                break;
            case R.id.delete:
                content.setText("");
                break;
            case R.id.note_save:
                String noteContent = content.getText().toString().trim();
                if (id != null) {
                    if (noteContent.length() > 0) {
                        if (mSQLiteHelper.updateData(id, noteContent, DBUtils.getTime())) {
                            showToast("修改成功");
                            setResult(2);
                            finish();
                        } else {
                            showToast("修改失败");
                        }
                    } else {
                        showToast("修改内容不能为空!");
                    }
                } else {
                    if (noteContent.length() > 0) {
                        if (mSQLiteHelper.insertData(noteContent, DBUtils.getTime())) {
                            showToast("保存成功");
                            setResult(2);
                            finish();
                        } else {
                            showToast("保存失败");
                        }
                    } else {
                        showToast("修改内容不能为空!");
                    }
                }
                break;
        }
    }
    public void showToast(String message){
        Toast.makeText(RecordActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}