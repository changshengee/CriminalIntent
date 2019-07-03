package changsheng.com.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

class CrimeListFragment extends Fragment {

    private static final int REQUEST_CRIME = 1;
    private RecyclerView mRecycleListView;

    private CrimeAdapter mCrimeAdapter;

    private UUID changedCrimeId;

    /**
     * 注意，没有LayoutManager支持，不仅RecyclerView无法工作，还会导致应用崩溃。所以
     * RecyclerView视图创建以后，就立即转交给了LayoutManager对象。
     * 前述，RecyclerView任务是回收利用以及定位屏幕上的TextView视图。实际上，定位的任务被委托给了LayoutManager
     * <p>
     * 除了在屏幕上定位列表项，LayoutManager还负责定义屏幕的滚动行为。
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_crime_list, container, false);
        mRecycleListView = view.findViewById(R.id.crime_recycler_list);
        mRecycleListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUi();
        return view;
    }

    /**
     * 设置CrimeListFragment用户的UI界面
     * <p>
     * 该方法创建CrimeAdapter，然后设置给RecyclerView
     */
    private void updateUi() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimes);
            mRecycleListView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.notifyDataSetChanged();
        }
    }


    /**
     * ViewHolder类引用了用于显示crime标题的TextView视图。这就要求itemView必须是个TextView，否则代码会崩溃。
     */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;

        private TextView mDateTextView;

        private CheckBox mSolvedCheckBox;

        private Crime mCrime;

        CrimeHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }


        void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getFormatDate());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
            mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isSolved) {
                    mCrime.setSolved(isSolved);
                }
            });
        }

        @Override
        public void onClick(View view) {
            // Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
            //Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            Intent intent=CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivityForResult(intent, REQUEST_CRIME);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        /**
         * RecyclerView需要新的View视图来显示列表项，会调用onCreateViewHolder()方法.
         * 在这个方法内部，我们创建View视图，然后封装到ViewHolder视图中。
         * 此时RecyclerView并不要求封装视图装载数据。
         * <p>
         * 为了得到View视图，我们实例化了android标准库中名为simple_list_item_1的布局。
         * 该布局定义了美观的TextView视图，主要用于列表项的展示。
         *
         * @param parent
         * @param viewType
         * @return
         */
        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        /**
         * 该方法会把ViewHolder的View视图和模型层数据绑定起来。收到ViewHolder和列表项在数据集中的索引位置后，我们
         * 找到要显示的数据进行绑定,绑定完毕，刷新View视图。
         *
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeUi();
    }

    private void resumeUi() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimes);
            mRecycleListView.setAdapter(mCrimeAdapter);
        } else {
            mCrimeAdapter.notifyItemChanged(crimeLab.getFocusPosition(this.changedCrimeId));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_CRIME) {
            this.changedCrimeId = (UUID) data.getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
            resumeUi();
        }
    }
}
