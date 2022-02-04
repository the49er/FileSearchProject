package other.test.scanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class FileUtils {
    public String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".") + 1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }

    public String getFileCreationTime(File file) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);

        StringBuilder sbTemp = new StringBuilder();
        StringBuilder sbTemp1 = new StringBuilder();
        char[] tmpArrCreationTime = attr.creationTime().toString().toCharArray();
        for (int i = 9; i >= 0; i--) {
            //D2 D1  - M2 M1  - Y4 Y3 Y2 Y1
            // 0  1  2  3  4  5  6  7  8  9
            sbTemp.append(tmpArrCreationTime[i]);
        }
//        Date creationTime = new Date(attr.creationTime().toMillis());
//        String res = creationTime.getDate()+"."+(creationTime.getMonth()+1)+"."+(creationTime.getYear()+1900);
//        System.out.println("res = " + res);

        char[] tempArr = sbTemp.toString().toCharArray();
        char tmp1 = tempArr[0];
        tempArr[0] = tempArr[1];
        tempArr[1] = tmp1;

        tmp1 = tempArr[3];
        tempArr[3] = tempArr[4];
        tempArr[4] = tmp1;

        tmp1 = tempArr[6];
        tempArr[6] = tempArr[9];
        tempArr[9] = tmp1;

        tmp1 = tempArr[7];
        tempArr[7] = tempArr[8];
        tempArr[8] = tmp1;
        for (char c : tempArr) {
            sbTemp1.append(c);
        }
        String result = sbTemp1.toString();
        String replaceStr = result.replace("-", ".");
        return replaceStr;
    }

    public String getFileLastModifiedTime(File file) throws IOException {
        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
//        long date = attr.creationTime().toMillis();
//        Instant instant = Instant.ofEpochMilli(date);
//        System.out.println(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        StringBuilder sb = new StringBuilder();
        char[] tmpArrLastModified = attr.lastModifiedTime().toString().toCharArray();
        for (int i = 0; i <= 9; i++) {
            sb.append(tmpArrLastModified[i]);

        }
        //YYYY-MM-DD
        return sb.toString();
    }

    public boolean isFileContainName(File file) throws IOException {
        boolean isContainName = false;

        if ((file.getName().contains("Рахунок")) || (file.getName().contains("замена"))) {
            isContainName = true;
        }

        return isContainName;
    }
    
    public String insertYearFromDate(String str){

        String[] arrStr = str.split("\\.");
        String result = arrStr[arrStr.length-1];
        return result;
    }

    public boolean isValidDate(String str){
        boolean isValidDate = false;


        Pattern validDate =  Pattern.compile("[0-3][0-9]\\.[0-1][0-9]\\.[0-9][0-9][0-9][0-9]");
        if (validDate.matcher(str).matches()) {
            isValidDate = true;
        }


        return isValidDate;
    }
}
