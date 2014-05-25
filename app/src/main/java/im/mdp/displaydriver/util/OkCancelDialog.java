package im.mdp.displaydriver.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by mdp on 5/24/14.
 */

public class OkCancelDialog extends DialogFragment {

    public interface OkCancelDialogListener {
        public void onDialogOkClick(DialogFragment dialog);
        public void onDialogCancelClick(DialogFragment dialog);
    }

    OkCancelDialogListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mListener = (OkCancelDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(mListener.toString() + " must implement OkCancelDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        String message = args.getString("message", "Are you sure?");

        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mListener.onDialogOkClick(OkCancelDialog.this);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        mListener.onDialogCancelClick(OkCancelDialog.this);
                    }
                })
                .create();
    }
}
