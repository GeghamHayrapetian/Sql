package com.example.sql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DataBaseHelper dataBaseHelper;
    private EditText name, surname, marks, id;
    private Button add, view, update, delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        add.setOnClickListener(this);
        update.setOnClickListener(this);
        view.setOnClickListener(this);
        delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.b_add:
                if (name.getText().toString().equals("") ||
                        surname.getText().toString().equals("") ||
                        marks.getText().toString().equals("")) {
                    showDialog("Error", "Fill all the fields");
                    break;
                }
                boolean insert = dataBaseHelper.insertData(name.getText().toString()
                        , surname.getText().toString(),
                        marks.getText().toString());
                if (insert == true) {
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "Data don`t inserted", Toast.LENGTH_SHORT).show();
                clear();
                break;
            case R.id.b_update:
                if (name.getText().toString().equals("") ||
                        surname.getText().toString().equals("") ||
                        marks.getText().toString().equals("") || id.getText().toString().equals("")) {
                    showDialog("Error", "Fill all the fields");
                    break;
                }
                boolean update = dataBaseHelper.updateData(id.getText().toString(), name.getText().toString()
                        , surname.getText().toString(),
                        marks.getText().toString());
                if (update == true) {
                    Toast.makeText(MainActivity.this, "Data update", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "Data don`t update", Toast.LENGTH_SHORT).show();
                clear();
                break;
            case R.id.b_delete:
                Integer deleted = dataBaseHelper.deleteById(id.getText().toString());
                if (deleted > 0) {
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(MainActivity.this, "Not deleted ", Toast.LENGTH_SHORT).show();
                clear();
                break;
            case R.id.b_view:
                Cursor res = dataBaseHelper.getAllData();
                if (res.getCount() == 0) {
                    showDialog("Error", "Nothing found");
                    break;

                }
                StringBuffer stringBuffer = new StringBuffer();
                while (res.moveToNext()) {
                    stringBuffer.append(DataBaseHelper.COl_1 + " " + res.getString(0) + '\n');
                    stringBuffer.append(DataBaseHelper.COl_2 + " " + res.getString(1) + '\n');
                    stringBuffer.append(DataBaseHelper.COl_3 + " " + res.getString(2) + '\n');
                    stringBuffer.append(DataBaseHelper.COl_4 + " " + res.getString(3) + "\n\n");
                }
                showDialog("Students", stringBuffer.toString());
                res.close();
                break;
            default:
                break;

        }


    }


    private void clear() {
        name.setText("");
        surname.setText("");
        marks.setText("");
        id.setText("");
    }


    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


    private void findViews() {
        dataBaseHelper = new DataBaseHelper(this);
        name = findViewById(R.id.et_name);
        id = findViewById(R.id.et_id);
        surname = findViewById(R.id.et_surname);
        marks = findViewById(R.id.et_marks);
        add = findViewById(R.id.b_add);
        view = findViewById(R.id.b_view);
        update = findViewById(R.id.b_update);
        delete = findViewById(R.id.b_delete);

    }
}
