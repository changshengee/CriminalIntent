package changsheng.com.criminalintent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * @author changshengee
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Crime() {
        mDate = new Date();
        mId = UUID.randomUUID();
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }

    public String getFormatDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE,MMM d, yyyy h:m:s aa", Locale.ENGLISH);
        return sdf.format(mDate);
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
