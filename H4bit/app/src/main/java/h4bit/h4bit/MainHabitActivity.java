package h4bit.h4bit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * habitsButton, socialButton, historyButton, addButton, habitsList
 * I suggest the habits button do nothing because it will only take us to the
 * screen we are already on (this one)
 */
public class MainHabitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_habit);

        Button historyButton = (Button) findViewById(R.id.historyButton);
        Button socialButton = (Button) findViewById(R.id.socialButton);

        // The habitsButton should do nothing on this screen
        // (Because it takes us to where we already are)

        // This is the listener for the historyButton press
        //
        newCounter.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){


    }
}
