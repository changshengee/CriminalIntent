package changsheng.com.criminalintent;

import java.util.UUID;

/**
 * @author changshengee
 */
public class Crime {
    private UUID mId;
    private String mTitle;

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
        mId = UUID.randomUUID();
    }
}
