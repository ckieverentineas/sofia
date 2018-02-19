import java.util.Scanner;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

class TicTac {
	static int turn = 0, player = 1, N = 3;
	static char[][] place = new char[N][N];
        static boolean finish = false, correct, xwinh = false, xwinv = false, xwind0 = false, ywind0 = false,
		       xwind1 = false, ywind1 = false, ywinh = false, ywinv = false;

	public static void Place() {
		//Создаем поле
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
					place [i][j] = '_';
				}
			}
	}

	public static void PrintPlace() {
		//Выводим поле
		System.out.println("Текущий ход: " + turn);
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(place [i][j]);
			}
				       System.out.println();
		}
		System.out.println();
	}

	public static void PeopleTurn() {
		//Ход человека
		try {
			int x,y;
			int number = 0;
			correct = false;
			while (correct == false && finish == false) {
			System.out.print("Введите двухзначное число вида ху: ");
                	Scanner scan = new Scanner(System.in);
                	if (scan.hasNextInt()) {
				number = scan.nextInt();
			}
			else {
				System.out.println("Неккоректно введены данные, повторите ввод данных!");
				continue;
			}
                	y = (number-11) % 10;
                	x = (number-y-11) / 10;
                	System.out.println ("Вы ввели число " + number);
                	System.out.print ("x="+x);
                	System.out.println (" y="+y);
			if ((0 <= x && x < N) && (0 <= y && y < N)) {
				correct = true;
			}
			else { 
				System.out.println("Максимальный дипозон поля ху = 33");
				continue;
			}	
			if (place[x][y] == '_') {
				place[x][y] = 'X';
				player = 0;
				turn = turn + 1;
			}
			else {
				System.out.println("Место поля занято, повторите ввод.");
				correct = false;
				continue;
			}
		    }
		}
		catch (NumberFormatException e) {
                System.out.println("Please enter the number"); 
		}
	}
	
	public static void ComputerTurn() {
		//Ход компьютера
		int x,y;
		correct = false;
		while (correct == false && finish == false) {
		x = (int)(Math.random()*3);
		y = (int)(Math.random()*3);
		if (place[x][y] == '_') {
			correct = true;
			place[x][y] = 'O';
			turn = turn + 1;
			player = 1;
		}
		else {
			continue;
		}
		}
	}
	
	public static void Cheker() {
		//Проверка исхода раунда
		int xh = 0, yh = 0, xv = 0, yv = 0, xd0 = 0, yd0 = 0, xd1 = 0, yd1 = 0;	
		if ((turn > 5) && (finish == false)) {
			for (int i = 0; i < N; i++) {
				//Проверка по диагонали
                                        if (place[i][i] == 'X') {
                                                xd0 = xd0 + 1;
                                        }
                                        if (place[i][i] == 'O') {
                                                yd0 = yd0 + 1;
                                        }
					if (place[i][N-i-1] == 'X') {
                                        xd1 = xd1 + 1;
                                        }
                                        if (place[i][N-i-1] == 'O') {
                                        yd1 = yd1 + 1;
                                        }
                                        if (xd0 == 3) {
                                                xwind0 = true;
                                                finish = true;
                                        }
                                        if (yd0 == 3) {
                                                ywind0 = true;
                                                finish = true;
                                        }
                                        if (xd1 == 3) {
                                                xwind1 = true;
                                                finish = true;
                                        }
                                        if (yd1 == 3) {
                                                ywind1 = true;
                                                finish = true;
                                        }
					for (int j = 0; j < N; j++) {
						//Проверка по горизонтале
						if (place[i][j] == 'X') {
						xh = xh + 1;
						}
						if (place[i][j] == 'O') {
                                                yh = yh + 1;
						}
						if (xh == 3) {
						xwinh = true;
						finish = true;
						}	
						if (yh == 3) {
                                                ywinh = true;
						finish = true;
                                        	}

						//Проверка по горизонтале
                                        	if (place[j][i] == 'X') {
                                                xv = xv + 1;
                                        	}
                                        	if (place[j][i] == 'O') {
                                                yv = yv + 1;
                                        	}
                                        	if (xv == 3) {
                                                xwinv = true;
                                                finish = true;
                                        	}
                                        	if (yv == 3) {
                                                ywinv = true;
                                                finish = true;
                                        	}
				}
				xh = 0;
			       	yh = 0;
			       	xv = 0;
			       	yv = 0;
                        }
			xd0 = 0;
                        yd0 = 0;
                        xd1 = 0;
                        yd1 = 0;
	}
	if (turn >= 9 ) {
                        finish = true;
	}
	}
	
	public static void Finish() {
	        if ((xwinh || xwinv || xwind0 || xwind1) == true) {
                System.out.println("Человек одержал победу. Игра длилась " + turn + " ходов.");
        }
        else {
                if ((ywinh || ywinv || ywind0 || ywind1) == true) {
                        System.out.println("Компьютер одержал победу. Игра длилась " + turn + " ходов.");
                }
                else {
                        System.out.println("Господин нИкто одержал победу. Игра длилась " + turn + " ходов.");
                }
        }
	}

	public static void main (String[] args) {
	Place();
	PrintPlace();
	while (finish == false) {
		PeopleTurn();
		Cheker();
		PrintPlace();
		if (finish == false) {
			ComputerTurn();
		}
		Cheker();
		PrintPlace();
		}
	Finish();
	PrintPlace();
	}
}
