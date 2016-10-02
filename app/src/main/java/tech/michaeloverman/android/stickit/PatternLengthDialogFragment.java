package tech.michaeloverman.android.stickit;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * Created by Michael on 10/1/2016.
 */

public class PatternLengthDialogFragment extends AppCompatDialogFragment {

    public static final String EXTRA_NEW_LENGTH = "tech.michaeloverman.android.stickit.newlength";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.choose_pattern_length)
                .setItems(R.array.pattern_lengths, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, which + 4);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }

    private void sendResult(int resultCode, int newLength) {
        if (getTargetFragment() == null) return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NEW_LENGTH, newLength);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
