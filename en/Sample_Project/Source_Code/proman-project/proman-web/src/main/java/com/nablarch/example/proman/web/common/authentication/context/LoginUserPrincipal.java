package com.nablarch.example.proman.web.common.authentication.context;

import java.io.Serializable;
import java.util.Date;

/**
 * Information of logged in user
 *
 * @author Masaya Seko
 */
public class LoginUserPrincipal implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /** User ID */
    private Integer userId;

    /** Name */
    private String kanjiName;

    /** PM position flag */
    private boolean pmFlag;

    /** Date/time of last login */
    private Date lastLoginDateTime;

    /**
     * Acquires user ID.
     *
     * @return User ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Set user ID.
     *
     * @param userId User ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Acquires name in kanji.
     *
     * @return Name in kanji
     */
    public String getKanjiName() {
        return kanjiName;
    }

    /**
     * Set name in kanji.
     *
     * @param kanjiName Name in kanji
     */
    public void setKanjiName(String kanjiName) {
        this.kanjiName = kanjiName;
    }

    /**
     * Acquires PM position flag.
     *
     * @return PM position flag
     */
    public boolean isPmFlag() {
        return pmFlag;
    }

    /**
     * Set PM position flag.
     *
     * @param pmFlag PM position flag
     */
    public void setPmFlag(boolean pmFlag) {
        this.pmFlag = pmFlag;
    }

    /**
     * Acquires date/time of last login.
     *
     * @return Date/time of last login
     */
    public Date getLastLoginDateTime() {
        return lastLoginDateTime;
    }

    /**
     * Set date/time of last login.
     *
     * @param lastLoginDateTime Date/time of last login
     */
    public void setLastLoginDateTime(Date lastLoginDateTime) {
        this.lastLoginDateTime = lastLoginDateTime;
    }

}
