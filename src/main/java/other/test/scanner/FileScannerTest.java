package other.test.scanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Scanner;

public class FileScannerTest {
    public static void main(String[] args) throws IOException {
        FileScanner fileScanner = new RecursiveFileScanner();
        FileUtils fileUtils = new FileUtils();
        Scanner scanner = new Scanner(System.in);



        System.out.println("\nСкрипт ищет файлы Excel с расширением *.xls и *.xlsx, в названии которых присутствуют слова: \"Рахунок\"" +
                " и \"замена\" c учетом даты создания файла.\n" +
                "_______________________________________________________________________________________");

        System.out.println("Укажите метку диска, на котором расположены файлы, например C  D или F и нажмите Enter:\r");
        String diskName = scanner.nextLine();
        System.out.println("_______________________________________________________________________________________");

        System.out.println("Введите дату в формате DD.MM.YYYY и нажмите Enter\r");
        String date = scanner.nextLine();
        int count = 0;


        String pathToDocs = diskName + ":\\Data\\ДОГОВОРА общ\\Договора 2022\\";
        Collection<File> files = fileScanner.scan(new File(pathToDocs));

        File dirForInvoices = new File(diskName + ":\\Data\\ДОГОВОРА общ\\_счета на отправку\\"+date+"\\");
        dirForInvoices.mkdir();
        String dest = diskName + ":\\Data\\ДОГОВОРА общ\\_счета на отправку\\"+date+"\\";

        for (File file : files) {
            if ((fileUtils.getFileExtension(file).equals("xls") ||
                    fileUtils.getFileExtension(file).equals("xlsx"))
                        && fileUtils.isFileContainName(file)) {

                if (fileUtils.getFileCreationTime(file).equals(date)){
                    try {
                        Files.copy(file.toPath(), new File(dest + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Скопирован счет:    " + file.getName());
                        count++;
                    } catch (FileAlreadyExistsException e) {
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (count > 0) {
            System.out.println("Количество скопированных файлов: " + count);
            System.out.println("Файлы скопированы в директорию:   " + dest);

        }
    }
}
