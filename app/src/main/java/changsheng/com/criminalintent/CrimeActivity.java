package changsheng.com.criminalintent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

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
public class CrimeActivity extends FragmentActivity {

    /**
     * fragment事务被用来添加、移除、附加、分离或替换fragment队列中的fragment。
     * 这是使用fragment在运行组装和重新组装用户界面的关键。FragmentManager管理着fragment
     * 事务回退栈。
     *
     * FragmentManager.beginTransaction()方法创建并返回FragmentTransaction实例。
     * FragmentTransaction类使用了名为fluent interface的接口方法，通过该方法配置FragmentTransaction
     * 返回FragmentTransaction类对象，而不是void。由此可以得到一个FragmentTransaction队列。
     *
     * add(...)方法是整个事务的核心，它含有两个参数:容器视图资源ID和新创建的CrimeFragment。
     * 容器视图资源ID是定义在activity_crime.xml中FrameLayout组件的资源ID。
     * 作用有:
     * 1、告诉FragmentManager，fragment视图应该出现在activity视图的什么位置。
     * 2、用作FragmentTransaction队列中fragment的唯一标识符。
     *
     *
     *
     * @param savedInstanceState 状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
