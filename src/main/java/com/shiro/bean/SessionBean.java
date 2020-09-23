package com.shiro.bean;

import org.apache.shiro.subject.Subject;

import java.io.Serializable;

/**
 * Created by dell on 2020/9/23.
 */
public class SessionBean {

    private    String username;

    private Subject subject;

    private Serializable sessionId;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Serializable getSessionId() {
        return sessionId;
    }

    public void setSessionId(Serializable sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "SessionBean{" +
                "username='" + username + '\'' +
                ", subject=" + subject +
                ", sessionId=" + sessionId +
                '}';
    }
}
