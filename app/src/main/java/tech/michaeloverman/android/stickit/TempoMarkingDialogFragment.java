package tech.michaeloverman.android.stickit;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

/**
 * Created by Michael on 10/5/2016.
 */
public class TempoMarkingDialogFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    public static final String EXTRA_TEMPO = "tech.michaeloverman.android.stickit.tempomarking";
//    private NumberPicker mPicker;
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflater.inflate(R.layout.number_picker_dialog_layout, null);
//        mPicker = (NumberPicker) v.findViewById(R.id.number_picker);
//        mPicker.setMaxValue(200);
//        mPicker.setMinValue(10);
//        mPicker.setWrapSelectorWheel(false);
//        mPicker.setOnValueChangedListener(this);
//
//        return new AlertDialog.Builder(getActivity())
//                .setTitle(R.string.set_new_tempo)
//                .setView(R.layout.number_picker_dialog_layout)
//                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setNegativeButton(android.R.string.cancel, null)
//                .create();
//    }

//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//        return new TimePickerDialog();
//    }

    private void sendResult(int resultCode, int newTempo) {
        if (getTargetFragment() == null) return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TEMPO, newTempo);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
//
//    @Override
//    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//        sendResult(Activity.RESULT_OK, newVal);
//    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
