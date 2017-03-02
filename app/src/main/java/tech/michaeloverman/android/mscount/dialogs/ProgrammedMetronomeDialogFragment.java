package tech.michaeloverman.android.mscount.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import tech.michaeloverman.android.mscount.R;

/**
 * Created by Michael on 2/21/2017.
 */

public class ProgrammedMetronomeDialogFragment extends AppCompatDialogFragment {
    public static final String EXTRA_CUSTOM_MET = "tech.michaeloverman.android.stickit.custom_met";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.choose_etude)
                .setItems(R.array.custom_metronome_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, which);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }

    private void sendResult(int resultCode, int selection) {
        if (getTargetFragment() == null) return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CUSTOM_MET, selection);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
