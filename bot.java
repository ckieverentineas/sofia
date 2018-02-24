import java.util.Scanner;
import java.io.IOException;
import java.io.*;

class Bot {
	static String message = " ";         //Сообщение пользователя 
	static int max = 0;                //Вероятное сообщение
	static int[] qwest_rate = new int[1000000];       //Статус схожести сообщений
	static String[][] answer_date = new String[1000000][2]; //База ответов
	static int answer_full = 0;
	static boolean finish;

	public static void Load_date() {
		//Загружаем базу 
		try(FileReader reader = new FileReader("Base_date.txt")) {
			// cчитывание посимвольно
			int count = 0, number = 0, table = 0, error = 0; //Индекс символа, индекс массива первого столбца
			String qwest = "";                     //Предложение
			char eqw;                              //Построчная проверка
			boolean status_date = false, correct = true;
			while ((count = reader.read()) != -1) {
				eqw = (char)count;
				if ((eqw != '\\') && (eqw != '0') && (eqw != '\n') /*&& (correct = true)*/) {
					qwest = qwest + (char)count;
				}
				if ((eqw  == '\\') && (status_date == false)) {
					table = table + 1;
					status_date = true;
					answer_date[number][table] = qwest;
					qwest = "";
					number = number + 1;
					continue;
				}
				if (eqw  == '\\' && status_date == true) {
					table = table - 1;
                                        status_date = false;
                                        answer_date[number][table] = qwest;
                                        qwest = "";
					continue;
                                }
			}
			answer_full = number;
			System.out.println("Загруженно строк: " + answer_full);
			System.out.println("Приветствую вас, я жалкая пародия ИИ, и всё готово для общения с вами.");
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void Enter_answer() {
		//Принятие сообщения от пользователя
		System.out.print("Введите текст: ");
		Scanner scan = new Scanner(System.in);
		if (scan.hasNextLine()) {
			message = scan.nextLine();
			System.out.println("Человек: " + message);
		}
	}

	public static void Scanner_message() {
		//Нахождение вероятного ответа на вопрос пользователя
		int count = 0;
		String qwest = " ";
		char[] mes_eqw = message.toCharArray();
		for (int i = 1; i < answer_full; i++) {
				//преобразовываем строку в маcсив
				qwest = answer_date[i][0];
				char[] qwest_eqw = qwest.toCharArray();
				//перебираем все элементы массива
				for (int j = 0; j<qwest_eqw.length-1 && j<mes_eqw.length-1; j++) {	
					if(mes_eqw[j] == qwest_eqw[j]) {
						count = count + 1;
					}	
				}
				qwest_rate[i] = count;
				count = 0;
		}
		if (mes_eqw.length == 5) {
			if (mes_eqw[0] == '/' & mes_eqw[1] == 'e' & 
			mes_eqw[2] == 'x' &	mes_eqw[3] == 'i' & mes_eqw[4] == 't') {
				finish = true;
				System.out.print("Надеюсь, вы тратили своё драгоценное время ещё напрасней, чем за наши");
			        System.out.println(" c вами минуты соития во время совместной беседы.");
		}
		}
	}

	public static void Print_answer() {
		//Выдача ответа на вопрос пользователя
		int maxi = 0;
		max = qwest_rate[0];
		for (int i = 1; i < answer_full; i++) {
			if (max < qwest_rate[i]) {
				max = qwest_rate[i];
				maxi = i;
			}
		}
		System.out.println("Бот: " + answer_date[maxi][1]);
		System.out.println();
	}

	public static void main(String[] args) {
		finish = false;
		Load_date();
		while (finish != true) {
			Enter_answer();
			Scanner_message();
			if (finish != true) {
				Print_answer();
			}
		}
	}
}
