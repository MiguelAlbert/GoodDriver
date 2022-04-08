package albert.miguel.gooddriver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity implements Animation.AnimationListener{

    TextView textView21,textView18;
    Animation animFadein1,animFadein2;
    private ImageView imageView21;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_welcome);

        imageView21= (ImageView)findViewById(R.id.imageView21);
        Animation zoomin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        imageView21.startAnimation(zoomin);

        textView21 = (TextView) findViewById(R.id.textView21);
        textView21.setVisibility(View.INVISIBLE);
        textView18 = (TextView) findViewById(R.id.textView18);
        textView18.setVisibility(View.INVISIBLE);
// load the animation fadein
        animFadein1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadein1.setAnimationListener(this);
        animFadein2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadein2.setAnimationListener(this);
        // start the animation
        textView21.setVisibility(View.VISIBLE);
        textView21.startAnimation(animFadein1);
        // Duration of wait
        //splash screen will be shown for 2 seconds
        int SPLASH_DISPLAY_LENGTH1 = 500;
        new Handler().postDelayed(() -> {
            // start the animation
            textView18.setVisibility(View.VISIBLE);
            textView18.startAnimation(animFadein2);
        }, SPLASH_DISPLAY_LENGTH1);

        // Duration of wait
        //splash screen will be shown for 2 seconds
        int SPLASH_DISPLAY_LENGTH = 4000;
        new Handler().postDelayed(() -> {

            Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animFadein1) {
            //Toast.makeText(getApplicationContext(), "Animation Stopped", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
