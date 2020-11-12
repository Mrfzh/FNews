package com.example.fnews.entity;

import java.util.List;

/**
 * @author fengzhaohao
 * @date 2020/11/12
 */
public class ChannelBean {

    /**
     * status : 0
     * msg : ok
     * result : ["头条","新闻","国内","国际","政治","财经","体育","娱乐","军事","教育","科技","NBA","股票","星座","女性","健康","育儿"]
     */

    private int status;
    private String msg;
    private List<String> result;

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

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
