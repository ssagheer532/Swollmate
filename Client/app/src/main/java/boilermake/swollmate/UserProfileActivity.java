package boilermake.swollmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

public class UserProfileActivity extends AppCompatActivity  {

    CheckBox gainMuscleGoal, leisureGoal, looseWeightGoal, sportsGoal;
    CheckBox beginnerLevel, expertLevel, intermediateLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        gainMuscleGoal = (CheckBox) findViewById(R.id.Gain_Muscle);
        leisureGoal = (CheckBox) findViewById(R.id.Leisure);
        looseWeightGoal = (CheckBox) findViewById(R.id.Loose_Weight);
        sportsGoal = (CheckBox) findViewById(R.id.Sports);

        beginnerLevel = (CheckBox) findViewById(R.id.Beginner);
        expertLevel = (CheckBox) findViewById(R.id.Expert);
        intermediateLevel = (CheckBox) findViewById(R.id.Intermediate);


    }


}
