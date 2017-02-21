package tech.michaeloverman.android.stickit.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.NumberPicker;

import tech.michaeloverman.android.stickit.R;

/**
 * Created by Michael on 10/5/2016.
 */
public class TempoMarkingDialogFragment extends DialogFragment
        implements NumberPicker.OnValueChangeListener {
    private static final String ARG_OLD_TEMPO = "tempo";
    public static final String EXTRA_TEMPO = "tech.michaeloverman.android.stickit.tempomarking";
    private NumberPicker mPicker;

    public static TempoMarkingDialogFragment newInstance(int oldtempo) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_OLD_TEMPO, oldtempo);

        TempoMarkingDialogFragment fragment = new TempoMarkingDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int currentSetting = (int) getArguments().getSerializable(ARG_OLD_TEMPO);
        mPicker = new NumberPicker(getActivity());
        mPicker.setMaxValue(200);
        mPicker.setMinValue(10);
        mPicker.setValue(currentSetting);
        mPicker.setWrapSelectorWheel(false);
        mPicker.setOnValueChangedListener(this);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.set_new_tempo)
                .setView(mPicker)
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
