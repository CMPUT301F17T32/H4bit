package h4bit.h4bit.Views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.io.FileNotFoundException;
import java.io.IOException;

import h4bit.h4bit.Models.Habit;
import h4bit.h4bit.Models.HabitEvent;
import h4bit.h4bit.Models.HabitEventList;
import h4bit.h4bit.Models.User;
import h4bit.h4bit.R;

/**
 * Created by benhl on 2017-10-29.
 */

public class DoHabitActivity extends AppCompatActivity {

    private Habit theHabit;
    private User user;
    private HabitEventList habitEventList;
    private EditText commentText;

    @Override
    protected void onStart(){   //@ben
        super.onStart();
        setContentView(R.layout.do_habit_activity);

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        Button doHabitButton = (Button) findViewById(R.id.doHabitButton);
        Button uploadImageButton = (Button) findViewById(R.id.uploadHabitPictureButton);
        ToggleButton locationToggle = (ToggleButton) findViewById(R.id.locationToggle);
        commentText = (EditText) findViewById(R.id.addCommentText);

        doHabitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                HabitEvent habitEvent = new HabitEvent(theHabit, commentText.getText().toString());
                habitEventList.addHabitEvent(habitEvent);
                // more
            }
        });

        //https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
        uploadImageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
