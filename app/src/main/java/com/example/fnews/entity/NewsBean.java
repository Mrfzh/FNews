package com.example.fnews.entity;

import java.util.List;

/**
 * @author fengzhaohao
 * @date 2020/11/2
 */
public class NewsBean {

    private int status;
    private String msg;
    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {

        private String channel;
        private int num;
        private List<ListBean> list;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private String title;
            private String time;
            private String src;
            private String category;
            private String pic;
            private String content;
            private String url;
            private String weburl;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWeburl() {
                return weburl;
            }

            public void setWeburl(String weburl) {
                this.weburl = weburl;
            }

            @Override public String toString() {
                return "ListBean{" +
                        "title='" + title + '\'' +
                        ", time='" + time + '\'' +
                        ", src='" + src + '\'' +
                        ", category='" + category + '\'' +
                        ", pic='" + pic + '\'' +
                        ", content='" + content + '\'' +
                        ", url='" + url + '\'' +
                        ", weburl='" + weburl + '\'' +
                        '}';
            }
        }
    }

    @Override public String toString() {
        return "NewsBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}