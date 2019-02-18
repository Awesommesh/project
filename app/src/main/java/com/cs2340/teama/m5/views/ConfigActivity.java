package com.cs2340.teama.m5.views;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs2340.teama.m5.R;
import com.cs2340.teama.m5.models.GameDifficulty;
import com.cs2340.teama.m5.models.Player;
import com.cs2340.teama.m5.models.SkillType;
import com.cs2340.teama.m5.viewModels.ConfigViewModel;

public class ConfigActivity extends AppCompatActivity {

    private Spinner difficultySpinner;
    private EditText figher;
    private EditText pilot;
    private EditText trader;
    private EditText engineer;
    private Button startGame;
    private EditText nameGiven;
    private ConfigViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        viewModel = ViewModelProviders.of(this).get(ConfigViewModel.class);

        /*
        Grabbing the widgets
         */
        difficultySpinner = findViewById(R.id.difficulty_spinner);
        figher = findViewById(R.id.pilot_number);
        pilot = findViewById(R.id.fighter_number);
        trader = findViewById(R.id.trader_number);
        engineer = findViewById(R.id.editText5);
        startGame = (Button) findViewById(R.id.begin_game_button);
        nameGiven = findViewById(R.id.editText);

        /*
        Set up the spinner
         */

        ArrayAdapter<String> difficulty_adapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, GameDifficulty.values());
        difficulty_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficulty_adapter);


    }



    public void onClick(View view) {
        Log.d("Edit", "Add Player Pressed");
        boolean ready;
        int pil = 0;
        int fight = 0;
        int trad = 0;
        int engin = 0;
        try {
            pil = Integer.parseInt(pilot.getText().toString());
            fight = Integer.parseInt(figher.getText().toString());
            trad = Integer.parseInt(trader.getText().toString());
            engin = Integer.parseInt(engineer.getText().toString());
            ready = viewModel.calculatePoints(pil, fight, trad, engin);
        } catch (Exception e) {
            Log.d("Error", "Inappropriate Input");
            ready = false;
        }

        if (!ready) {
            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast();
                }
            });
        } else {

            GameDifficulty diff = (GameDifficulty) difficultySpinner.getSelectedItem();

            Player player = new Player(nameGiven.getText().toString());

            player.incrementSkill(SkillType.FIGHTER, fight);
            player.incrementSkill(SkillType.ENGINEER, engin);
            player.incrementSkill(SkillType.TRADER, trad);
            player.incrementSkill(SkillType.PILOT, pil);


            Log.d("Edit", "Got new player" + player);

            viewModel.addPlayer(player);
            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openBlankActivity();
                }
            });
        }

    }
    public void openBlankActivity() {
        Intent intent = new Intent(this, BlankActivity.class);
        startActivity(intent);
    }
    public void showToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        toast.show();
    }

}
