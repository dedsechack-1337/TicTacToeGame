package com.example.tictactoegame;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;
    //player representation
        // 0 - X
        // 1 - O
        int activePlayer = 0;
        int[] gameState = {2,2,2,2,2,2,2,2,2};
        //State meaning
        //0 - X
        //1 - O
        //2 - Null
    int[][] winingPositions = {{0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}
        };

    public void playerTap(View view){
        if(!gameActive){
            gameReset(view);
            return;

        }
        ImageView img = (ImageView) view;
        int tappedImage = Integer.parseInt(img.getTag().toString());
        if(gameState[tappedImage]==2 ){
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);
            if(activePlayer==0){
                img.setImageResource(R.drawable.x);
                activePlayer=1;
                TextView status = findViewById(R.id.statusBar);
                status.setText("O's Turn - Tap To Play");
            }
            else{
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                TextView status = findViewById(R.id.statusBar);
                status.setText("X's Turn - Tap To Play");
            }

            img.animate().translationYBy(1000f).setDuration(300);
        }
        //checks if any player has won
        for(int[] winPosition :winingPositions){
            if(gameState[winPosition[0]]==gameState[winPosition[1]] && gameState[winPosition[1]]==gameState[winPosition[2]] &&
            gameState[winPosition[0]] != 2){
                //somebody has won! - Find out who
                gameActive = false;
                String winnerStr;
                if(gameState[winPosition[0]]==0){
                    winnerStr = "X has Won! - Tap Anywhere to Reset";
                }else{
                    winnerStr = "O has Won! - Tap Anywhere to Reset";
                }

                //Update the Status bar for winner announcement
                TextView status = findViewById(R.id.statusBar);
                status.setText(winnerStr);

            }
        }
        //Checks draw
        if (!gameActive) return;
        boolean draw = true;
        for (int state : gameState){
            if(state==2){
                draw = false;
                break;
            }
        }
        if (draw){
            gameActive = false;
            TextView status = findViewById(R.id.statusBar);
            status.setText("Game Draw! - Tap Anywhere to Reset");


        }

    }
    public void gameReset(View view){
        activePlayer = 0;
        gameActive = true;
        for(int i=0 ;i<gameState.length;i++){
            gameState[i] = 2;
        }
        ((ImageView)findViewById(R.id.c0)).setImageResource(0);
        ((ImageView)findViewById(R.id.c1)).setImageResource(0);
        ((ImageView)findViewById(R.id.c2)).setImageResource(0);
        ((ImageView)findViewById(R.id.c3)).setImageResource(0);
        ((ImageView)findViewById(R.id.c4)).setImageResource(0);
        ((ImageView)findViewById(R.id.c5)).setImageResource(0);
        ((ImageView)findViewById(R.id.c6)).setImageResource(0);
        ((ImageView)findViewById(R.id.c7)).setImageResource(0);
        ((ImageView)findViewById(R.id.c8)).setImageResource(0);
        ((TextView)findViewById(R.id.statusBar)).setText("X's Turn - Tap To Play");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}