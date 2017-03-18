package edu.csb.cs.cs185.jordanang.habittracker.Intro;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntroFragment;

import edu.csb.cs.cs185.jordanang.habittracker.MainActivity;
import edu.csb.cs.cs185.jordanang.habittracker.R;

/**
 * Created by Jordan Ang on 3/17/2017.
 */

public class AppIntro extends com.github.paolorotolo.appintro.AppIntro{

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        super.init(savedInstanceState);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("Title", "This is where the description would go if I had one", R.drawable.pencil, Color.parseColor("#ffa726")));
        addSlide(AppIntroFragment.newInstance("Title", "This is where the description would go if I had one", R.drawable.pencil, Color.parseColor("#3F51B5")));
        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);
        setVibrateIntensity(30);

        setFlowAnimation();
    }

        @Override
        public void onSkipPressed(Fragment currentFragment) {
            super.onSkipPressed(currentFragment);
            // Do something when users tap on Skip button.
        }

        @Override
        public void onDonePressed(Fragment currentFragment) {
            super.onDonePressed(currentFragment);
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent);
        }

        @Override
        public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
            super.onSlideChanged(oldFragment, newFragment);
            // Do something when the slide changes.
        }
}
