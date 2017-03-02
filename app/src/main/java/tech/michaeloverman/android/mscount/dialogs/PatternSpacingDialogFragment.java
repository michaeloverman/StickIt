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
 * Created by Michael on 10/2/2016.
 */

public class PatternSpacingDialogFragment extends AppCompatDialogFragment {

    public static final String EXTRA_SPACING = "tech.michaeloverman.android.stickit.spacing";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.set_pattern_spacing)
                .setItems(R.array.spacing_options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, which);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);

        return builder.create();
    }

    private void sendResult(int resultCode, int spacingChoice) {
        if (getTargetFragment() == null) return;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SPACING, spacingChoice);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }


}
