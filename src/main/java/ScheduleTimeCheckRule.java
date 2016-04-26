import com.google.gson.annotations.Expose;

public class ScheduleTimeCheckRule{
        @Expose
        private String smsText;

        //时间区间格式
        @Expose
        private String sectionFormat;

        //时间格式
        @Expose
        private String timeFormat;

        @Expose
        private String split;

        public String getSmsText() {
            return smsText;
        }

        public void setSmsText(String smsText) {
            this.smsText = smsText;
        }

        public String getSectionFormat() {
            return sectionFormat;
        }

        public void setSectionFormat(String sectionFormat) {
            this.sectionFormat = sectionFormat;
        }

        public String getTimeFormat() {
            return timeFormat;
        }

        public void setTimeFormat(String timeFormat) {
            this.timeFormat = timeFormat;
        }

        public String getSplit() {
            return split;
        }

        public void setSplit(String split) {
            this.split = split;
        }
    }
