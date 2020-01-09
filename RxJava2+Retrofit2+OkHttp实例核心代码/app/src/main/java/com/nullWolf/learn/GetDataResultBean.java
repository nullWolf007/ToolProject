package com.nullWolf.learn;

/**
 * to be a better man.
 *
 * @author nullWolf
 * @date 2020/1/9
 */
public class GetDataResultBean {

    /**
     * status : 1
     * content : {"from":"en-EU","to":"zh-CN","vendor":"wps","out":"你好世界","ciba_use":"来自机器翻译。","ciba_out":"","err_no":0}
     */

    private int status;//请求成功返回1
    private ContentBean content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * from : en-EU
         * to : zh-CN
         * vendor : wps
         * out : 你好世界
         * ciba_use : 来自机器翻译。
         * ciba_out :
         * err_no : 0
         */

        private String from;//原文内容类型
        private String to;//译文内容类型
        private String vendor;//来源平台
        private String out;//译文内容
        private String ciba_use;
        private String ciba_out;
        private int err_no;//请求成功取0

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public String getCiba_use() {
            return ciba_use;
        }

        public void setCiba_use(String ciba_use) {
            this.ciba_use = ciba_use;
        }

        public String getCiba_out() {
            return ciba_out;
        }

        public void setCiba_out(String ciba_out) {
            this.ciba_out = ciba_out;
        }

        public int getErr_no() {
            return err_no;
        }

        public void setErr_no(int err_no) {
            this.err_no = err_no;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", vendor='" + vendor + '\'' +
                    ", out='" + out + '\'' +
                    ", ciba_use='" + ciba_use + '\'' +
                    ", ciba_out='" + ciba_out + '\'' +
                    ", err_no=" + err_no +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetDataResultBean{" +
                "status=" + status +
                ", content=" + content +
                '}';
    }
}
