package org.project.subscriptionservice.share.entity;

public class PageNumber {

    public static int in(int page) {
        return page - 1;
    }

    public static int out(int page) {
        return page + 1;
    }

}
