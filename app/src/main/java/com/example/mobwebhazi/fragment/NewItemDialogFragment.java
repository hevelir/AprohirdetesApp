package com.example.mobwebhazi.fragment;



import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.mobwebhazi.R;
import com.example.mobwebhazi.data.Item;

public class NewItemDialogFragment extends DialogFragment {
    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText PriceEditText;
    private Spinner categorySpinner;

    private String username;
    public static final String TAG = "NewItemDialogFragment";

    public interface NewItemDialogListener {
        void onItemCreated(Item newItem);
    }

    private NewItemDialogListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            username = bundle.getString("uname");
        }
        if (activity instanceof NewItemDialogListener) {
            listener = (NewItemDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the NewItemDialogListener interface!");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.new_item)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isValid()) {
                            listener.onItemCreated(getItem());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private boolean isValid() {
        return nameEditText.getText().length() > 0;
    }

    private Item getItem() {
        Item Item = new Item();
        Item.name = nameEditText.getText().toString();
        Item.user = username;

        Item.description = descriptionEditText.getText().toString();
        try {
            Item.price = Integer.parseInt(PriceEditText.getText().toString());
        } catch (NumberFormatException e) {
            Item.price = 0;
        }
        Item.category = com.example.mobwebhazi.data.Item.Category.getByOrdinal(categorySpinner.getSelectedItemPosition());

        return Item;
    }

    private View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_item, null);
        nameEditText = contentView.findViewById(R.id.ItemNameEditText);
        descriptionEditText = contentView.findViewById(R.id.ItemDescriptionEditText);
        PriceEditText = contentView.findViewById(R.id.ItemPriceEditText);
        categorySpinner = contentView.findViewById(R.id.ItemCategorySpinner);
        categorySpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.category_items)));
        return contentView;
    }
}