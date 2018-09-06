package com.example.harshithg.marblesolitaire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    BoardView boardView;
    MarbleView[][] marbles;

    HashMap<String, Integer> marbleViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boardView = findViewById(R.id.boardView);

        marbleViews = new HashMap<>();

        marbleViews.put("3,3", R.id.marble3_3);
        marbleViews.put("3,2", R.id.marble3_2);
        marbleViews.put("3,4", R.id.marble3_4);
        marbleViews.put("3,5", R.id.marble3_5);
        marbleViews.put("3,6", R.id.marble3_6);
        marbleViews.put("2,4", R.id.marble2_4);
        marbleViews.put("2,5", R.id.marble2_5);
        marbleViews.put("2,6", R.id.marble2_6);
        marbleViews.put("0,4", R.id.marble0_4);
        marbleViews.put("0,3", R.id.marble0_3);
        marbleViews.put("0,2", R.id.marble0_2);
        marbleViews.put("1,4", R.id.marble1_4);
        marbleViews.put("1,3", R.id.marble1_3);
        marbleViews.put("1,5", R.id.marble1_5);
        marbleViews.put("1,2", R.id.marble1_2);
        marbleViews.put("1,1", R.id.marble1_1);
        marbleViews.put("2,0", R.id.marble2_0);
        marbleViews.put("3,0", R.id.marble3_0);
        marbleViews.put("4,0", R.id.marble4_0);
        marbleViews.put("2,1", R.id.marble2_1);
        marbleViews.put("2,2", R.id.marble2_2);
        marbleViews.put("2,3", R.id.marble2_3);
        marbleViews.put("3,1", R.id.marble3_1);
        marbleViews.put("5,2", R.id.marble5_2);
        marbleViews.put("5,3", R.id.marble5_3);
        marbleViews.put("5,4", R.id.marble5_4);
        marbleViews.put("5,5", R.id.marble5_5);
        marbleViews.put("6,2", R.id.marble6_2);
        marbleViews.put("6,3", R.id.marble6_3);
        marbleViews.put("6,4", R.id.marble6_4);
        marbleViews.put("4,2", R.id.marble4_2);
        marbleViews.put("4,3", R.id.marble4_3);
        marbleViews.put("4,4", R.id.marble4_4);
        marbleViews.put("4,5", R.id.marble4_5);
        marbleViews.put("4,6", R.id.marble4_6);
        marbleViews.put("4,1", R.id.marble4_1);
        marbleViews.put("5,1", R.id.marble5_1);


        marbles = new MarbleView[7][7];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (boardView.board_structure[i][j] == 1) {
                    marbles[i][j] = findViewById(marbleViews.get(String.format("%d,%d", j, i)));
                    marbles[i][j].setParent(boardView);
                }
                else {
                    marbles[i][j] = null;
                }
            }
        }

        boardView.setMarbles (marbles);
    }


}
