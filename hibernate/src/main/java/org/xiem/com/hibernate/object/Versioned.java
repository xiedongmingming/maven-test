package org.xiem.com.hibernate.object;

import java.io.Serializable;
import java.util.Date;

public interface Versioned extends Serializable {// 鐢ㄤ簬鐗堟湰鎺у埗鐨�7涓叕鍏卞瓧娈�

    long getVersionId();

    void setVersionId(final long id);

    int getRevisionUserId();

    void setRevisionUserId(final int id);

    String getRevisionComment();

    void setRevisionComment(final String comment);

    Date getRevisionTime();

    void setRevisionTime(final Date time);

    boolean getIsDeleted();

    void setIsDeleted(final boolean deleted);

    boolean getIsLatest();

    void setIsLatest(final boolean isLatest);

    boolean getIsEnabled();

    void setIsEnabled(final boolean isEnabled);
}
