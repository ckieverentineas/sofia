import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

class Main {
	static void Begin() {
		//Указание параметров пользователем для совершения дальнейших преобразований
		String firstName = " ", //Первый файл
		       secondName = " ", //Второй файл
		       message = " "; //Промежуточные данные
		System.out.println("Введите путь к первому файлу: ");
		Scanner scan = new Scanner(System.in); //Инициализация сканера
		if (scan.hasNextLine()) {
			message = scan.nextLine(); //Ввод пути первого файла
			firstName = message;
			System.out.println("Введите путь ко второму файлу: ");
			message = scan.nextLine(); //Ввод пути второго файла
			secondName = message;
		Load(firstName, secondName); //Передача путей  файлов для загрузки
		scan.close();
		}
	}

	static void Load(String firstName, String secondName) {
		//Загрузка файлов
		int loading = 0;
		int count = 0; //Динамичный номер последовательности символа в файле
		char symbol = ' '; //Активный символ
		String stroke = "", //Активное слово
		       nameFile = ""; //Активное название файла
		ArrayList <String> firstData = new ArrayList<String>(); //Строки в массиве от первого файла
		ArrayList <String> secondData = new ArrayList<String>(); //Строки в массиве от второго файла
		nameFile = firstName;
		for (int i = 0; i < 2; i++) {
			if (i == 1) {
				nameFile = secondName;
			}
			try (FileReader reader = new FileReader(nameFile)) {
				while ((count = reader.read()) != -1) { //Пока файл не прочитается полностью, считывать...
					symbol = (char)count;
					if (symbol != '\n') { //Если нет перехода на новую строку, то...
						stroke = stroke + (char)count;
					} else { //Иначе
						if (i == 1) {
							secondData.add(stroke); //Запись строки в первый массив из 1-го файла
						} else {
							firstData.add(stroke); //Запись строки во второй массив из 2го файла
						}
						stroke = "";
					}
				}
			} catch(IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		System.out.println("Загружено строк из файла " + firstName + ": " + firstData.size());
		System.out.println("Загружено строк из файла " + secondName + ": " + secondData.size());
		loading = firstData.size() * secondData.size();
		Reseach(firstData, secondData, loading);
	}
		
	
	static void Reseach(ArrayList<String> firstData, ArrayList<String> secondData, int loading) {
		//Поиск дубликатов
		ArrayList<String> doubleStroke = new ArrayList<String>(); //Массив совпадений
		for (int i = 0; i < firstData.size(); i++) { //Берется каждая строка первого массива и прогоняются все строки 
			for (int j = 0; j < secondData.size(); j++) { //второго массива с каждой строкой первого
				if (firstData.get(i).equals(secondData.get(j)) == true) {
					//String temp = (String)secondData.get(j);
					doubleStroke.add(secondData.get(j)/*temp*/);
					secondData.set(j,"del");
					System.out.println(secondData.get(j));
				}
				//System.out.print(i + " " + j + "; ");
				System.out.println("Осталось просканировать: " + loading-- + " элементов.");
			}
		}
		int count = 0;
		for (int i = 0; i < secondData.size(); i++) {
			if(secondData.get(i) == "del") {
				//Очистка дубликатов
				secondData.remove(i);
				i--;
				count++;
			}
		}
		System.out.println("Найдено дубликатов: " + doubleStroke.size());
		System.out.println("Очищено дубликатов: " + count); 
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SaveResults(firstData, secondData, doubleStroke);
	}

	static void SaveResults(ArrayList<String> firstData, ArrayList<String> secondData, ArrayList<String> doubleStroke) {
		//Сохранение преобразований
		String nameFile = "", //ПРомежуточное название файла
		       Duplicate = "duplicate.txt", //Файл, в который будут сохранены дубликаты
		       clearFile = "clearFile.txt"; //Файл, в который будут сохранены очищенные от дубликатов данные
		nameFile = Duplicate; //Подготовка к запись дубликатов
		try {
			for (int i = 0; i < 2; i++) { 
				if (i == 1) {
					nameFile = clearFile; //На следующей итерации записываем очищенные от дублик-в дан-е
				}
				FileWriter Writer = new FileWriter(nameFile, false); //Запись в файл
				if (i == 0) {
					for (String line : doubleStroke) { 
						//Запись дубликатов
						Writer.write(line);
						Writer.write(System.getProperty("line.separator"));
					}
				} else {
					for (String line : secondData) {
						//Запись очищеннных данных
						Writer.write(line);
						Writer.write(System.getProperty("line.separator"));
					}
				}
				Writer.close();
			}
			System.out.println("Дубликаты записаны в файл: " + Duplicate);
			System.out.println("Очищенный файл от дубликатов сохранен в файл: " + clearFile);
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void main (String[] args) {
		//Управление подпрограммами
		Begin();
	}
}
	

