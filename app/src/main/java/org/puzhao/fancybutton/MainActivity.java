package org.puzhao.fancybutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.puzhao.awesomebutton.AwesomeButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AwesomeButton awesomeButton = (AwesomeButton) findViewById(R.id.awesome_button);
        awesomeButton.setButtonClickInterface(new AwesomeButton.ButtonClickInterface() {
            @Override
            public View.OnClickListener onButton1Click() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "button1", Toast.LENGTH_SHORT).show();
                    }
                };
            }

            @Override
            public View.OnClickListener onButton2Click() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "button2", Toast.LENGTH_SHORT).show();
                    }
                };
            }

            @Override
            public View.OnClickListener onButton3Click() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "button3", Toast.LENGTH_SHORT).show();
                    }
                };
            }
        });
    }


}
