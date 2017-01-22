package boilermake.swollmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import static boilermake.swollmate.MainActivity.me;

public class UserProfileActivity extends AppCompatActivity {

    boolean gainMuscle, looseWeight, sports, leisure;
    boolean beginner, intermediate, expert, advance;
    String userGender;

    CheckBox gainMuscleGoal, leisureGoal, looseWeightGoal, sportsGoal;
    CheckBox beginnerLevel, expertLevel, intermediateLevel;
    TextView name;
    EditText age, bio;
    Button next;
    private RadioGroup radioGroup;
    private RadioButton male, female, other;
    private DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name = (TextView) findViewById(R.id.Name_Of_Person);
        setTitle("Profile");

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

        readFromFirebase();

        age = (EditText) findViewById(R.id.Age_Int_Only);
        bio = (EditText) findViewById(R.id.BioForPerson);

        next = (Button) findViewById(R.id.buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeToFirebase("age", age.getText().toString());
                writeToFirebase("bio", bio.getText().toString());
                String goals = "";
                if (gainMuscleGoal.isChecked()) {
                    if (goals.isEmpty()) {
                        goals = "Gain Muscle";
                    } else {
                        goals = goals + ", Gain Muscle";
                    }
                }
                if (leisureGoal.isChecked()) {
                    if (goals.isEmpty()) {
                        goals = "Leisure";
                    } else {
                        goals = goals + ", Leisure";
                    }
                }
                if (looseWeightGoal.isChecked()) {
                    if (goals.isEmpty()) {
                        goals = "Loose Weight";
                    } else {
                        goals = goals + ", Loose Weight";
                    }
                }
                if (sportsGoal.isChecked()) {
                    if (goals.isEmpty()) {
                        goals = "Sports";
                    } else {
                        goals = goals + ", Sports";
                    }
                }
                writeToFirebase("goals", goals);

                Intent intent = new Intent(UserProfileActivity.this, DiscoverActivity.class);
                startActivity(intent);
            }
        });

        beginnerLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (beginnerLevel.isChecked()) {
                    beginner = true;
                    writeToFirebase("skill", "Beginner");
                }
                intermediateLevel.setChecked(false);
                expertLevel.setChecked(false);
            }
        });

        intermediateLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intermediateLevel.isChecked()) {
                    intermediate = true;
                    writeToFirebase("skill", "Intermediate");
                }
                beginnerLevel.setChecked(false);
                expertLevel.setChecked(false);
            }
        });

        expertLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expertLevel.isChecked()) {
                    expert = true;
                    writeToFirebase("skill", "Expert");
                }
                beginnerLevel.setChecked(false);
                intermediateLevel.setChecked(false);
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (male.isPressed()) {
                    userGender = "male";
                    writeToFirebase("gender", "Male");
                }
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (female.isPressed()) {
                    userGender = "female";
                    writeToFirebase("gender", "Female");
                }
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (other.isPressed()) {
                    userGender = "other";
                    writeToFirebase("gender", "Other");
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

                if (key.equals("firstName")) {
                    name.setText(value);
                }

                if (key.equals("lastName")) {
                    name.setText(name.getText() + " " + value);
                }

                if (key.equals("picURL")) {
                    Picasso.with(UserProfileActivity.this).load(value).into((ImageView) findViewById(R.id.my_pic));
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

    public void writeToFirebase(String child, String value) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("users").child(me.uID).child(child).setValue(value);
    }

}
