package tech.michaeloverman.android.stickit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

/**
 * Created by Michael on 10/5/2016.
 */
public class TempoMarkingDialogFragment extends DialogFragment
        implements NumberPicker.OnValueChangeListener {

    public static final String EXTRA_TEMPO = "tech.michaeloverman.android.stickit.tempomarking";
    private NumberPicker mPicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int currentSetting = 100; // CHANGE THIS to get value from savedInstanceState
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.number_picker_dialog_layout, null);
        mPicker = (NumberPicker) v.findViewById(R.id.number_picker);
        mPicker.setMaxValue(200);
        mPicker.setMinValue(10);
        mPicker.setValue(currentSetting);
        mPicker.setWrapSelectorWheel(false);
        mPicker.setOnValueChangedListener(this);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.set_new_tempo)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private void sendResult(int resultCode, int newTempo) {
        if (getTargetFragment() == null) return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TEMPO, newTempo);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        sendResult(Activity.RESULT_OK, newVal);
    }

}
