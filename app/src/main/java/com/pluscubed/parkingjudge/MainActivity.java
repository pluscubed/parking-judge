package com.pluscubed.parkingjudge;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    public static class MainActivityFragment extends Fragment {

        private EditText mCarWidth;
        private EditText mBetweenWheels;
        private EditText mBoxWidth;
        private EditText mBoxLength;
        private EditText mLeftFromCurb;
        private EditText mRightFromCurb;
        private EditText mClosestToEdge;
        private EditText mCarLength;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view =  inflater.inflate(R.layout.fragment_main, container, false);
            mCarLength = (EditText) view.findViewById(R.id.fragment_main_car_length);
            mCarWidth = (EditText) view.findViewById(R.id.fragment_main_car_width);
            mBetweenWheels = (EditText) view.findViewById(R.id.fragment_main_between_wheels);
            mBoxWidth = (EditText) view.findViewById(R.id.fragment_main_dimensions_box_width);
            mBoxLength = (EditText) view.findViewById(R.id.fragment_main_dimensions_box_length);
            mLeftFromCurb = (EditText) view.findViewById(R.id.fragment_main_leftfromcurb);
            mRightFromCurb = (EditText) view.findViewById(R.id.fragment_main_rightfromcurb);
            mClosestToEdge = (EditText) view.findViewById(R.id.fragment_main_closestfromedge);
            final TextView score = (TextView) view.findViewById(R.id.fragment_main_score);
            final Button calculate = (Button) view.findViewById(R.id.fragment_main_calculate_button);

            calculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        double boxLength = Double.parseDouble(String.valueOf(mBoxLength.getText()));
                        double boxWidth = Double.parseDouble(String.valueOf(mBoxWidth.getText()));
                        double betweenWheels = Double.parseDouble(String.valueOf(mBetweenWheels.getText()));
                        double carLength = Double.parseDouble(String.valueOf(mCarLength.getText()));
                        double carWidth = Double.parseDouble(String.valueOf(mCarWidth.getText()));
                        double leftFromCurb = Double.parseDouble(String.valueOf(mLeftFromCurb.getText()));
                        double rightFromCurb = Double.parseDouble(String.valueOf(mRightFromCurb.getText()));
                        double closestToEdge = Double.parseDouble(String.valueOf(mClosestToEdge.getText()));

                        double angle = Math.toDegrees(Math.atan(Math.abs(leftFromCurb - rightFromCurb) / betweenWheels));
                        score.setText("Angle: " + angle);

                        double averageOffsetVertical = (leftFromCurb+rightFromCurb)/2;
                        double longerPartOfY = carWidth*Math.abs(leftFromCurb - rightFromCurb) / betweenWheels;
                        double averageOffsetHorizontal = longerPartOfY/2+closestToEdge;

                        double centerX = averageOffsetHorizontal+carLength/2;
                        double centerY = averageOffsetVertical+carWidth/2;

                        double centerSupposedX = boxLength/2;
                        double centerSupposedY = boxWidth/2;

                        double distanceFromPerfectCenter = Math.sqrt(Math.pow(centerX-centerSupposedX,2)+Math.pow(centerY-centerSupposedY,2));
                        score.append("\nDistance From Perfect Center: "+distanceFromPerfectCenter);

                        score.append("\n\nFinal Score: "+ ((int)(1000-angle*10-distanceFromPerfectCenter*10)) +"/1000");
                    }catch(NumberFormatException exception){
                        score.setText(exception.getMessage());
                    }
                }
            });

            return view;
        }
    }
}
