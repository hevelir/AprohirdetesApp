package com.example.mobwebhazi.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.mobwebhazi.R;
import com.example.mobwebhazi.data.Item;

public class DetailsDialogFragment extends DialogFragment {
    private TextView detailsTextView;
    public static final String TAG = "DetailsDialogFragment";
    private Item item;

    public DetailsDialogFragment(Item item) {
        this.item = item;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FragmentActivity activity = getActivity();
       /* if (activity instanceof NewItemDialogFragment.NewItemDialogListener) {
            listener = (NewItemDialogFragment.NewItemDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the NewItemDialogListener interface!");
        }*/
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.details)
                .setView(getContentView())
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private View getContentView() {
        /*
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_item, null);
        nameEditText = contentView.findViewById(R.id.ItemNameEditText);
        descriptionEditText = contentView.findViewById(R.id.ItemDescriptionEditText);
        PriceEditText = contentView.findViewById(R.id.ItemPriceEditText);
        categorySpinner = contentView.findViewById(R.id.ItemCategorySpinner);
        categorySpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.category_items)));
        return contentView;*/
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_details, null);
        detailsTextView = contentView.findViewById(R.id.ItemDescription);

        detailsTextView.setText(item.description);

        return contentView;
    }
}
