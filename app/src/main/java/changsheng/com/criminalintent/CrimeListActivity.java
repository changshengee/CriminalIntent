package changsheng.com.criminalintent;

import androidx.fragment.app.Fragment;

/**
 * @author changshengee
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
