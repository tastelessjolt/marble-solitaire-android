package com.example.harshithg.marblesolitaire;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BoardView extends View {

    int[][] board_structure;
    int size;

    MarbleView[][] marbles;

    // Selected marble
    MarbleView last_marble;

    // Possible moves for the current marble
    HashSet<MarbleView> highlighted;

    Bitmap boardBit;
    Rect bounds;

    Paint mPaint;
    Context context;

    public BoardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        board_structure = new int[][]{
                {0, 0, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 0, 0},
        };

        bounds = new Rect(0, 0, 0, 0);
        boardBit = BitmapFactory.decodeResource(getResources(), R.drawable.marble_solitaire);

        highlighted = new HashSet<>();

        mPaint = new Paint();
    }

    public void setMarbles(MarbleView[][] marbles) {
        this.marbles = marbles;
    }

    int check_status() {
        int count = 0;
        int adjacent_count = 0;
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (board_structure[i][j] == 1)
                {
                    if (!marbles[i][j].hidden) {
                    count ++;
                    adjacent_count += num_neighbours(i, j);
                }
                }
            }
        }

        adjacent_count /= 2;

        if (count == 1)
            return 1;

        if (adjacent_count == 0)
            return -1;

        return 0;
    }

    int num_neighbours (int x, int y) {
        int arr_x[] = {1, 0, -1, 0};
        int arr_y[] = {0, -1, 0, 1};

        int count = 0;
        MarbleView curr;
        for (int i = 0; i < 4; i++) {
            if (in_between(x + arr_x[i], 0, 7) && in_between(y + arr_y[i], 0, 7) ) {
                curr = marbles[x + arr_x[i]][y + arr_y[i]];
                if (curr != null && !curr.hidden)
                    count++;
            }
        }
        return count;
    }

    boolean in_between (int a, int i, int j) {
        return a >= i && a < j;
    }

    int move(int x, int y) {

        int arr_x[] = {2, 0, -2, 0};
        int arr_y[] = {0, -2, 0, 2};

        // First click
        // Stores the possible next positions
        if ( highlighted.isEmpty() ) {
            if (marbles[x][y].hidden) {
                return 0;
            }

            MarbleView target = null;
            MarbleView middle = null;
            for (int i = 0; i < 4; i++) {
                if (in_between(x + arr_x[i], 0, 7) && in_between(y + arr_y[i], 0, 7)) {
                    target = marbles[x + arr_x[i]][y + arr_y[i]];
                    middle = marbles[x + arr_x[i]/2][y + arr_y[i]/2];
                    if (middle != null && !middle.hidden) {
                        if (target != null && target.hidden) {
                            highlighted.add(target);
                            target.highlight = true;
                            target.select();
                        }
                    }
                }
            }
            last_marble = marbles[x][y];
        }

        // Second Click
        // Checks if the possible position is clicked
        else {
            for (MarbleView mar: highlighted) {
                if (mar.xpos == x && mar.ypos == y) {
                    if (mar.highlight) {
                        mar.highlight = false;
                        mar.show_img();

                        last_marble.hide_img();
                        marbles[(last_marble.xpos + x) / 2][(last_marble.ypos + y)/2].hide_img();
                    }
                    else {
                        mar.unselect();
                    }
                }
                else {
                    mar.unselect();
                }
            }
            highlighted.clear();

            int stat = check_status();
            if (stat > 0 /* win */) {
                Toast.makeText(context, "Congrats! You finished it!", Toast.LENGTH_SHORT).show();
            }
            else if (stat < 0 /* lose */) {
                Toast.makeText(context, "Sorry, you're out of moves :(. Try again. Fighting! :)", Toast.LENGTH_SHORT).show();
                reset();
            }
        }

        return 0;
    }

    public void reset(){
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (board_structure[i][j] == 1)
                {
                    marbles[i][j].show_img();
                }
            }
        }
        marbles[3][3].hide_img();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        bounds.right = getMeasuredWidth();
        bounds.bottom = bounds.right;

        canvas.drawBitmap(boardBit, null, bounds, mPaint);
    }

}
