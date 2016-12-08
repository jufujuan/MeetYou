package com.zrj.dllo.meetyou.entity;

import java.util.List;

/**
 * Created by ${ZhaoXuancheng} on 16/12/6.
 */

public class ConsBean {


    /**
     * code : 200
     * msg : success
     * newslist : [{"title":"金牛座：巨蟹座","grade":"友情：★★★爱情：★★★★婚姻：★★★★亲情：★★★★","content":"因为金牛和巨蟹都是内向型的星座，而且都是以家庭为人生最终目标，所以两个人一旦擦出火花是颇理想的一对，互相爱得对方很深很深，唯一的问题是双方的嫉妒心都很重，只要有什么风吹草动，马上变得敏感起来，不懂得控制自己的情绪，加上内敛的性格，往往变成冷战的局面。"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * title : 金牛座：巨蟹座
         * grade : 友情：★★★爱情：★★★★婚姻：★★★★亲情：★★★★
         * content : 因为金牛和巨蟹都是内向型的星座，而且都是以家庭为人生最终目标，所以两个人一旦擦出火花是颇理想的一对，互相爱得对方很深很深，唯一的问题是双方的嫉妒心都很重，只要有什么风吹草动，马上变得敏感起来，不懂得控制自己的情绪，加上内敛的性格，往往变成冷战的局面。
         */

        private String title;
        private String grade;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
