package ru.nsu.fit.modao.myStorage;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

public class HelpFunction {
    public static void startNewActivity(Context context, Class<?> cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public interface DelayCallback{
        void afterDelay();
    }

    public static void delay(long secs, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 1000);
    }
}
