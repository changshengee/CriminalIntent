package changsheng.com.criminalintent.entity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import changsheng.com.criminalintent.dao.CrimeBaseHelper;

/**
 * @author changshengee
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    private Context mContext;

    private SQLiteDatabase mDatabase;


    @RequiresApi(api = Build.VERSION_CODES.P)
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

// todo 2019/7/18

    /**
     * 打开SQLiteDataBase
     * <p>
     * 调用getWritableDatabase(),CrimeBaseHelper要做如下工作
     * 1、打开data/data/com.xxx.xxx/databases/crimeBase.db数据库;如果不存在，就先创建。
     * 2、如果是首次创建数据库，就调用onCreate(SQLiteDatabase.db)方法，然后保存最新的版本号。
     * 3、如果已创建过数据库，首先检查它的版本号。如果CrimeOpenHelper中的版本号更高，就调用onUpgrade(...)方法升级
     *
     * @param context context
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        mCrimes = new ArrayList<>();
    }

    public int getFocusPosition(UUID id) {
        for (int i = 0; i < mCrimes.size(); i++) {
            Crime crime = mCrimes.get(i);
            if (crime.getId().equals(id)) {
                return i;
            }
        }
        return 0;
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for (Crime crime : mCrimes) {
            if (crime.getId().equals(id)) {
                return crime;
            }
        }
        return null;
    }

    public void addCrime(Crime c) {
        mCrimes.add(c);
    }

    public void delCrime(Crime crime) {
        mCrimes.remove(crime);
    }
}
