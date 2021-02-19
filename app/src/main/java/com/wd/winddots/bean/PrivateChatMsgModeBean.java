package com.wd.winddots.bean;

public class PrivateChatMsgModeBean {

    private TextBean textBean;
    private ImageBean imageBean;
    private ChatBean chatBean;


    public TextBean getTextBean() {
        return textBean;
    }

    public void setTextBean(TextBean textBean) {
        this.textBean = textBean;
    }

    public ImageBean getImageBean() {
        return imageBean;
    }

    public void setImageBean(ImageBean imageBean) {
        this.imageBean = imageBean;
    }

    public ChatBean getChatBean() {
        return chatBean;
    }

    public void setChatBean(ChatBean chatBean) {
        this.chatBean = chatBean;
    }

    public static class TextBean{

        /**
         * extras : {}
         * text : highhighhighhighhighhighhighhigh哈哈哈哈highhigh好好highhigh哈哈哈哈highhigh哈哈哈哈highhigh好好highhigh给high即离开了苦苦解决计划哈哈哈哈
         */

        private ExtrasBean extras;
        private String text;

        public ExtrasBean getExtras() {
            return extras;
        }

        public void setExtras(ExtrasBean extras) {
            this.extras = extras;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static class ExtrasBean {
            @Override
            public String toString() {
                return "ExtrasBean{}";
            }
        }

        @Override
        public String toString() {
            return "TextBean{" +
                    "extras=" + extras +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    public static class ImageBean{

        /**
         * mediaId : qiniu/image/j/56A6D9CC30A6A1F8EF3F6A67936427C2.jpg
         * mediaCrc32 : 1906874777
         * width : 720
         * height : 1280
         * format : jpg
         * fsize : 261651
         */

        private String mediaId;
        private String media_id;
        private long mediaCrc32;
        private int width;
        private int height;
        private String format;
        private int fsize;

        public String getMedia_id() {
            return media_id;
        }

        public void setMedia_id(String media_id) {
            this.media_id = media_id;
        }

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public long getMediaCrc32() {
            return mediaCrc32;
        }

        public void setMediaCrc32(long mediaCrc32) {
            this.mediaCrc32 = mediaCrc32;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public int getFsize() {
            return fsize;
        }

        public void setFsize(int fsize) {
            this.fsize = fsize;
        }

        @Override
        public String toString() {
            return "ImageBean{" +
                    "mediaId='" + mediaId + '\'' +
                    ", media_id='" + media_id + '\'' +
                    ", mediaCrc32=" + mediaCrc32 +
                    ", width=" + width +
                    ", height=" + height +
                    ", format='" + format + '\'' +
                    ", fsize=" + fsize +
                    '}';
        }
    }

    public static class ChatBean{

        /**
         * extras : {"lastTime":"2020-02-26 09:38:48","isAllDay":1,"lastUserName":"丁二辉","questionId":"1232480097051742208","relativePhotos":"","questionAvatar":"http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/7ad83417e9414fb191e3919671b5a3ee.jpg","lastContent":"嗯摸摸摸","startTime":"","endTime":"","title":"兔兔","content":"嗯摸摸摸"}
         * text : 兔兔
         */

        private ExtrasBean extras;
        private String text;

        public ExtrasBean getExtras() {
            return extras;
        }

        public void setExtras(ExtrasBean extras) {
            this.extras = extras;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public static class ExtrasBean {
            /**
             * lastTime : 2020-02-26 09:38:48
             * isAllDay : 1
             * lastUserName : 丁二辉
             * questionId : 1232480097051742208
             * relativePhotos : 
             * questionAvatar : http://erp-cfpu-com.oss-cn-hangzhou.aliyuncs.com/7ad83417e9414fb191e3919671b5a3ee.jpg
             * lastContent : 嗯摸摸摸
             * startTime : 
             * endTime : 
             * title : 兔兔
             * content : 嗯摸摸摸
             */

            private String lastTime;
            private int isAllDay;
            private String lastUserName;
            private String questionId;
            private String relativePhotos;
            private String questionAvatar;
            private String lastContent;
            private String startTime;
            private String endTime;
            private String title;
            private String content;

            public String getLastTime() {
                return lastTime;
            }

            public void setLastTime(String lastTime) {
                this.lastTime = lastTime;
            }

            public int getIsAllDay() {
                return isAllDay;
            }

            public void setIsAllDay(int isAllDay) {
                this.isAllDay = isAllDay;
            }

            public String getLastUserName() {
                return lastUserName;
            }

            public void setLastUserName(String lastUserName) {
                this.lastUserName = lastUserName;
            }

            public String getQuestionId() {
                return questionId;
            }

            public void setQuestionId(String questionId) {
                this.questionId = questionId;
            }

            public String getRelativePhotos() {
                return relativePhotos;
            }

            public void setRelativePhotos(String relativePhotos) {
                this.relativePhotos = relativePhotos;
            }

            public String getQuestionAvatar() {
                return questionAvatar;
            }

            public void setQuestionAvatar(String questionAvatar) {
                this.questionAvatar = questionAvatar;
            }

            public String getLastContent() {
                return lastContent;
            }

            public void setLastContent(String lastContent) {
                this.lastContent = lastContent;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            @Override
            public String toString() {
                return "ExtrasBean{" +
                        "lastTime='" + lastTime + '\'' +
                        ", isAllDay=" + isAllDay +
                        ", lastUserName='" + lastUserName + '\'' +
                        ", questionId='" + questionId + '\'' +
                        ", relativePhotos='" + relativePhotos + '\'' +
                        ", questionAvatar='" + questionAvatar + '\'' +
                        ", lastContent='" + lastContent + '\'' +
                        ", startTime='" + startTime + '\'' +
                        ", endTime='" + endTime + '\'' +
                        ", title='" + title + '\'' +
                        ", content='" + content + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ChatBean{" +
                    "extras=" + extras +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PrivateChatMsgModeBean{" +
                "textBean=" + textBean +
                ", imageBean=" + imageBean +
                ", chatBean=" + chatBean +
                '}';
    }
}
