package vn.phoneandhisfriends.washcar_store.dialogs;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.phoneandhisfriends.washcar_store.R;

public class ReasonDenyDialog extends DialogFragment {
    EditText edtReason;
    Button btnCancel;
    Button btnOk;

    public ReasonDenyDialog() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reason_deny_dialog, null);
        builder.setView(view);
        edtReason = view.findViewById(R.id.edtReason);
        btnOk = view.findViewById(R.id.btnOk);
        btnCancel = view.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtReason.getText().toString().length() > 0) {
                    Toast.makeText(getActivity(), "Your decision will be sended to customer", Toast.LENGTH_SHORT).show();

                    dismiss();
                }else {
                    Toast.makeText(getActivity(), "Please input reason", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            d.getWindow().setLayout(width, height);
        }
    }

}
