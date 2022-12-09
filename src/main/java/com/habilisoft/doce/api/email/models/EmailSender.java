package com.habilisoft.doce.api.email.models;

/**
 * Created on 2019-04-08.
 */
public enum  EmailSender {
    JAVA("JAVA"), SES("SES");

    String displayName;

    EmailSender(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
