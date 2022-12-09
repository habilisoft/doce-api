package com.habilisoft.doce.api.email.models;

/**
 * Created on 2020-11-13.
 */
public enum EmailTemplates {
    EMAIL_INVITATION("templates/report.ftl");
    final String uri;
    EmailTemplates(String uri) {
        this.uri = uri;
    }
    public String toString() {
        return this.uri;
    }
}
