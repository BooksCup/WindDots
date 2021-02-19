package com.wd.winddots.bean.param;

public class MessageBody {

    private TextBody textBody;


    public static class TextBody{

        /**
         * extras : {}
         * text : 111111
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
        }

        @Override
        public String toString() {
            return "TextBody{" +
                    "extras=" + extras +
                    ", text='" + text + '\'' +
                    '}';
        }
    }


    public static class ImageBody{

    }


    @Override
    public String toString() {
        return "MessageBody{" +
                "textBody=" + textBody +
                '}';
    }
}
