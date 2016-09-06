package com.example.enterprise.progressbar_custom_drawable;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.os.Handler;


public class MainActivity extends Activity {
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the widgets reference from XML layout
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        final Button btn = (Button) findViewById(R.id.btn);
        final TextView tv = (TextView) findViewById(R.id.tv);
        final ProgressBar pb_default = (ProgressBar) findViewById(R.id.pb_default);
        final ProgressBar pb_drawable = (ProgressBar) findViewById(R.id.pb_drawable);

        /*
            public void setProgressDrawable (Drawable d)
                Define the drawable used to draw the progress bar in progress mode.

                Parameters
                d : the new drawable
        */

        // Set progress bar custom drawable programmatically
        // Uncomment the below line to activate it
        //pb_drawable.setProgressDrawable(getDrawable(R.drawable.progressbar_states));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the progress status zero on each button click
                progressStatus = 0;

                // Start the lengthy operation in a background thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(progressStatus < 100){
                            // Update the progress status
                            progressStatus +=1;

                            // Try to sleep the thread for 20 milliseconds
                            try{
                                Thread.sleep(20);
                            }catch(InterruptedException e){
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    pb_default.setProgress(progressStatus);
                                    pb_drawable.setProgress(progressStatus);
                                    // Show the progress on TextView
                                    tv.setText(progressStatus+"");
                                    // If task execution completed
                                    if(progressStatus == 100){
                                        // Set a message of completion
                                        tv.setText("Operation completed.");
                                    }
                                }
                            });
                        }
                    }
                }).start(); // Start the operation
            }
        });
    }
}
