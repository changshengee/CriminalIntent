package changsheng.com.criminalintent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;

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
public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
