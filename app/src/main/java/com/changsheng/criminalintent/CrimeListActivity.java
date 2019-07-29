package com.changsheng.criminalintent;

import androidx.fragment.app.Fragment;

/**
 * @author changshengee
 */
public class CrimeListActivity extends BaseSingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
