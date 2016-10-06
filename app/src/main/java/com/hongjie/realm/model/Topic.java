package com.hongjie.realm.model;

/**
 * Created by hongjiedong on 6/17/16.
 */

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Topic extends RealmObject {
    @PrimaryKey
    private String id;
    private String authorId;
    private String tab;


    private String category;
    private String content;
    private String title;
    private String lastReplyAt;
    private Boolean good;
    private Boolean top;
    private Integer replyCount;
    private Integer visitCount;
    private String createAt;
    private User author;

    public String getCategory () {
        return category;
    }

    public void setCategory (String category) {
        this.category = category;
    }

    /**
     * @return The id
     */
    public String getId () {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId (String id) {
        this.id = id;
    }

    /**
     * @return The authorId
     */
    public String getAuthorId () {
        return authorId;
    }

    /**
     * @param authorId The author_id
     */
    public void setAuthorId (String authorId) {
        this.authorId = authorId;
    }

    /**
     * @return The tab
     */
    public String getTab () {
        return tab;
    }

    /**
     * @param tab The tab
     */
    public void setTab (String tab) {
        this.tab = tab;
    }

    /**
     * @return The content
     */
    public String getContent () {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent (String content) {
        this.content = content;
    }

    /**
     * @return The title
     */
    public String getTitle () {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle (String title) {
        this.title = title;
    }

    /**
     * @return The lastReplyAt
     */
    public String getLastReplyAt () {
        return lastReplyAt;
    }

    /**
     * @param lastReplyAt The last_reply_at
     */
    public void setLastReplyAt (String lastReplyAt) {
        this.lastReplyAt = lastReplyAt;
    }

    /**
     * @return The good
     */
    public Boolean getGood () {
        return good;
    }

    /**
     * @param good The good
     */
    public void setGood (Boolean good) {
        this.good = good;
    }

    /**
     * @return The top
     */
    public Boolean getTop () {
        return top;
    }

    /**
     * @param top The top
     */
    public void setTop (Boolean top) {
        this.top = top;
    }

    /**
     * @return The replyCount
     */
    public Integer getReplyCount () {
        return replyCount;
    }

    /**
     * @param replyCount The reply_count
     */
    public void setReplyCount (Integer replyCount) {
        this.replyCount = replyCount;
    }

    /**
     * @return The visitCount
     */
    public Integer getVisitCount () {
        return visitCount;
    }

    /**
     * @param visitCount The visit_count
     */
    public void setVisitCount (Integer visitCount) {
        this.visitCount = visitCount;
    }

    /**
     * @return The createAt
     */
    public String getCreateAt () {
        return createAt;
    }

    /**
     * @param createAt The create_at
     */
    public void setCreateAt (String createAt) {
        this.createAt = createAt;
    }

    /**
     * @return The author
     */
    public User getAuthor () {
        return author;
    }

    /**
     * @param author The author
     */
    public void setAuthor (User author) {
        this.author = author;
    }

}