package nl.hva.gamesbacklogmanager.utility;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import nl.hva.gamesbacklogmanager.R.string;

/**
 * Created by Raoul on 16-4-2016.
 */
public class ConfirmDeleteDialog extends DialogFragment {
   /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
    * Each method passes the DialogFragment in case the host needs to query it. */

    // Use this instance of the interface to deliver action events
    private ConfirmDeleteDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the ConfirmDeleteDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the ConfirmDeleteDialogListener so we can send events to the host
            mListener = (ConfirmDeleteDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ConfirmDeleteDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("message");
        Builder builder = new Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(string.dialog_game_deletion_positive, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Activate method onDialogPositiveClick inside implementing class
                        mListener.onDialogPositiveClick(ConfirmDeleteDialog.this);
                    }
                })
                .setNegativeButton(string.dialog_game_deletion_negative, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Activate method onDialogNegativeClick inside implementing class
                        mListener.onDialogNegativeClick(ConfirmDeleteDialog.this);
                    }
                });
        return builder.create();
    }

    public interface ConfirmDeleteDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);
    }
}
