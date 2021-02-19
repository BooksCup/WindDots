package com.wd.winddots.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import com.wd.winddots.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextHighLight {

    /**
     * 关键字高亮显示
     *
     * @param text     文字
     * @param keywords 文字中的关键字数组
     * @return
     */
    public static SpannableStringBuilder matcherSearchContent(Context context, String text, String[] keywords) {

        String[] keyword = new String[keywords.length];
        System.arraycopy(keywords, 0, keyword, 0, keywords.length);
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        CharacterStyle span;
        String wordReg;
        for (int i = 0; i < keyword.length; i++) {
            String key = "";
            //  处理通配符问题
            if (keyword[i].contains("*") || keyword[i].contains("(") || keyword[i].contains(")")) {
                char[] chars = keyword[i].toCharArray();
                for (int k = 0; k < chars.length; k++) {
                    if (chars[k] == '*' || chars[k] == '(' || chars[k] == ')') {
                        key = key + "\\" + String.valueOf(chars[k]);
                    } else {
                        key = key + String.valueOf(chars[k]);
                    }
                }
                keyword[i] = key;
            }

            wordReg = "(?i)" + keyword[i];   //忽略字母大小写
            Pattern pattern = Pattern.compile(wordReg);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                span = new ForegroundColorSpan(context.getResources().getColor(R.color.high_light_red));
                spannable.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_MARK_MARK);
            }
        }

        return spannable;
    }
}