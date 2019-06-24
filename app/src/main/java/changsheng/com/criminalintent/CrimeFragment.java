package changsheng.com.criminalintent;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private Crime mCrime;
    private EditText mTitleField;

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    /**
     * 该方法实例化fragment视图的布局，然后将实例化的View返回给托管activity。
     * LayoutInflater和ViewGroup是实例化布局的必要参数。Bundle用来存储恢复数据，可供该方法从保存状态下
     * 重建视图。
     * <p>
     * 在onCreateView(...)方法中，fragment的视图是直接通过调用LayoutInflater.inflater(...)方法并传入
     * 布局的资源ID生成的。第二个参数是视图的父视图，我们通常需要父视图来正确配置组件。第三个参数告知布局生成器
     * 是否将生成的视图添加给父视图。这里，我们传入了false参数，因为我们将以activity代码的方式添加视图。
     *
     * @param inflater           布局参数
     * @param container          父视图参数
     * @param savedInstanceState 保存状态
     * @return 视图
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = view.findViewById(R.id.crime_title);
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
        return view;
    }
}
