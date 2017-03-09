package org.xiem.com.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.xiem.com.hibernate.object.AbstractVersionedWithIdGen;
import org.xiem.com.hibernate.object.Versioned;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class HibernateUtils {

    public static final int SERVER_AS_USER = 2;
    public static final int TESTER_USER = 1;

	public static void closeQuietly(final Session session) {// 关闭连接会话
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException e) {
                //TODO:
            }
        }
    }

    public static <VersionedType extends Versioned> void initNewVersionedObject(VersionedType vt, boolean enabled, int revisionUserId, String comment) {
        vt.setIsDeleted(false);
        vt.setIsLatest(true);
        vt.setIsEnabled(enabled);
        vt.setRevisionTime(new Date());
        vt.setRevisionUserId(revisionUserId);
        vt.setRevisionComment(comment);
    }

    public static <VersionedType extends Versioned> List<VersionedType> getAllCurrentActive(final Class<VersionedType> type, final Session session) {
        return getAllCurrentActive(type, session, null);
    }

	//***************************************************************************************
	//
	//***************************************************************************************
    @SuppressWarnings("unchecked")
    public static <VersionedType extends Versioned> List<VersionedType> getAllCurrentActive(final Class<VersionedType> type, final Session session, final String extraFilter) {
		StringBuilder sb = new StringBuilder();
        sb.append("SELECT vt FROM ");
		sb.append(ClassUtils.getShortClassName(type));//
        sb.append(" vt where vt.isDeleted = false and vt.isEnabled = true and vt.isLatest = true");
        if (StringUtils.isNotBlank(extraFilter)) {
            sb.append(" and ");
            sb.append(extraFilter);
        }
        return session.createQuery(sb.toString()).list();
    }

	//***************************************************************************************
	//
	//
	//***************************************************************************************
    public static <V extends AbstractVersionedWithIdGen> TIntObjectMap<V> getActiveIdToVersionedObject(final Session session, final Class<V> clazz) {
		List<V> all = getAllCurrentActive(clazz, session, null);//
		TIntObjectMap<V> map = new TIntObjectHashMap<V>();
        for (V v : all) {
            map.put(v.getId(), v);
        }
        return map;
    }
}
