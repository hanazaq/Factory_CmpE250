import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Project1 {
	// should read from input file line by line
	// write to output file
	// add input and output to the program arguments
	public static void main(String[] args) {
		FactoryImpl myFactory = new FactoryImpl();
		try {
			FileWriter myWriter = new FileWriter(args[1]);
			try {
				File myObj = new File(args[0]);
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					String[] input = data.split(" ");
					String func = input[0];
					if (func.equals("AF")) {
						Product product = new Product(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
						myFactory.addFirst(product);
					} else if (func.equals("AL")) {
						Product product = new Product(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
						myFactory.addLast(product);
					} else if (func.equals("A")) {
						try {
							Product product = new Product(Integer.parseInt(input[2]), Integer.parseInt(input[3]));
							myFactory.add(Integer.parseInt(input[1]), product);
						} catch (IndexOutOfBoundsException e) {
							myWriter.write("Index out of bounds.");
							myWriter.write("\n");
						}
					} else if (func.equals("RF")) {
						try {
							myWriter.write(myFactory.removeFirst().toString());
							myWriter.write("\n");
						} catch (NoSuchElementException e) {
							myWriter.write("Factory is empty.");
							myWriter.write("\n");
						}
					} else if (func.equals("RL")) {
						try {
							myWriter.write(myFactory.removeLast().toString());
							myWriter.write("\n");
						} catch (NoSuchElementException e) {
							myWriter.write("Factory is empty.");
							myWriter.write("\n");
						}
					} else if (func.equals("RI")) {
						try {
							myWriter.write(myFactory.removeIndex(Integer.parseInt(input[1])).toString());
							myWriter.write("\n");
						} catch (IndexOutOfBoundsException e) {
							myWriter.write("Index out of bounds.");
							myWriter.write("\n");
						}
					} else if (func.equals("RP")) {
						try {
							myWriter.write(myFactory.removeProduct(Integer.parseInt(input[1])).toString());
							myWriter.write("\n");
						} catch (NoSuchElementException e) {
							myWriter.write("Product not found.");
							myWriter.write("\n");
						}
					} else if (func.equals("F")) {
						try {
							myWriter.write(myFactory.find(Integer.parseInt(input[1])).toString());
							myWriter.write("\n");
						} catch (NoSuchElementException e) {
							myWriter.write("Product not found.");
							myWriter.write("\n");
						}
					} else if (func.equals("G")) {
						try {
							myWriter.write(myFactory.get(Integer.parseInt(input[1])).toString());
							myWriter.write("\n");
						} catch (IndexOutOfBoundsException e) {
							myWriter.write("Index out of bounds.");
							myWriter.write("\n");
						}
					} else if (func.equals("U")) {
						try {
							myWriter.write(myFactory.update(Integer.parseInt(input[1]), Integer.parseInt(input[2]))
									.toString());
							myWriter.write("\n");
						} catch (NoSuchElementException e) {
							myWriter.write("Product not found.");
							myWriter.write("\n");
						}

					} else if (func.equals("FD")) {
						myWriter.write(String.valueOf(myFactory.filterDuplicates()));
						myWriter.write("\n");
					} else if (func.equals("R")) {
						myFactory.reverse();
						myWriter.write(myFactory.print());
						myWriter.write("\n");
					} else if (func.equals("P")) {
						myWriter.write(myFactory.print());
						myWriter.write("\n");
					}

//					myWriter.write("\n");
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			myWriter.close();
//			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
//		FactoryImpl myFactory2 = new FactoryImpl();
//		Product a = new Product(1, 1);
//		Product b = new Product(2, 2);
//		Product c = new Product(3, 3);
//		Product z = new Product(100, 100);
//		myFactory2.addLast(a);
//		myFactory2.addLast(b);
//		myFactory2.addLast(c);
//		myFactory2.addLast(z);
//		System.out.println(myFactory2.print());
//		myFactory2.reverse2();
//		System.out.println(myFactory2.print());

	}
}