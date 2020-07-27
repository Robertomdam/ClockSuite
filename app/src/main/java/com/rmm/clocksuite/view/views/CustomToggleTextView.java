package com.rmm.clocksuite.view.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.rmm.clocksuite.R;

import java.util.ArrayList;

/**
 * Custom TextView that behaves like a toggle button.
 */
public class CustomToggleTextView extends androidx.appcompat.widget.AppCompatTextView implements View.OnClickListener {

    private ArrayList<CustomToggleTextViewListener> mEventListeners;
    private boolean mIsSelected;

    /**
     * Construct the Custom toggle text view and sets the default values of its members. Also setups and on click listener.
     * @param context
     * @param attrs
     */
    public CustomToggleTextView (Context context, AttributeSet attrs) {
        super(context, attrs);

        mIsSelected = false;

        mEventListeners = new ArrayList<>();

        setOnClickListener(this);
    }

    /**
     * Calls the toggle functionality.
     * @param view
     */
    @Override
    public void onClick(View view) {
//        Toast.makeText(getContext(), "Selected: " + getText(), Toast.LENGTH_SHORT).show();
        toggle();
    }

    /**
     * Actives the toggle functionality by changing its state and refreshing its appearance.
     * Also make a call to the event of every event listeners of the view.
     */
    private void toggle () {
        mIsSelected = !mIsSelected;

        notifyToggleEvent ();
        refreshAppearance ();
    }

    /**
     * Refreshes the appearance of the view by changing its text color from a default value to the accent of the app.
     */
    private void refreshAppearance () {
        if (mIsSelected)
            setTextColor (getResources().getColor(R.color.greenLeaf));
        else
            setTextColor (getResources().getColor(R.color.greyLightXX));
    }

    /**
     * Does the call to the toggle event of all the listeners of the view.
     */
    private void notifyToggleEvent () {
        for (int i = 0; i < mEventListeners.size(); i++) {
            mEventListeners.get(i).onToggleStateChanged (mIsSelected);
        }
    }

    /**
     * Getter for the isSelected value.
     * @return Whether the view is currently selected or not.
     */
    public boolean getIsSelected () { return mIsSelected; }

    /**
     * Setter for the isSelected value.
     * @param b If the toggle is selected or not.
     */
    public void setIsSelected (boolean b) { mIsSelected = b; refreshAppearance(); }

    /**
     * Adds a new event listeners for the view.
     * @param eventListener The reference to the new event listener.
     */
    public void addEventListener (CustomToggleTextViewListener eventListener) {
        mEventListeners.add(eventListener);
    }

    /**
     * Interface to allow external classes to listen to events that belongs to CustomToggleTextView class.
     */
    public interface CustomToggleTextViewListener {
        void onToggleStateChanged (boolean currentState);
    }
}
