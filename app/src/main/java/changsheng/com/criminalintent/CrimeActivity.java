package changsheng.com.criminalintent;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

/**
 * 在Fragment类中引入Honeycomb的时候，为协同工作，Activity类中相应添加了
 * FragmentManager类。
 * FragmentManager类负责管理fragment并将它们的视图添加到activity的视图层级结构中。
 * <p>
 * FragmentManager类具体管理的是：
 * 1、fragment队列
 * 2、fragment事务回退栈。
 *
 * @author changshengee
 */
public class CrimeActivity extends BaseSingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "changsheng.com.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent i = new Intent(packageContext, CrimeActivity.class);
        i.putExtra(EXTRA_CRIME_ID, crimeId);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

}
