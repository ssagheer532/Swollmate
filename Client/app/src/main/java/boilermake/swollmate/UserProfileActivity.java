package boilermake.swollmate;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    boolean gainMuscle, looseWeight, sports, leisure;
    boolean beginner, intermediate, advance;
    String userGender;

    CheckBox gainMuscleGoal, leisureGoal, looseWeightGoal, sportsGoal;
    CheckBox beginnerLevel, expertLevel, intermediateLevel;
    TextView name;
    private RadioGroup radioGroup;
    private RadioButton male, female, other;
    private DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = (TextView) findViewById(R.id.Name_Of_Person);
        setTitle("Profile");

        readFromFirebase();
        gainMuscleGoal = (CheckBox) findViewById(R.id.Gain_Muscle);
        leisureGoal = (CheckBox) findViewById(R.id.Leisure);
        looseWeightGoal = (CheckBox) findViewById(R.id.Loose_Weight);
        sportsGoal = (CheckBox) findViewById(R.id.Sports);

        beginnerLevel = (CheckBox) findViewById(R.id.Beginner);
        expertLevel = (CheckBox) findViewById(R.id.Expert);
        intermediateLevel = (CheckBox) findViewById(R.id.Intermediate);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        other = (RadioButton) findViewById(R.id.other);

        gainMuscleGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gainMuscleGoal.isChecked()){
                    gainMuscle = true;
                }
            }
        });

        looseWeightGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (looseWeightGoal.isChecked()){
                    looseWeight = true;
                }
            }
        });

        sportsGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sportsGoal.isChecked()){
                    sports = true;
                }
            }
        });
        leisureGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (leisureGoal.isChecked()){
                    leisure = true;
                }
            }
        });

        beginnerLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (beginnerLevel.isChecked()){
                    beginner = true;
                }
            }
        });
        intermediateLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intermediateLevel.isChecked()){
                    intermediate = true;
                }
            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (male.isPressed()){
                    userGender = "male";
                }

            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (female.isPressed()){
                    userGender = "female";
                }
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (other.isPressed()){
                    userGender = "other";
                }
            }

        });
    }

    public void readFromFirebase() {
        String uID = MainActivity.me.uID;
        usersDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uID);

        ChildEventListener childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = (String) dataSnapshot.getKey();
                String value = (String) dataSnapshot.getValue().toString();

                if (key.equals("firstName") || key.equals("lastName")) {
                    name.setText(name.getText() + " " + value);
                }
                if (key.equals("picURL")) {
                    Picasso.with(UserProfileActivity.this).load(value).into((ImageView)findViewById(R.id.my_pic));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersDatabase.addChildEventListener(childListener);

    }


}
