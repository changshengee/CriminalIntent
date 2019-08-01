package com.changsheng.criminalintent;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author changshengee
 */
public abstract class BaseSingleFragmentActivity extends AppCompatActivity {
    /**
     * SingleFragmentActivity的子类会实现该方法，来返回由activity托管的实例
     *
     * @return activity托管的实例
     */
    protected abstract Fragment createFragment();


    /**
     * @return 任何时候，该方法都返回有效的布局资源Id
     */
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
