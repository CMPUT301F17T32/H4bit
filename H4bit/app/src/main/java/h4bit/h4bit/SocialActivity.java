package h4bit.h4bit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by bhlewka on 11/6/17.
 */

public class SocialActivity extends AppCompatActivity {

    private User user;
    private static final String FILENAME = "localsave.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        // this can just hang because it isnt a goal for part4

        // Init the buttons and text search bar
        Button habitsButton = (Button) findViewById(R.id.habitsButton);
        Button historyButton = (Button) findViewById(R.id.historyButton);

        habitsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                habitsTab();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                historyTab();
            }
        });
    }

    public void habitsTab(){
        Intent intent = new Intent(this, MainHabitActivity.class);
        startActivity(intent);
        finish();
    }

    public void historyTab(){
        Intent intent = new Intent(this, HabitHistoryActivity.class);
        startActivity(intent);
        finish();
    }
}
