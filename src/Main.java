import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.IOException;
public class Main {

    private static void Check_for_count(String data){
        int spaces = data.length() - data.replace(" ", "").length();
        if (spaces != 5){
            System.out.println("Введено неверное кол-во данных. Пожалуйста повторите ввод...");
            Insert_Data();
        }else{
            Check(data);
        }
    }
    public static boolean its_Date(String dateString) {
        try {
            Calendar calendar = new GregorianCalendar();
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            df.setLenient(true);
            calendar.setTime(df.parse(dateString));
            return true;
        } catch (NullPointerException | ParseException e) {
            return false;
        }
    }

    public static boolean its_Char(String dateString) {
        if(dateString.length() == 1 && (dateString.equals("f") || dateString.equals("m"))){
            return true;
        }else return false;
    }
    public static Integer its_Numerical(String dateString) {
        boolean hasDigits = false;
        int count=0;
        //System.out.printf("Длина строки %d;\n",dateString.length());
        try {
            for(int i = 0; i < dateString.length(); i++) {
                if(Character.isDigit(dateString.charAt(i))) {
                    hasDigits = true;
                    count++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            hasDigits = false;
        }finally {
            if (hasDigits == false){
                return -1;
            }else if (count == dateString.length()){
                return 0;
            }else return 1;
        }
    }
    private static void Check(String data) {
        String buff = "";
        Integer answer = 0;
        char space = ' ';
        String lastname = "";
        String name = "";
        String soname = "";
        String number_phone = "";
        String gender = null;
        String birthday = "";
        for (int i = 0; i <= data.length(); i++) {
            if (i < data.length() && data.charAt(i) != space) {
                buff = buff + data.charAt(i);
            } else {
                try {
                    answer = its_Numerical(buff);
                    if (answer == 1) {
                        throw new RuntimeException("Строка содержит цифры");
                    } else if (answer == -1) {
                        if (lastname.isEmpty()) {
                            lastname = buff;
                            System.out.printf("Фамилия - %s;\n", lastname);
                            buff = "";
                        } else if (name.isEmpty()) {
                            name = buff;
                            System.out.printf("Имя - %s;\n", name);
                            buff = "";
                        } else if (soname.isEmpty()) {
                            soname = buff;
                            System.out.printf("Отчество - %s;\n", soname);
                            buff = "";
                        } else if (its_Char(buff) == true) {
                            gender = buff;
                            System.out.printf("Пол - %s;\n", gender);
                            buff = "";
                        }
                    } else {
                        number_phone = buff;
                        System.out.printf("Номер телефона - %s;\n", number_phone);
                        buff = "";
                    }
                } catch (RuntimeException e) {
                    try {
                        if (its_Date(buff) == true) {
                            birthday = buff;
                            System.out.printf("День рождения - %s;\n", birthday);
                            buff = "";
                        }
                    } catch (Exception a) {
                        System.out.println("Ищим следующие данные");
                    }
                }
            }
        }
        String filename = lastname + ".txt";
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(lastname+" "+name+" "+soname+" "+birthday+" "+number_phone+" "+gender);
            fw.append('\n');
            fw.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    private static void Insert_Data(){
        Scanner readline = new Scanner(System.in);
        System.out.println("===================================================================");
        System.out.println("Введите последовательно Фамилию,Имя,Отчество,дату рождения,номер телефона и пол.");
        System.out.println("При вводе ФИО первое введёное слово будет считаться Фамилией, 2-ое - Именем, 3-е Отчеством");
        System.out.println("===================================================================");
        //Check_for_count(readline.nextLine());
        String buff = "Ivanov Alexander Andreevich 27.04.1990 89994106105 m";
        System.out.println(buff);
        Check_for_count(buff);
    }

    public static void main(String[] args) {
        Insert_Data();
    }
}