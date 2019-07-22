package changsheng.com.criminalintent.entity;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import changsheng.com.criminalintent.dao.CrimeDbSchema;
import changsheng.com.criminalintent.dao.CrimeDbSchema.CrimeTable;

/**
 * @author changshengee
 */
public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getcrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        return crime;
    }

}
