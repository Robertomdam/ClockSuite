package com.rmm.clocksuite.view.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Custom FragmentPagerAdapter to handle the needed fragments for the TabbedActivity.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mFragmentsNames;

    /**
     * Adds a new fragment to be managed.
     * @param f The new fragment to be added.
     * @param name The name of the fragment.
     */
    public void addFragment (Fragment f, String name)
    {
        mFragments.add(f);
        mFragmentsNames.add(name);
    }

    /**
     * Constructs the ViewPagerAdapter and initializes its members.
     * @param fm The FragmentManager of the Activity.
     * @param behavior The behavior.
     */
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        mFragments = new ArrayList<>();
        mFragmentsNames = new ArrayList<>();
    }

    /**
     * Retrieves the fragment at that position.
     * @param position The position of the current item.
     * @return The fragment at that position.
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    /**
     * Retrieves the total amount of fragments.
     * @return
     */
    @Override
    public int getCount() {
        return mFragments.size();
    }

    /**
     * Retrieves the name of the fragment at that position.
     * @param position The position of the current item.
     * @return The name of the fragment at that position.
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentsNames.get(position);
    }
}
