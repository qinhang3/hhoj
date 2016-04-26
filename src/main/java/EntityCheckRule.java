import java.util.List;

import com.google.gson.annotations.Expose;

public class EntityCheckRule{
        //列分隔符
        @Expose
        private String splitIn;
        //行分隔符
        @Expose
        private String splitOut;

        @Expose
        private String smsText;
        //时间格式
        @Expose
        private String timeFormat;
        //商品报警格式
        @Expose
        private String rowFormat;
        @Expose
        private List<String> regexs;

        public String getSplitIn() {
            return splitIn;
        }

        public void setSplitIn(String splitIn) {
            this.splitIn = splitIn;
        }

        public String getSplitOut() {
            return splitOut;
        }

        public void setSplitOut(String splitOut) {
            this.splitOut = splitOut;
        }

        public String getSmsText() {
            return smsText;
        }

        public void setSmsText(String smsText) {
            this.smsText = smsText;
        }

        public String getTimeFormat() {
            return timeFormat;
        }

        public void setTimeFormat(String timeFormat) {
            this.timeFormat = timeFormat;
        }

        public List<String> getRegexs() {
            return regexs;
        }

        public void setRegexs(List<String> regexs) {
            this.regexs = regexs;
        }

        public String getRowFormat() {
            return rowFormat;
        }

        public void setRowFormat(String rowFormat) {
            this.rowFormat = rowFormat;
        }
    }