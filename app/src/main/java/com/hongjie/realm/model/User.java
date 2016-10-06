package com.hongjie.realm.model;

/**
 * Created by hongjiedong on 6/16/16.
 */

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject{

    @PrimaryKey
    private String _id;
    private String accessToken;
    private String avatar;
    private String email;
    private String pass;
    private String loginname;
    private String name;
    private Integer v;
    private Boolean receiveAtMail;
    private Boolean receiveReplyMail;
    private Boolean active;
    private String updateAt;
    private String createAt;
    private Integer collectTopicCount;
    private Integer collectTagCount;
    private Integer followingCount;
    private Integer followerCount;
    private Integer replyCount;
    private Integer topicCount;
    private Integer score;
    private Boolean isBlock;

    public String get_id () {
        return _id;
    }

    public void set_id (String _id) {
        this._id = _id;
    }

    /**
     * @return The accessToken
     */
    public String getAccessToken () {
        return accessToken;
    }

    /**
     * @param accessToken The accessToken
     */
    public void setAccessToken (String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return The avatar
     */
    public Object getAvatar () {
        return avatar;
    }

    /**
     * @param avatar The avatar
     */
    public void setAvatar (String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return The email
     */
    public Object getEmail () {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail (String email) {
        this.email = email;
    }

    /**
     * @return The pass
     */
    public String getPass () {
        return pass;
    }

    /**
     * @param pass The pass
     */
    public void setPass (String pass) {
        this.pass = pass;
    }

    /**
     * @return The loginname
     */
    public String getLoginname () {
        return loginname;
    }

    /**
     * @param loginname The loginname
     */
    public void setLoginname (String loginname) {
        this.loginname = loginname;
    }

    /**
     * @return The name
     */
    public String getName () {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName (String name) {
        this.name = name;
    }

    /**
     * @return The v
     */
    public Integer getV () {
        return v;
    }

    /**
     * @param v The __v
     */
    public void setV (Integer v) {
        this.v = v;
    }

    /**
     * @return The receiveAtMail
     */
    public Boolean getReceiveAtMail () {
        return receiveAtMail;
    }

    /**
     * @param receiveAtMail The receive_at_mail
     */
    public void setReceiveAtMail (Boolean receiveAtMail) {
        this.receiveAtMail = receiveAtMail;
    }

    /**
     * @return The receiveReplyMail
     */
    public Boolean getReceiveReplyMail () {
        return receiveReplyMail;
    }

    /**
     * @param receiveReplyMail The receive_reply_mail
     */
    public void setReceiveReplyMail (Boolean receiveReplyMail) {
        this.receiveReplyMail = receiveReplyMail;
    }

    /**
     * @return The active
     */
    public Boolean getActive () {
        return active;
    }

    /**
     * @param active The active
     */
    public void setActive (Boolean active) {
        this.active = active;
    }

    /**
     * @return The updateAt
     */
    public String getUpdateAt () {
        return updateAt;
    }

    /**
     * @param updateAt The update_at
     */
    public void setUpdateAt (String updateAt) {
        this.updateAt = updateAt;
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
     * @return The collectTopicCount
     */
    public Integer getCollectTopicCount () {
        return collectTopicCount;
    }

    /**
     * @param collectTopicCount The collect_topic_count
     */
    public void setCollectTopicCount (Integer collectTopicCount) {
        this.collectTopicCount = collectTopicCount;
    }

    /**
     * @return The collectTagCount
     */
    public Integer getCollectTagCount () {
        return collectTagCount;
    }

    /**
     * @param collectTagCount The collect_tag_count
     */
    public void setCollectTagCount (Integer collectTagCount) {
        this.collectTagCount = collectTagCount;
    }

    /**
     * @return The followingCount
     */
    public Integer getFollowingCount () {
        return followingCount;
    }

    /**
     * @param followingCount The following_count
     */
    public void setFollowingCount (Integer followingCount) {
        this.followingCount = followingCount;
    }

    /**
     * @return The followerCount
     */
    public Integer getFollowerCount () {
        return followerCount;
    }

    /**
     * @param followerCount The follower_count
     */
    public void setFollowerCount (Integer followerCount) {
        this.followerCount = followerCount;
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
     * @return The topicCount
     */
    public Integer getTopicCount () {
        return topicCount;
    }

    /**
     * @param topicCount The topic_count
     */
    public void setTopicCount (Integer topicCount) {
        this.topicCount = topicCount;
    }

    /**
     * @return The score
     */
    public Integer getScore () {
        return score;
    }

    /**
     * @param score The score
     */
    public void setScore (Integer score) {
        this.score = score;
    }

    /**
     * @return The isBlock
     */
    public Boolean getIsBlock () {
        return isBlock;
    }

    /**
     * @param isBlock The is_block
     */
    public void setIsBlock (Boolean isBlock) {
        this.isBlock = isBlock;
    }

}