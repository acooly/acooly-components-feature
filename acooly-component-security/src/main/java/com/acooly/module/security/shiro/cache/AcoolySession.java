/**
 * acooly-components-feature
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-12-07 14:08
 */
package com.acooly.module.security.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.StoppedSessionException;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;

/**
 * @author zhangpu
 * @date 2022-12-07 14:08
 */
@Slf4j
public class AcoolySession implements ValidatingSession, Serializable {
    protected static final long MILLIS_PER_SECOND = 1000;
    protected static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;
    protected static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

    private Serializable id;
    private Date startTimestamp;
    private Date stopTimestamp;
    private Date lastAccessTime;
    private long timeout;
    private boolean expired;
    private String host;
    private Map<Object, Object> attributes;

    public AcoolySession() {
        this.timeout = DefaultSessionManager.DEFAULT_GLOBAL_SESSION_TIMEOUT;
        this.startTimestamp = new Date();
        this.lastAccessTime = this.startTimestamp;
    }

    public AcoolySession(String host) {
        this();
        this.host = host;
    }


    private static boolean isFieldPresent(short bitMask, int fieldBitMask) {
        return (bitMask & fieldBitMask) != 0;
    }

    public static AcoolySession newWithId(Serializable sessionId) {
        AcoolySession session = new AcoolySession();
        session.setId(sessionId);
        return session;
    }

    @Override
    public Serializable getId() {
        return this.id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    @Override
    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    /**
     * Returns the time the session was stopped, or <tt>null</tt> if the session is still active.
     * <p/>
     * A session may become stopped under a number of conditions:
     * <ul>
     * <li>If the user logs out of the system, their current session is terminated (released).</li>
     * <li>If the session expires</li>
     * <li>The application explicitly calls {@link #stop()}</li>
     * <li>If there is an internal system error and the session state can no longer accurately
     * reflect the user's behavior, such in the case of a system crash</li>
     * </ul>
     * <p/>
     * Once stopped, a session may no longer be used.  It is locked from all further activity.
     *
     * @return The time the session was stopped, or <tt>null</tt> if the session is still
     * active.
     */
    public Date getStopTimestamp() {
        return stopTimestamp;
    }

    public void setStopTimestamp(Date stopTimestamp) {
        this.stopTimestamp = stopTimestamp;
    }

    @Override
    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    /**
     * Returns true if this session has expired, false otherwise.  If the session has
     * expired, no further user interaction with the system may be done under this session.
     *
     * @return true if this session has expired, false otherwise.
     */
    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public long getTimeout() {
        return timeout;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Map<Object, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<Object, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void touch() {
        this.lastAccessTime = new Date();
    }

    @Override
    public void stop() {
        if (this.stopTimestamp == null) {
            this.stopTimestamp = new Date();
        }
    }

    protected boolean isStopped() {
        return getStopTimestamp() != null;
    }

    protected void expire() {
        stop();
        this.expired = true;
    }

    /**
     * @since 0.9
     */
    @Override
    public boolean isValid() {
        return !isStopped() && !isExpired();
    }

    /**
     * Determines if this session is expired.
     *
     * @return true if the specified session has expired, false otherwise.
     */
    protected boolean isTimedOut() {

        if (isExpired()) {
            return true;
        }

        long timeout = getTimeout();

        if (timeout >= 0L) {

            Date lastAccessTime = getLastAccessTime();

            if (lastAccessTime == null) {
                String msg = "session.lastAccessTime for session with id [" +
                        getId() + "] is null.  This value must be set at " +
                        "least once, preferably at least upon instantiation.  Please check the " +
                        getClass().getName() + " implementation and ensure " +
                        "this value will be set (perhaps in the constructor?)";
                throw new IllegalStateException(msg);
            }

            // Calculate at what time a session would have been last accessed
            // for it to be expired at this point.  In other words, subtract
            // from the current time the amount of time that a session can
            // be inactive before expiring.  If the session was last accessed
            // before this time, it is expired.
            long expireTimeMillis = System.currentTimeMillis() - timeout;
            Date expireTime = new Date(expireTimeMillis);
            return lastAccessTime.before(expireTime);
        } else {
            if (log.isTraceEnabled()) {
                log.trace("No timeout for session with id [" + getId() +
                        "].  Session is not considered expired.");
            }
        }

        return false;
    }

    @Override
    public void validate() throws InvalidSessionException {
        //check for stopped:
        if (isStopped()) {
            //timestamp is set, so the session is considered stopped:
            String msg = "Session with id [" + getId() + "] has been " +
                    "explicitly stopped.  No further interaction under this session is " +
                    "allowed.";
            throw new StoppedSessionException(msg);
        }

        //check for expiration
        if (isTimedOut()) {
            expire();

            //throw an exception explaining details of why it expired:
            Date lastAccessTime = getLastAccessTime();
            long timeout = getTimeout();

            Serializable sessionId = getId();

            DateFormat df = DateFormat.getInstance();
            String msg = "Session with id [" + sessionId + "] has expired. " +
                    "Last access time: " + df.format(lastAccessTime) +
                    ".  Current time: " + df.format(new Date()) +
                    ".  Session timeout is set to " + timeout / MILLIS_PER_SECOND + " seconds (" +
                    timeout / MILLIS_PER_MINUTE + " minutes)";
            if (log.isTraceEnabled()) {
                log.trace(msg);
            }
            throw new ExpiredSessionException(msg);
        }
    }

    private Map<Object, Object> getAttributesLazy() {
        Map<Object, Object> attributes = getAttributes();
        if (attributes == null) {
            attributes = new HashMap<Object, Object>();
            setAttributes(attributes);
        }
        return attributes;
    }

    @Override
    public Collection<Object> getAttributeKeys() throws InvalidSessionException {
        Map<Object, Object> attributes = getAttributes();
        if (attributes == null) {
            return Collections.emptySet();
        }
        return attributes.keySet();
    }

    @Override
    public Object getAttribute(Object key) {
        Map<Object, Object> attributes = getAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.get(key);
    }

    @Override
    public void setAttribute(Object key, Object value) {
        if (value == null) {
            removeAttribute(key);
        } else {
            getAttributesLazy().put(key, value);
        }
    }

    @Override
    public Object removeAttribute(Object key) {
        Map<Object, Object> attributes = getAttributes();
        if (attributes == null) {
            return null;
        } else {
            return attributes.remove(key);
        }
    }

    /**
     * Returns {@code true} if the specified argument is an {@code instanceof} {@code SimpleSession} and both
     * {@link #getId() id}s are equal.  If the argument is a {@code SimpleSession} and either 'this' or the argument
     * does not yet have an ID assigned, the value of {@link #onEquals(SimpleSession) onEquals} is returned, which
     * does a necessary attribute-based comparison when IDs are not available.
     * <p/>
     * Do your best to ensure {@code SimpleSession} instances receive an ID very early in their lifecycle to
     * avoid the more expensive attributes-based comparison.
     *
     * @param obj the object to compare with this one for equality.
     * @return {@code true} if this object is equivalent to the specified argument, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SimpleSession) {
            SimpleSession other = (SimpleSession) obj;
            Serializable thisId = getId();
            Serializable otherId = other.getId();
            if (thisId != null && otherId != null) {
                return thisId.equals(otherId);
            } else {
                //fall back to an attribute based comparison:
                return onEquals(other);
            }
        }
        return false;
    }

    /**
     * Provides an attribute-based comparison (no ID comparison) - incurred <em>only</em> when 'this' or the
     * session object being compared for equality do not have a session id.
     *
     * @param ss the SimpleSession instance to compare for equality.
     * @return true if all the attributes, except the id, are equal to this object's attributes.
     * @since 1.0
     */
    protected boolean onEquals(SimpleSession ss) {
        return (getStartTimestamp() != null ? getStartTimestamp().equals(ss.getStartTimestamp()) : ss.getStartTimestamp() == null) &&
                (getStopTimestamp() != null ? getStopTimestamp().equals(ss.getStopTimestamp()) : ss.getStopTimestamp() == null) &&
                (getLastAccessTime() != null ? getLastAccessTime().equals(ss.getLastAccessTime()) : ss.getLastAccessTime() == null) &&
                (getTimeout() == ss.getTimeout()) &&
                (isExpired() == ss.isExpired()) &&
                (getHost() != null ? getHost().equals(ss.getHost()) : ss.getHost() == null) &&
                (getAttributes() != null ? getAttributes().equals(ss.getAttributes()) : ss.getAttributes() == null);
    }

    /**
     * Returns the hashCode.  If the {@link #getId() id} is not {@code null}, its hashcode is returned immediately.
     * If it is {@code null}, an attributes-based hashCode will be calculated and returned.
     * <p/>
     * Do your best to ensure {@code SimpleSession} instances receive an ID very early in their lifecycle to
     * avoid the more expensive attributes-based calculation.
     *
     * @return this object's hashCode
     * @since 1.0
     */
    @Override
    public int hashCode() {
        Serializable id = getId();
        if (id != null) {
            return id.hashCode();
        }
        int hashCode = getStartTimestamp() != null ? getStartTimestamp().hashCode() : 0;
        hashCode = 31 * hashCode + (getStopTimestamp() != null ? getStopTimestamp().hashCode() : 0);
        hashCode = 31 * hashCode + (getLastAccessTime() != null ? getLastAccessTime().hashCode() : 0);
        hashCode = 31 * hashCode + Long.valueOf(Math.max(getTimeout(), 0)).hashCode();
        hashCode = 31 * hashCode + Boolean.valueOf(isExpired()).hashCode();
        hashCode = 31 * hashCode + (getHost() != null ? getHost().hashCode() : 0);
        hashCode = 31 * hashCode + (getAttributes() != null ? getAttributes().hashCode() : 0);
        return hashCode;
    }

    /**
     * Returns the string representation of this SimpleSession, equal to
     * <code>getClass().getName() + &quot;,id=&quot; + getId()</code>.
     *
     * @return the string representation of this SimpleSession, equal to
     * <code>getClass().getName() + &quot;,id=&quot; + getId()</code>.
     * @since 1.0
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(",id=").append(getId());
        return sb.toString();
    }

}
