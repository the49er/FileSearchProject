package other.test.scanner;

import javax.swing.filechooser.FileSystemView;
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

        long time = System.currentTimeMillis();

        System.out.println("\nСкрипт ищет файлы Excel с расширением *.xls и *.xlsx, в названии которых присутствуют слова: \"Рахунок\"" +
                " или \"замена\" c учетом даты создания файла.\n" +
                "_______________________________________________________________________________________");

        File[] paths;
        FileSystemView fsv = FileSystemView.getFileSystemView();

// returns pathnames for files and directory
        paths = File.listRoots();

// for each pathname in pathname array
        for (File path : paths) {
            // prints file and directory paths
            System.out.println("Drive Name: " + path + "   " + fsv.getSystemTypeDescription(path));
            //System.out.println("Description: "+fsv.getSystemTypeDescription(path));
        }

        System.out.println("\nУкажите метку диска, на котором расположены файлы, например: C  D или F, нажмите Enter:\r");
        String diskName = scanner.nextLine();
        System.out.println("_______________________________________________________________________________________");

        System.out.println("Введите дату в формате DD.MM.YYYY и нажмите Enter\r");
        String date = scanner.nextLine();
        System.out.println("_______________________________________________________________________________________\n");
        int count = 0;

        if(!fileUtils.isValidDate(date)) {
            System.out.println("Неверно указан формат даты");
            System.out.println("\nНажмите клавишу Enter, чтобы выйти.");
            scanner.nextLine();

        }

        String pathToDocs = diskName + ":\\Data\\ДОГОВОРА общ\\Договора "+fileUtils.insertYearFromDate(date)+"\\";
        File isRealdir = new File(diskName + ":\\Data\\ДОГОВОРА общ\\Договора "+fileUtils.insertYearFromDate(date)+"\\");

        if (!isRealdir.exists()) {
            System.out.println("Неверно указана метка диска");
            System.out.println("\nНажмите клавишу Enter, чтобы выйти.");
            scanner.nextLine();
        }


        Collection<File> files = fileScanner.scan(new File(pathToDocs));


        File dirForInvoices = new File(diskName + ":\\Data\\ДОГОВОРА общ\\_счета на отправку\\" + date + "\\");
        dirForInvoices.mkdir();
        String dest = diskName + ":\\Data\\ДОГОВОРА общ\\_счета на отправку\\" + date + "\\";

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

                if (fileUtils.getFileCreationTime(file).equals(date)) {
                    try {
                        Files.copy(file.toPath(), new File(dest + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Скопирован файл:    " + file.getName());
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
            System.out.println("Время выполнения поиска, сек: "+(float)(System.currentTimeMillis() - time)/1000+"\n");
            System.out.println("\nНажмите клавишу Enter, чтобы выйти.");

            scanner.nextLine();
        } else {
            System.out.println("Файлов для копирования на дату " + date + " не найдено");
            System.out.println("Время выполнения поиска, сек: "+(float)(System.currentTimeMillis() - time)/1000+"\n");
            System.out.println("\nНажмите клавишу Enter, чтобы выйти.");
            dirForInvoices.delete();

            scanner.nextLine();
        }
    }
}
