package com.example.samplelooper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;

    Handler handler = new Handler();

    ProcessThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView );

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editText.getText().toString();
                Message message = Message.obtain();
                message.obj = input;

                thread.processThread.sendMessage(message);
            }
        });

        thread = new ProcessThread();
    }

    class ProcessThread extends Thread {
        ProcessHandler processThread = new ProcessHandler();

        @Override
        public void run() {
            Looper.prepare();
            Looper.loop();
        }
    }

    class ProcessHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            final String output = msg.obj + " from thread.";
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText(output);
                }
            });
        }
    }
}