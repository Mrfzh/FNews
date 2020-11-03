package com.example.fnews.entity;

/**
 * @author fengzhaohao
 * @date 2020/11/3
 */
public class NewsData {
    private String title;
    private String src;
    private String time;
    private String pic;

    public NewsData(String title, String src, String time, String pic) {
        this.title = title;
        this.src = src;
        this.time = time;
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
