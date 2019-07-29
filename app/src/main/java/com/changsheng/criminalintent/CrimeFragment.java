package com.changsheng.criminalintent;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import com.changsheng.criminalintent.entity.Crime;
import com.changsheng.criminalintent.entity.CrimeLab;

/**
 * CrimeFragment类是与模型及视图对象交互的控制器，用于显示特定的crime的明细
 * 信息，并在用户修改这些信息后立即进行更新。
 * 在GeoQuiz应用中，activity通过其生命周期方法完成了大部分逻辑控制工作。
 * 而在CrimeFragment应用中，这些工作是由fragment生命周期方法完成的。
 * fragment的许多方法对应着我们熟知的Activity方法，如OnCreate(Bundle)方法。
 *
 * @author changshengee
 */
public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";

    static final String EXTRA_CRIME_ID = "com.changsheng.criminalintent.extra_crime_id";

    private static final String DIALOG_DATE = "DialogDate";

    private static final String DIALOG_TIME = "DialogTime";


    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 2;
    private static final int REQUEST_PHOTO = 3;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;
    private Button mTimeButton;
    private Button mReportButton;
    private Button mSuspectButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;


    static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(args);
        return crimeFragment;
    }


    /**
     * 首先，Fragment.OnCreate(Bundle)是公共方法，而Activity.OnCreate(Bundle)
     * 是保护方法。Fragment.OnCreate(...)方法及其他Fragment生命周期的方法必须是
     * 公共方法，因为托管的fragment的activity要调用它们。
     * <p>
     * 其次，类似于activity，fragment同样具有保存及获取状态的Bundle。如同使用Activity.
     * OnSaveInstanceState(Bundle)方法那样，我们可以更具需要覆盖Fragment.OnSaveInstanceState(Bundle)
     * 方法。
     * <p>
     * 另外，fragment的视图没有在Fragment.OnCreate(...)方法中生成。虽然我们在Fragment.OnCreate(...)
     * 方法中配置了fragment实例，但创建和配置fragment视图是另一个fragment生命周期方法完成的。
     *
     * @param savedInstanceState 状态
     */
    @TargetApi(Build.VERSION_CODES.P)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID crimeId = (UUID) Objects.requireNonNull(getArguments()).getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
        mPhotoFile = CrimeLab.get(getActivity()).getPhotoFile(mCrime);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).update(mCrime);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 该方法实例化fragment视图的布局，然后将实例化要参数。Bundle用来存储恢复数据，可供该方法从保存状态下
     * * 重建视图。
     * * <p>
     * * 在onCreateView(...)方法中，fragment的视图是直接通过调用LayoutInflater.inflater(...)方法并传入
     * * 布局的资源ID生成的。第二个参数是视图的父视图，我们通常需要父视图来正确配置组件。第三个参数告知布局生成器
     * * 是否将生成的视图添加给父视图。这里，我们传入了false参数，因为我们将以activity代码的方式添加视图。
     * *
     * * @param inflater           布局参数
     * * @param container          父视图参数的View返回给托管activity。
     * LayoutInflater和ViewGroup是实例化布局的必
     *
     * @param savedInstanceState 保存状态
     * @return 视图
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This one too
            }
        });
        mDateButton = view.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getFormatDate());
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(Objects.requireNonNull(fm), DIALOG_DATE);
            }
        });
        mTimeButton = view.findViewById(R.id.crime_time_picker);
        mTimeButton.setText(mCrime.getFormatTime());
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(Objects.requireNonNull(fm), DIALOG_TIME);
            }
        });
        mSolvedCheckBox = view.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSolved) {
                mCrime.setSolved(isSolved);
            }
        });
        mReportButton = view.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = ShareCompat.IntentBuilder.from(Objects.requireNonNull(getActivity())).getIntent();
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        // 验证过滤器 不让任何联系人应用和intent匹配
        //pickContact.addCategory(Intent.CATEGORY_HOME);
        mSuspectButton = view.findViewById(R.id.crime_suspect);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 第二个参数必须是大于等于0的
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });
        if (mCrime.getSuspect() != null) {
            mSuspectButton.setText(mCrime.getSuspect());
        }
        PackageManager pm = Objects.requireNonNull(getActivity()).getPackageManager();
        if (pm.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            mSuspectButton.setEnabled(false);
        }
        mPhotoButton = view.findViewById(R.id.crime_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(pm) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        if (canTakePhoto) {
            //i uri = Uri.fromFile(mPhotoFile);
            Uri uri = FileProvider.getUriForFile(getActivity().getApplicationContext(),
                    getActivity().getPackageName() + ".fileProvider",
                    mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = view.findViewById(R.id.crime_photo);
        returnResult();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void returnResult() {
        Intent data = new Intent();
        data.putExtra(EXTRA_CRIME_ID, mCrime.getId());
        Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_OK, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) Objects.requireNonNull(data).getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            mDateButton.setText(mCrime.getFormatDate());
        }
        if (requestCode == REQUEST_TIME) {
            Date date = (Date) Objects.requireNonNull(data).getSerializableExtra(TimePickerFragment.EXTRA_DATA);
            mCrime.setDate(date);
            mTimeButton.setText(mCrime.getFormatTime());
        }
        if (requestCode == REQUEST_CONTACT && data != null) {
            Uri contactUri = data.getData();
            // Specify which fields you want your query to return
            // values for
            String[] queryFields = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };
            // Perform your query - the contactUri is like a "where"
            // clause here
            Cursor c = Objects.requireNonNull(getActivity()).getContentResolver().query(
                    Objects.requireNonNull(contactUri),
                    queryFields,
                    null,
                    null,
                    null);
            try {
                if (Objects.requireNonNull(c).getCount() == 0) {
                    return;
                }
                c.moveToFirst();
                String suspect = c.getString(0);
                mCrime.setSuspect(suspect);
                mSuspectButton.setText(suspect);
            } finally {
                Objects.requireNonNull(c).close();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime, menu);
    }

    @TargetApi(Build.VERSION_CODES.P)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_del_item) {
            CrimeLab.get(getActivity()).delCrime(mCrime);
            Intent intent = new Intent(getActivity(), CrimeListActivity.class);
            startActivity(intent);
            Objects.requireNonNull(this.getActivity()).finish();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getCrimeReport() {
        String solvedString;
        if (mCrime.isSolved()) {
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }
        String dateFormat = "EEE，MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();
        String suspect = mCrime.getSuspect();
        suspect = suspect == null ? getString(R.string.crime_report_no_suspect) : getString(R.string.crime_report_suspect, suspect);
        return getString(R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect);
    }
}
