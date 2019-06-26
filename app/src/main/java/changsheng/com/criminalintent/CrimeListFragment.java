package changsheng.com.criminalintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

class CrimeListFragment extends Fragment {
    private RecyclerView mRecycleListView;

    /**
     * 注意，没有LayoutManager支持，不仅RecyclerView无法工作，还会导致应用崩溃。所以
     * RecyclerView视图创建以后，就立即转交给了LayoutManager对象。
     * 前述，RecyclerView任务是回收利用以及定位屏幕上的TextView视图。实际上，定位的任务被委托给了LayoutManager
     *
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
        return view;
    }

}
