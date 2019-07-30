package com.changsheng.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.changsheng.criminalintent.utils.PictureUtils;

import java.util.Objects;

public class PhotoFragment extends DialogFragment {

    public static final String ARGS_PHOTO = "photo";

    private ImageView mPhotoView;

    public static PhotoFragment newInstance(String path) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_PHOTO, path);
        PhotoFragment pf = new PhotoFragment();
        pf.setArguments(args);
        return pf;
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String path = (String) Objects.requireNonNull(getArguments()).getSerializable(ARGS_PHOTO);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);
        mPhotoView = view.findViewById(R.id.dialog_photo);
        mPhotoView.setImageBitmap(PictureUtils.getBitmap(path));
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .create();
    }
}
