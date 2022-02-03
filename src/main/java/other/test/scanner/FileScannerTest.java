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
                " или \"замена\" c учетом даты создания файла.\n" +
                "_______________________________________________________________________________________");

        System.out.println("Укажите метку диска, на котором расположены файлы, например: C  D или F, нажмите Enter:\r");
        String diskName = scanner.nextLine();
        System.out.println("_______________________________________________________________________________________");

        String pathToDocs = diskName + ":\\Data\\ДОГОВОРА общ\\Договора 2022\\";
        File isRealdir = new File(diskName + ":\\Data\\ДОГОВОРА общ\\Договора 2022\\");

        if(!isRealdir.exists()) {
            System.out.println("Неверно указана метка диска");
            System.out.println("\nНажмите клавишу Enter, чтобы выйти.");
            scanner.nextLine();
        }


        Collection<File> files = fileScanner.scan(new File(pathToDocs));

        System.out.println("Введите дату в формате DD.MM.YYYY и нажмите Enter\r");
        String date = scanner.nextLine();
        System.out.println("_______________________________________________________________________________________\n");
        int count = 0;


        File dirForInvoices = new File(diskName + ":\\Data\\ДОГОВОРА общ\\_счета на отправку\\"+date+"\\");
        dirForInvoices.mkdir();
        String dest = diskName + ":\\Data\\ДОГОВОРА общ\\_счета на отправку\\"+date+"\\";

//        try {
//            if(!dirForInvoices.exists()) {
//                System.out.println((!dirForInvoices.exists()));
//                System.out.println("Неверно указан метка диска");
//                System.out.println("\nНажмите клавишу Enter, чтобы выйти.");
//                scanner.nextLine();
//            }
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }

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
            System.out.println("\nКоличество скопированных файлов: " + count);
            System.out.println("Файлы скопированы в директорию:   " + dest);
            System.out.println("\nНажмите клавишу Enter, чтобы выйти.");
            scanner.nextLine();
        }else{
            System.out.println("Файлов для копирования на дату "+ date +" не найдено");
            System.out.println("\nНажмите клавишу Enter, чтобы выйти.");
            dirForInvoices.delete();

            scanner.nextLine();
        }
    }
}
