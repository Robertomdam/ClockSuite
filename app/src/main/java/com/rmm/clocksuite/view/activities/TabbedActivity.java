package com.rmm.clocksuite.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.rmm.clocksuite.R;
import com.rmm.clocksuite.view.adapters.ViewPagerAdapter;
import com.rmm.clocksuite.view.fragments.AlarmsFragment;
import com.rmm.clocksuite.view.fragments.ChronoFragment;
import com.rmm.clocksuite.view.fragments.TimezoneFragment;
import com.rmm.clocksuite.view.fragments.TimerFragment;

/**
 * Main activity of the app. It will act as the container for all the fragments.
 */
public class TabbedActivity extends AppCompatActivity {

    ViewPagerAdapter viewPagerAdapter;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    /**
     * Initializes all the stuff that belongs to the activity management:
     * · Finds all the views.
     * · Creates the fragments that will be contained in the activity.
     * · Sets up the TabLayout and ViewPager needed configuration.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        findViews();
        createFragments();

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Find all the views of the activity.
     */
    private void findViews ()
    {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    /**
     * Creates the fragments that will be contained in the activity and adds them to the view pager.
     */
    private void createFragments ()
    {
        Fragment fragAlarms   = new AlarmsFragment   ();
        Fragment fragTimeZone = new TimezoneFragment();
        Fragment fragChrono   = new ChronoFragment   ();
        Fragment fragTimer    = new TimerFragment    ();

        viewPagerAdapter = new ViewPagerAdapter (getSupportFragmentManager(), -1);
        viewPagerAdapter.addFragment (fragAlarms    , getResources().getString(R.string.fragment_alarms_label  ) );
        viewPagerAdapter.addFragment (fragTimeZone  , getResources().getString(R.string.fragment_timezone_label) );
        viewPagerAdapter.addFragment (fragChrono    , getResources().getString(R.string.fragment_chrono_label  ) );
        viewPagerAdapter.addFragment (fragTimer     , getResources().getString(R.string.fragment_timer_label   ) );
    }
}
