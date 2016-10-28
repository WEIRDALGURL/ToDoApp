package com.meowisthetime.todoapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by sheamaynard on 10/24/16.
 */

public class AddTasksActivity extends AppCompatActivity {
    private EditText taskName;
    private EditText taskCategory;
    private EditText dueDate;
    private EditText dueTime;
    private Button loadImage;
    private EditText comments;
    private int index;
    Calendar myCalendar = Calendar.getInstance();
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskName = (EditText) findViewById(R.id.task_name);
        taskCategory = (EditText) findViewById(R.id.category_name);
        dueDate = (EditText) findViewById(R.id.date_due);
        dueTime = (EditText) findViewById(R.id.time_due);
        comments = (EditText) findViewById(R.id.commentBox);
        loadImage = (Button) findViewById(R.id.buttonLoadPicture);

        Intent intent = getIntent();
        index = intent.getIntExtra(MainActivity.TASK_INDEX, -1);

        taskName.setText(intent.getStringExtra(MainActivity.TASK_NAME));
        dueDate.setText(intent.getStringExtra(MainActivity.TASK_DUE));
        dueTime.setText(intent.getStringExtra(MainActivity.TASK_TIME_DUE));
        comments.setText(intent.getStringExtra(MainActivity.TASK_TEXT));


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }

        };

        dueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddTasksActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateLabelTime();

            }
        };

        dueTime.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(AddTasksActivity.this, time, myCalendar
                        .get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show();
            }
        });

    }

    private void updateLabelDate() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dueDate.setText(sdf.format(myCalendar.getTime()));
    }


    private void updateLabelTime() {

        String myFormat = "h:mm a"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        dueTime.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.submit_btn) {

            Intent intent = getIntent();
            intent.putExtra(MainActivity.TASK_INDEX, index);
            intent.putExtra(MainActivity.TASK_NAME, taskName.getText().toString());
            intent.putExtra(MainActivity.TASK_DUE, dueDate.getText().toString());
            intent.putExtra(MainActivity.TASK_TIME_DUE, dueTime.getText().toString());
            intent.putExtra(MainActivity.TASK_TEXT, comments.getText().toString());
            intent.putExtra(MainActivity.TASK_CATEGORY, taskCategory.getText().toString());
            setResult(RESULT_OK, intent);
            finish();

//            Intent intent = new Intent(AddTasksActivity.this, MainActivity.class);
//            intent.putExtra(MainActivity.TASK_NAME, taskName.getText().toString());
//            intent.putExtra(MainActivity.TASK_CATEGORY, taskCategory.getText().toString());
////            intent.putExtra("Category", taskCategory.getText().toString());
////            //I gotta figure out how to get the date here
////            intent.putExtra("Due Date", myCalendar.getTime());
////            //I gotta figure out how to get the time here
//            intent.putExtra(MainActivity.TASK_DUE, dueDate.getText().toString());
////            intent.putExtra("Comments", comments.getText().toString());
////            //I gotta figure out how to get the photo here
////            intent.putExtra("Photo", imgDecodableString.toString());
//            intent.putExtra("Index", index);
//            setResult(RESULT_OK, intent);
//            finish();
//            startActivityForResult(intent, 1);
//            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.thumbnail1);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

}


