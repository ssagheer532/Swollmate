package boilermake.swollmate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static boilermake.swollmate.MainActivity.buttons;

public class ChatListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT, 300);
        params.setMargins(0, 0, 0, 8);

        final Button b1 = new Button(this);
        b1.setLayoutParams(params);
        b1.setPadding(150, 0, 0, 0);
        b1.setText("Button");
        b1.setTextSize(18);
        ll.addView(b1);

        
    }

    public void addButton() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT, 300);
        params.setMargins(0, 0, 0, 8);

        final Button b1 = new Button(this);
        buttons.add(b1);
        b1.setLayoutParams(params);
        b1.setPadding(150, 0, 0, 0);
        b1.setText("Button");
        b1.setTextSize(18);
        ll.addView(b1);
    }
}
