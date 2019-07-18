package changsheng.com.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


/**
 * @author changshengee
 */
public class TimePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "date";

    private TimePicker mTimePicker;

    static  final  String EXTRA_DATA="com.changsheng.criminalintent.time";

    static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        TimePickerFragment df = new TimePickerFragment();
        df.setArguments(args);
        return df;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Date date = (Date) Objects.requireNonNull(getArguments()).getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(date));
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        mTimePicker = view.findViewById(R.id.dialog_date_time_picker);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int hour = mTimePicker.getHour();
                        int minute = mTimePicker.getMinute();
                        calendar.set(Calendar.HOUR, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        Date date = calendar.getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();

    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATA, date);
        getTargetFragment().onActivityResult(1, resultCode, intent);
    }
}
