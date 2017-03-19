package edu.csb.cs.cs185.jordanang.habittracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by Jordan Ang on 3/17/2017.
 */

public class AppIntro extends com.github.paolorotolo.appintro.AppIntro{

    static boolean firstTimeOpening = true;

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        super.init(savedInstanceState);

        getSupportActionBar().hide();

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("Create a habit", "Think of a habit you want to build! It can be something as small as " +
                "walking a pet or as life-changing as going vegan.", R.drawable.intro_plus, Color.parseColor("#686868")));
        addSlide(AppIntroFragment.newInstance("Update your habit", "Be sure to update your progression! You'll be reminded to " +
                "log whether or not you've been keeping up with the habit.", R.drawable.intro_check, Color.parseColor("#686868")));
        addSlide(AppIntroFragment.newInstance("See your accomplishment", "Look back at what you've achieved! You'll be able to " +
                "to see all the work you put in and how the habit is building.", R.drawable.graph, Color.parseColor("#686868")));
        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#00bcd4"));
        setSeparatorColor(Color.parseColor("#ffffff"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);
        setVibrateIntensity(30);

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
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            startActivity(intent);
        }

        @Override
        public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
            super.onSlideChanged(oldFragment, newFragment);
            // Do something when the slide changes.
        }
}
