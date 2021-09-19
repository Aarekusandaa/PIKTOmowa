package com.example.piktomowafraglist;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
//import android.support.design.widget.TextInputLayout;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.PendingIntent.getActivity;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class addActivity extends AppCompatActivity {

    Button btnOK;
    ImageView CameraPicture;
    EditText NameText;
    String pathToFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        Intent catIntent = getIntent();
        final int catNum = catIntent.getIntExtra("category", 1);

        if (Build.VERSION.SDK_INT >= 23){
            requestPermissions(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        dispatchPictureTakerAction();

        btnOK = findViewById(R.id.AddButton);
        CameraPicture = findViewById(R.id.picture);
        NameText = findViewById(R.id.AddName);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = NameText.getText().toString();
                Intent add_result = new Intent();
                add_result.putExtra("category", catNum);
                final SingleItem result = new SingleItem(name, null, true, pathToFile);

                if (name.equals("")){
                    Toast.makeText(getApplicationContext(), "Musisz wpisać nazwę!",
                            Toast.LENGTH_SHORT).show();
                }else if (catNum == 1){
                    saveTasksToJson(SelectedCategoryFragment.AlphabetGson, result,
                            SelectedCategoryFragment.ALPHABET_JSON, catNum);
                    setResult(RESULT_OK, add_result);
                    finish();
                }else if (catNum == 2){
                    saveTasksToJson(SelectedCategoryFragment.NumbersGson, result,
                            SelectedCategoryFragment.NUMBERS_JSON, catNum);
                    setResult(RESULT_OK, add_result);
                    finish();
                }else if (catNum == 3){
                    saveTasksToJson(SelectedCategoryFragment.ColorsGson, result,
                            SelectedCategoryFragment.COLORS_JSON, catNum);
                    setResult(RESULT_OK, add_result);
                    finish();
                }else if (catNum == 4){
                    saveTasksToJson(SelectedCategoryFragment.ShapesGson, result,
                            SelectedCategoryFragment.SHAPES_JSON, catNum);
                    setResult(RESULT_OK, add_result);
                    finish();
                }else if (catNum == 5){
                    saveTasksToJson(SelectedCategoryFragment.DirectionsGson, result,
                            SelectedCategoryFragment.DIRECTIONS_JSON, catNum);
                    setResult(RESULT_OK, add_result);
                    finish();
                }else if (catNum == 6){
                    saveTasksToJson(SelectedCategoryFragment.QuestionsGson, result,
                            SelectedCategoryFragment.QUESTIONS_JSON, catNum);
                    setResult(RESULT_OK, add_result);
                    finish();
                }else if (catNum == 7){
                    saveTasksToJson(SelectedCategoryFragment.PersonsGson, result,
                            SelectedCategoryFragment.PERSONS_JSON, catNum);
                    setResult(RESULT_OK, add_result);
                    finish();
                }else if (catNum == 8){
                    saveTasksToJson(SelectedCategoryFragment.Wlasne_piktoGson, result,
                            SelectedCategoryFragment.WLASNE_PIKTO_JSON, catNum);
                    setResult(RESULT_OK, add_result);
                    finish();
                }else {
                    saveTasksToJson(SelectedCategoryFragment.Wlasne_piktoGson, result,
                            SelectedCategoryFragment.WLASNE_PIKTO_JSON, catNum);
                    setResult(RESULT_OK, add_result);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 3){
                if (resultCode == RESULT_OK){
                    Bitmap bitmap = PicUtils.decodePicture(pathToFile, 60, 60);
                    CameraPicture.setImageBitmap(bitmap);
            }else {
                    dispatchPictureTakerAction();
                }
        }
    }

    private void dispatchPictureTakerAction() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            photoFile = createPhotoFile();
            if(photoFile != null){
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(getApplicationContext(),
                        "com.example.piktomowafraglist.fileprovider", photoFile);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePicture, 3);
            }


        }
    }

    private File createPhotoFile() {
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("myLog", "Excep : " + e.toString());
        }
        return image;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    private void saveTasksToJson(Gson gson, SingleItem item, String fileJson, int category){

        ArrayList<SingleItem> list;
        if (category == 1){
            list = SelectedCategoryFragment.alphabet;
            list.add(item);
        }else if (category == 2){
            list = SelectedCategoryFragment.numbers;
            list.add(item);
        }else if (category == 3){
            list = SelectedCategoryFragment.colors;
            list.add(item);
        }else if (category == 4){
            list = SelectedCategoryFragment.shapes;
            list.add(item);
        }else if (category == 5){
            list = SelectedCategoryFragment.directions;
            list.add(item);
        }else if (category == 6){
            list = SelectedCategoryFragment.questions;
            list.add(item);
        }else if (category == 7){
            list = SelectedCategoryFragment.persons;
            list.add(item);
        }else if (category == 8){
            list = SelectedCategoryFragment.wlasne_pikto;
            list.add(item);
        }else {
            list = SelectedCategoryFragment.wlasne_pikto;
            list.add(item);
        }
        String listJson = gson.toJson(list);
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileJson, MODE_PRIVATE);
            FileWriter writer = new FileWriter(outputStream.getFD());
            writer.write(listJson);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
