package com.flowforgr.FlowForgr.shared.util;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FlowForgrStringUtil {

    private FlowForgrStringUtil(){}

    public static boolean containsIgnoreCase(String src, String what) {
        if (src == null || what == null) return false;

        int len = what.length();
        for (int i = 0; i <= src.length() - len; i++) {
            if (src.regionMatches(true, i, what, 0, len)) {
                return true;
            }
        }
        return false;
    }

    public static boolean startsWithIgnoreCase(String text, String prefix) {
        if (text == null || prefix == null) return false;
        return prefix.length() <= text.length()
                && text.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static String replaceIgnoreCase(
            String text, String search, String replacement) {

        if (text == null || search == null || search.isEmpty()) {
            return text;
        }

        StringBuilder result = new StringBuilder(text.length());
        int pos = 0;
        int idx;

        while ((idx = indexOfIgnoreCase(text, search, pos)) != -1) {
            result.append(text, pos, idx);
            result.append(replacement);
            pos = idx + search.length();
        }

        return result.append(text, pos, text.length()).toString();
    }

    public static String safeReplace(
            String text, String search, String replacement) {

        if (text == null || search == null || replacement == null) {
            return text;
        }
        return text.replace(search, replacement);
    }


    private static int indexOfIgnoreCase(String src, String search, int fromIndex) {
        int end = src.length() - search.length();
        for (int i = fromIndex; i <= end; i++) {
            if (src.regionMatches(true, i, search, 0, search.length())) {
                return i;
            }
        }
        return -1;
    }


    public static String appendAndReturnZeroPaddedToGeneratedSequence(StringBuilder sb, int seq, int width) {
        String s = Integer.toString(seq);
        int padding = width - s.length();
        for (int i = 0; i < padding; i++) {
            sb.append('0');
        }
        return sb.append(s).toString();
    }

    public static void appendZeroPaddedToGeneratedSequence(StringBuilder sb, int seq, int width) {
        String s = Integer.toString(seq);
        int padding = width - s.length();
        for (int i = 0; i < padding; i++) {
            sb.append('0');
        }
        sb.append(s);
    }

    public static String commaSeparatedJoin(String... data) {
        if (!ObjectUtils.isEmpty(data)) {
            String responseMessage = Arrays.stream(data).collect(Collectors.joining(","));
            return (isNotBlank(responseMessage) && responseMessage.endsWith(",")) ? responseMessage.substring(0,responseMessage.lastIndexOf(",")) : responseMessage;
        }
        return "";
    }

    public static String generateInitials(String name){
        if(name.length() >1) {
            String[] initials = name.split(" ");
            if (initials.length > 1) {
                if(initials.length > 2) {
                    return (initials[0].charAt(0) + "" + initials[1].charAt(0) +""+initials[2].charAt(0)).toUpperCase();
                }else{
                    return (initials[0].charAt(0) + "" + initials[1].charAt(0)).toUpperCase();
                }
            } else {
                return (name.charAt(0) + "" + name.charAt(1)).toUpperCase();
            }
        }
        return name.toUpperCase();
    }

    public static String normalizeSpaceWithinString(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            int size = str.length();
            char[] newChars = new char[size];
            int count = 0;
            int whitespacesCount = 0;
            boolean startWhitespaces = true;

            for(int i = 0; i < size; ++i) {
                char actualChar = str.charAt(i);
                boolean isWhitespace = Character.isWhitespace(actualChar);
                if (isWhitespace) {
                    if (whitespacesCount == 0 && !startWhitespaces) {
                        newChars[count++] = " ".charAt(0);
                    }

                    ++whitespacesCount;
                } else {
                    startWhitespaces = false;
                    newChars[count++] = actualChar == 160 ? 32 : actualChar;
                    whitespacesCount = 0;
                }
            }

            if (startWhitespaces) {
                return "";
            } else {
                return (new String(newChars, 0, count - (whitespacesCount > 0 ? 1 : 0))).trim();
            }
        }
    }

    public static String normalizedAndCleanAllWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        } else {
            int sz = str.length();
            char[] chs = new char[sz];
            int count = 0;

            for(int i = 0; i < sz; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    chs[count++] = str.charAt(i);
                }
            }

            if (count == sz) {
                return str;
            } else if (count == 0) {
                return "";
            } else {
                return new String(chs, 0, count);
            }
        }
    }

    public static List<String> convertCommaSparatedToList(String str) {
        if (isNotBlank(str)) {
            String[] commaSeparated = str.split(",");
            return (!ObjectUtils.isEmpty(commaSeparated))?Arrays.stream(commaSeparated)
                    .filter(FlowForgrStringUtil::isNotBlank).toList(): new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public static List<String> convertCommaSparatedToList(String str, String delimiter) {
        if (isNotBlank(str)) {
            String[] commaSeparated = StringUtils.split(str, delimiter);
            return (!ObjectUtils.isEmpty(commaSeparated))?Arrays.stream(commaSeparated)
                    .filter(FlowForgrStringUtil::isNotBlank).toList(): new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public static String trimLeadingAndTrailingWhiteSpace(String data){
        if(isNotBlank(data)) {
            data = data.stripLeading();
            data = data.stripTrailing();
        }
        return data;
    }


    public static boolean isEmpty(CharSequence str) {
        return str == null || str.toString().trim().length() == 0;
    }

    public static boolean isNumeric(CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for(int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String readInputStreamContent(InputStream inputStream){
        StringBuffer responseContent = new StringBuffer();
        try{
            if(inputStream.available() >0) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                bufferedReader.lines().forEach(responseContent::append);
            }
        }catch(Exception err){
            err.printStackTrace();
        }
        return responseContent.toString();
    }

    private static boolean between(LocalTime start, LocalTime end) {
        LocalTime currentTime = LocalTime.now();
        return (!currentTime.isBefore(start)) && currentTime.isBefore(end);
    }



    public static String displayDateFormat(){
        return "EEE |dd| MMM, yyyy";
    }

    public static String displayFullDateFormat(){
        return "MMMM |dd| yyyy";
    }

    public static String displayDateTimeFormat(){
        return "MMM |dd|, yyyy hh:mm a";
    }

    public static String statementDateTimeFormat(){
        return "EEEE |dd| MMMM, yyyy hh:mm a";
    }

    public static String wrapDateString(String dateData){
        if(isNotBlank(dateData)) {
            String date = dateData.substring(dateData.indexOf("|"), dateData.lastIndexOf("|") + 1);
            return dateData.replace(date, normalizedDay(date.replace("|", "")));
        }
        return "";
    }
    public static String normalizedDay(String day) {
        if(day.startsWith("0")){
            day = day.substring(1,day.length());
        }
        if (day.equals("0")) {
            return "0";
        } else if (!day.equals("11") && !day.equals("12") && !day.equals("13")) {
            if (day.endsWith("1")) {
                return day + "st";
            } else if (day.endsWith("2")) {
                return day + "nd";
            } else {
                return (day.endsWith("3") ? day + "rd" : day + "th");
            }
        } else {
            return day + "th";
        }
    }

    public static String createMaskString(String mask, int repeat) {
        AtomicReference<String> atomicMaskReference = new AtomicReference("");
        IntStream.range(0, repeat).forEach((range) -> {
            atomicMaskReference.set(((String)atomicMaskReference.get()).concat(mask));
        });
        return atomicMaskReference.get();
    }

    public static String maskMobileNumber(String mobileNumber) {
        if (!StringUtils.isEmpty(mobileNumber)) {
            StringBuilder masked = new StringBuilder();
            if (mobileNumber.length() >= 10) {
                String prefix = generateMobileMaskPrefix(mobileNumber);
                masked.append(prefix);
                String dataM = mobileNumber.substring(prefix.length());
                masked.append(createMaskString("*", dataM.length() - 2));
                masked.append(dataM.substring(dataM.length() - 2));
            }

            return masked.toString();
        } else {
            return "";
        }
    }

    public static String maskAccountNumber(String accountNumber) {
        StringBuffer accountNumberBuilder = new StringBuffer("");
        if (isNotBlank(accountNumber)) {
            for(int i = 0; i < accountNumber.length(); ++i) {
                if ((i < 2) || (i > accountNumber.length() - 4)) {
                    accountNumberBuilder.append(accountNumber.charAt(i));
                } else {
                    accountNumberBuilder.append("*");
                }
            }
        }

        return accountNumberBuilder.toString();
    }

    private static String generateMobileMaskPrefix(String mobileNumber){
        String prefix="";
        if(mobileNumber.startsWith("+")){
            prefix = mobileNumber.substring(0,6);
        }else if(mobileNumber.startsWith("234")){
            prefix = mobileNumber.substring(0,5);
        }else{
            prefix = mobileNumber.substring(0,3);
        }
        return prefix;
    }

    public static String maskEmailAddress(String email) {
        if (!StringUtils.isEmpty(email)) {
            StringBuilder masked = new StringBuilder();
            String content = email.substring(0, email.lastIndexOf("@"));
            if (content.length() > 0) {
                if (content.length() == 1) {
                    masked.append("*");
                } else {
                    masked.append(email.charAt(0));
                    int repeatContent = content.length() - 3;
                    if (repeatContent > 2) {
                        masked.append(createMaskString("*", repeatContent));
                    } else {
                        masked.append(createMaskString("*", content.length() - 1));
                    }

                    if (content.length() > 5) {
                        masked.append(content.substring(content.length() - 2, content.length()));
                    }
                }
            }

            masked.append(email.substring(email.lastIndexOf("@"), email.length()));
            return masked.toString();
        } else {
            return "";
        }
    }


    public static String resolveToCamelCase(String data) {
        if(FlowForgrStringUtil.isNotBlank(data)) {
            data = StringUtils.trimAllWhitespace(data);
            if (data.length() > 1) {
                data = data.substring(0, 1).toUpperCase().concat(data.substring(1, data.length()).toLowerCase());
            }
        }else{
            data ="";
        }
        return data;
    }

    public static String splitAndChangeToCamelCase(String data,String delimiter){
        if(data.contains(delimiter)) {
            StringBuilder stringBuilder = new StringBuilder();
            String[] dataString = StringUtils.split(data, delimiter);
            for (String rec : dataString) {
                if (!stringBuilder.isEmpty()) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(resolveToCamelCase(rec));
            }
            return stringBuilder.toString();
        }
        return data;
    }

    public static String createFormattedToken(String token, int characterTokenization){
        if(!token.contains("-")){
            token = StringUtils.trimAllWhitespace(token);
            StringBuilder tokenBuffer = new StringBuilder();
            int length = token.length();
            int counterLength =0;
            int delimiterStart =1;
            while(counterLength < token.length()){
                if(delimiterStart < characterTokenization){
                    tokenBuffer.append(token.charAt(counterLength));
                }else{
                    tokenBuffer.append(token.charAt(counterLength));
                    delimiterStart=0;
                }
                delimiterStart++;
                counterLength++;
                if(delimiterStart==1 && counterLength < (length - 1)){
                    tokenBuffer.append("-");
                }
            }
            token = tokenBuffer.toString();
        }
        return token;
    }

    public static String normalizePhoneNumber(String mobileNumber, String prefix){
        if(FlowForgrStringUtil.isNotBlank(mobileNumber)){
            if(mobileNumber.startsWith("+234")){
                mobileNumber = mobileNumber.substring(4,mobileNumber.length());
            }if(mobileNumber.startsWith("234")){
                mobileNumber = mobileNumber.substring(3,mobileNumber.length());
            }
            if(mobileNumber.length() < 11 && !mobileNumber.startsWith(prefix)){
                mobileNumber=prefix.concat(mobileNumber);
            }
        }
        return mobileNumber;
    }

    public static String resolveFullName(String firstName, String middleName, String lastName){
        StringBuilder fullNameBuilder = new StringBuilder();
        if(isNotBlank(firstName)){
            fullNameBuilder.append(firstName);
        }
        if(isNotBlank(middleName)){
            if(!fullNameBuilder.isEmpty()){
                fullNameBuilder.append(" ");
            }
            fullNameBuilder.append(middleName);
        }
        if(isNotBlank(lastName)){
            if(!fullNameBuilder.isEmpty()){
                fullNameBuilder.append(" ");
            }
            fullNameBuilder.append(lastName);
        }
        return trimLeadingAndTrailingWhiteSpace(fullNameBuilder.toString());
    }

    public static String resolveAccountGenerationName(String firstName, String middleName, String lastName){
        StringBuilder accountGeneratorNameBuilder = new StringBuilder();
        if(isNotBlank(firstName)){
            accountGeneratorNameBuilder.append(firstName.toUpperCase());
        }
        if(isNotBlank(middleName)){
            if(!accountGeneratorNameBuilder.isEmpty()){
                accountGeneratorNameBuilder.append(" ");
            }
            accountGeneratorNameBuilder.append(String.valueOf(middleName.charAt(0)).toUpperCase());
        }
        if(isNotBlank(lastName)){
            if(!accountGeneratorNameBuilder.isEmpty()){
                accountGeneratorNameBuilder.append(" ");
            }
            accountGeneratorNameBuilder.append(lastName.toUpperCase());
        }
        return  trimLeadingAndTrailingWhiteSpace(accountGeneratorNameBuilder.toString());
    }


    public static String formatMobileNumber(String mobileNumber){
        if(isNotBlank(mobileNumber)){
            if(!mobileNumber.startsWith("+")) {
                if (mobileNumber.startsWith("234")) {
                    mobileNumber = "+" + mobileNumber;
                } else if (mobileNumber.startsWith("0") || mobileNumber.startsWith("1")) {
                    mobileNumber = "+234" + mobileNumber.substring(1);
                }else{
                    if((mobileNumber.length() > 6 && mobileNumber.length() < 11) && !mobileNumber.startsWith("+") && !mobileNumber.startsWith("234")){
                        mobileNumber =  "+234"+ mobileNumber;
                    }
                }
            }
        }
        return mobileNumber;
    }

    public static String formatToNigerialMobileNumber(String mobileNumber){
        if(isNotBlank(mobileNumber)){
            mobileNumber = normalizedAndCleanAllWhitespace(mobileNumber);
            if(mobileNumber.startsWith("+234")){
                mobileNumber = mobileNumber.substring(1,mobileNumber.length());
            }if(mobileNumber.startsWith("0") || mobileNumber.startsWith("1")){
                mobileNumber = "234"+mobileNumber.substring(1,mobileNumber.length());
            }
        }
        return mobileNumber;
    }

    public static boolean isMobileNumberFormat(String mobileNumber){
        if(mobileNumber.startsWith("+")){
            mobileNumber = mobileNumber.substring(mobileNumber.indexOf("+")+1);
        }
        return (mobileNumber.matches("\\d{11}") || mobileNumber.matches("\\d{13}"));
    }

    public static String formatMobileNumberToSMSGatewayFormat(String mobileNumber){
        if(!StringUtils.isEmpty(mobileNumber)){
            if(mobileNumber.startsWith("+234")){
                mobileNumber = mobileNumber.substring(1,mobileNumber.length());
            }if(mobileNumber.startsWith("0") || mobileNumber.startsWith("1")){
                mobileNumber = "234"+mobileNumber.substring(1,mobileNumber.length());
            }
        }
        return mobileNumber;
    }


    public static boolean isEmailFormat(String emailFormat){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(emailFormat).matches();
    }

    public static String[] tokenizeString(String dataString, String delim){
        StringTokenizer stringTokenizer = new StringTokenizer(dataString,delim);
        String data[] = new String[stringTokenizer.countTokens()];
        int i=0;
        while(stringTokenizer.hasMoreTokens()){
            data[i] = stringTokenizer.nextToken();
            i++;
        }

        return data;
    }

    public static boolean isNotBlank(String data){
        return (!StringUtils.isEmpty(data) && !data.equalsIgnoreCase("null"));
    }

    public static boolean isBlank(String data){
        return !isNotBlank(data);
    }

    public static String resolveValueWithAlternativeWhenEmpty(String value,String alternateValue){
        return (isNotBlank(value))?value:alternateValue;
    }

    public static String shortNarration(String narration){
        return (narration.length() >10)?narration.substring(0,10):narration;
    }

    public static long createPage(long totalPageSize, long requestPageSize) {
        long pagesize = Math.round((totalPageSize / requestPageSize));
        if ((totalPageSize % requestPageSize) > 0) {
            pagesize += 1;
        }
        return pagesize;
    }

    public static long computedLastPage(long currentPage, long totalPageSize, long iterationCount){
        long computedLastPage = currentPage;
        if(currentPage != totalPageSize) {
            AtomicLong pageSize = new AtomicLong(0l);
            for (long counter = currentPage; counter <= totalPageSize; counter++) {
                computedLastPage+=1;
                if(pageSize.incrementAndGet() == iterationCount){
                    break;
                }
            }
        }
        return computedLastPage;
    }

    public static String numberSuffix(int number){

        String[] suffixArray = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (number % 100) {
            case 11:
            case 12:
            case 13:
                return number + "th";
            default:
                return number + suffixArray[number % 10];

        }
    }

    public static String replaceKey(String data, String key, String newKeyValue){
        if(isNotBlank(key) && isNotBlank(data)){
            while (data.contains(key)){
                data = data.replace(key,newKeyValue);
            }
        }
        return data;
    }

    public static String replaceString(String base, String ... child){
        if(!ObjectUtils.isEmpty(child)){
            for(int i=0; i< child.length; i++){
                while(base.contains("{"+i+"}")){
                    base = base.replace("{"+i+"}",child[i]);
                }
            }
        }
        return base;
    }

}

