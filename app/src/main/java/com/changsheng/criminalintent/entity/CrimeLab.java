package com.changsheng.criminalintent.entity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.changsheng.criminalintent.dao.CrimeBaseHelper;
import com.changsheng.criminalintent.dao.CrimeDbSchema.CrimeTable;

/**
 * @author changshengee
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;

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
    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public int getFocusPosition(UUID id) {
        List<Crime> crimes = getCrimes();
        for (int i = 0; i < crimes.size(); i++) {
            Crime crime = crimes.get(i);
            if (crime.getId().equals(id)) {
                return i;
            }
        }
        return 0;
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Crime getCrime(UUID id) {
        try (CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID + " =?", new String[]{id.toString()})) {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }
    }

    public void addCrime(Crime c) {
        ContentValues cv = getContextValues(c);
        mDatabase.insert(CrimeTable.NAME, null, cv);
    }

    public void delCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " =?", new String[]{uuidString});

    }

    public void update(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues cv = getContextValues(crime);
        mDatabase.update(CrimeTable.NAME, cv, CrimeTable.Cols.UUID + " =?", new String[]{uuidString});
    }

    private static ContentValues getContextValues(Crime crime) {
        ContentValues cv = new ContentValues();
        cv.put(CrimeTable.Cols.UUID, crime.getId().toString());
        cv.put(CrimeTable.Cols.TITLE, crime.getTitle());
        cv.put(CrimeTable.Cols.DATE, crime.getDate().toString());
        cv.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        cv.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        return cv;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null,
                null
        );
        return new CrimeCursorWrapper(cursor);
    }

    public File getPhotoFile(Crime crime) {
        File externalFileDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFileDir == null) {
            return null;
        }
        return new File(externalFileDir, crime.getPhotoFilename());
    }
}
