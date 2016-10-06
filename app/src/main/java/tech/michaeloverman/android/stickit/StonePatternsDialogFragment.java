package tech.michaeloverman.android.stickit;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * Created by Michael on 10/4/2016.
 */
public class StonePatternsDialogFragment extends AppCompatDialogFragment {
    public static final String EXTRA_STONE_RESPONSE = "tech.michaeloverman.android.stickit.stoneresponse";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.stick_control_question)
                .setMessage(R.string.stick_control_dialog_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, true);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, false);
                    }
                });

        return builder.create();
    }

    private void sendResult(int resultCode, boolean response) {
        if (getTargetFragment() == null) return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_STONE_RESPONSE, response);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
