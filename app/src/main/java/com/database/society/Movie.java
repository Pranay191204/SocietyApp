package com.database.society;

public class Movie{
        public String title,date,valid_till,notice_status,content;

        public int notice_id;

        public Movie() {

        }

        public Movie(String title) {
            this.title = title;

        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNotice_id() {
            return notice_id;
        }

        public void setNotice_id(int notice_id) {
            this.notice_id = notice_id;
        }

        public String getValid_till() {
            return valid_till;
        }

        public void setValid_till(String valid_till) {
            this.valid_till = valid_till;
        }


    public String getNotice_status() {
        return notice_status;
    }

    public void setNotice_status(String notice_status) {
        this.notice_status = notice_status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}