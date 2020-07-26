package com.rmm.clocksuite.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rmm.clocksuite.R;

import java.util.ArrayList;

/**
 * Custom RecyclerViewAdapter to handle the alarms.
 */
public class AlarmsRecylerAdapter extends RecyclerView.Adapter<AlarmsRecylerAdapter.AlarmViewHolder> {

    private IAlarmRecyclerEventListener mEventListener;

    private ArrayList<String> mData;
    public void setData(ArrayList<String> data) {
        mData = data;
    }

    /**
     * Contrucs the adapter.
     * @param eventListener The object that will listen to the adapter's events.
     */
    public AlarmsRecylerAdapter (IAlarmRecyclerEventListener eventListener) {
        mEventListener = eventListener;
    }

    /**
     * Inflates the view of the view holder.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmViewHolder ( LayoutInflater.from(parent.getContext()).inflate (R.layout.viewholder_alarm, parent, false) );
    }

    /**
     * Fills the data of the view holder views based on the data of the alarms.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.tvTime.setText ("12:34");
        holder.tvNote.setText ("Test note");
        holder.swSwitch.setChecked (true);
    }

    /**
     * The total amount of alarms.
     * @return
     */
    @Override
    public int getItemCount() {
        return 14;
    }

    /**
     * Custom ViewHolder to help the AlarmsRecyclerAdapter handle the alarms.
     */
    class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTime;
        private TextView tvNote;
        private SwitchCompat swSwitch;

        /**
         * Construct a view holder and sets up an onCLickListener to itself.
         * @param itemView
         */
        private AlarmViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTime   = itemView.findViewById (R.id.tvTime);
            tvNote   = itemView.findViewById (R.id.tvNote);
            swSwitch = itemView.findViewById (R.id.swSwitch);

            itemView.setOnClickListener(this);
        }

        /**
         * Triggers the event listener method that corresponds with the onCLick event for a particular view holder.
         * @param view
         */
        @Override
        public void onClick (View view) {
            mEventListener.onAlarmItemSelected( getAdapterPosition() );
        }
    }

    /**
     * Interface for letting objects receive events triggered by the AlarmsRecyclerAdapter.
     */
    public interface IAlarmRecyclerEventListener
    {
        public void onAlarmItemSelected(int position);
    }
}
