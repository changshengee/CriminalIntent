package changsheng.com.criminalintent.entity;

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


    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public Crime() {
        this(UUID.randomUUID());
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
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d yyyy", Locale.ENGLISH);
        return sdf.format(mDate);
    }

    public String getFormatTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("h:m:s aa", Locale.ENGLISH);
        return sdf.format(mDate);
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
