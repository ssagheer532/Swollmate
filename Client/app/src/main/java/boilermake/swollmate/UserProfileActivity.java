package boilermake.swollmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UserProfileActivity extends AppCompatActivity {

    CheckBox gainMuscleGoal, leisureGoal, looseWeightGoal, sportsGoal;
    CheckBox beginnerLevel, expertLevel, intermediateLevel;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;


    @Override
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        gainMuscleGoal = (CheckBox) findViewById(R.id.Gain_Muscle);
        leisureGoal = (CheckBox) findViewById(R.id.Leisure);
        looseWeightGoal = (CheckBox) findViewById(R.id.Loose_Weight);
        sportsGoal = (CheckBox) findViewById(R.id.Sports);

        beginnerLevel = (CheckBox) findViewById(R.id.Beginner);
        expertLevel = (CheckBox) findViewById(R.id.Expert);
        intermediateLevel = (CheckBox) findViewById(R.id.Intermediate);


    }*/

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioGroup);

        int selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton) findViewById(selectedId);


    }
}
