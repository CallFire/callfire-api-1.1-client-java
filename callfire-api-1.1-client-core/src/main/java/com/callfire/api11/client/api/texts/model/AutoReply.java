package com.callfire.api11.client.api.texts.model;

import com.callfire.api11.client.api.common.model.CfApi11Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Auto-Replies are text message replies sent to a customer when a customer replies to a text message from a campaign.
 * A keyword or number will need to have been purchased before an Auto-Reply can be created.
 */
public class AutoReply extends CfApi11Model {
    @JsonProperty("@id")
    private Long id;
    private String number;
    private String keyword;
    private String match;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get auto-reply's phone number
     *
     * @return phone number in E.164 11 digit format
     */
    public String getNumber() {
        return number;
    }

    /**
     * Phone number to configure an auto reply message
     *
     * @param number phone number in E.164 11 digit format
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Get auto reply's keyword
     *
     * @return keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Set Keyword for text auto reply
     *
     * @param keyword auto reply's keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Get matching text
     *
     * @return matching text
     */
    public String getMatch() {
        return match;
    }

    /**
     * Set matching text
     * <p>
     * Matching text is either null or empty which represents all matches
     * all other text, for example rocks, will be matched as case insensitive whole words.
     *
     * @param match matching text
     */
    public void setMatch(String match) {
        this.match = match;
    }

    /**
     * Get auto-reply's message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set auto-reply's message
     *
     * @param message message text
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("id", id)
            .append("number", number)
            .append("keyword", keyword)
            .append("match", match)
            .append("message", message)
            .toString();
    }
}
