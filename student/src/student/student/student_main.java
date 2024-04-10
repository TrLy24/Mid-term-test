package student.student;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class student_main {

	private static int age;
	private static String encodedString;
	private static int sum;
	
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		int choice;

		do {
			System.out.println("Menu:");
			System.out.println("1. Đọc File");
			System.out.println("2. Ghi File");
			System.out.println("3. Đọc File Kết Quả");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				readFile();
				break;
			case 2:
				ghiFile();
				break;
			case 3:
				readFileKB();
				break;
			case 4:
				System.out.println("Exiting the program...");
				break;
			default:
				System.out.println("Invalid choice. Please enter a number from 1 to 4.");
			}
		} while (choice != 4);

		scanner.close();

	}

	public static void readFile() {
		// Đường dẫn tới file student.xml
		String filePath = "student.xml";

		// Get date
		LocalDate currentDate = LocalDate.now();

		// Luồng 1: Đọc file student.xml chứa thông tin của các học sinh
		try {
			// Phân tích file XML
			File file = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			// Lấy danh sách các phần tử student
			NodeList nodeList = doc.getElementsByTagName("student");

			// Xử lý từng phần tử student
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String id = element.getElementsByTagName("id").item(0).getTextContent();
					String name = element.getElementsByTagName("name").item(0).getTextContent();
					String address = element.getElementsByTagName("address").item(0).getTextContent();
					String dateOfBirth = element.getElementsByTagName("dateOfBirth").item(0).getTextContent();

					// Tính tuổi
					LocalDate birth = LocalDate.parse(dateOfBirth);
					age = Period.between(birth, currentDate).getYears();
					int months = Period.between(birth, currentDate).getMonths();
					int day = Period.between(birth, currentDate).getDays();

					// Mã Hóa
					String maHoa = String.valueOf(age) + String.valueOf(months) + String.valueOf(day);
				    encodedString = Base64.getEncoder().encodeToString(maHoa.getBytes());

					System.out.println("ID: " + id);
					System.out.println("NAME: " + name);
					System.out.println("ADDRESS: " + address);
					System.out.println("DATE OF BIRTH: " + dateOfBirth);
					System.out.println("AGE: " + age);
					System.out.println("YEARS: " + age + " - MONTHS: " + months + " - DAY: " + day);
					System.out.println("ENCODED: " + encodedString);
					System.out.println("--------------------------------------");
					
					// tổng học sinh
					sum = sum+1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void ghiFile() {
		// Sample XML data
        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<student>\n" +
                "    <student>\n" +
                "         <age>"+age+"</age>\n" +
                "         <sum>"+sum+"</sum>\n" +
                "         <isDigit>"+encodedString+"</isDigit>\n" +
                "    </student>\n" +
                "</student>";
        

        // File path to write XML data
        String filePath = "student_out.xml";

        try {
            // Create FileWriter object with file path
            FileWriter fileWriter = new FileWriter(filePath);

            // Write XML data to file
            fileWriter.write(xmlData);

            // Close the FileWriter
            fileWriter.close();

            System.out.println("XML data has been written to " + filePath + " successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("XML data has been written to " + filePath + " Error.");
        }

	}
	
	public static void readFileKB() {
		// Đường dẫn tới file student.xml
		String filePath = "student_out.xml";
		try {
			// Phân tích file XML
			File file = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);

			// Lấy danh sách các phần tử student
			NodeList nodeList = doc.getElementsByTagName("student");

			// Xử lý từng phần tử student
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String age = element.getElementsByTagName("age").item(0).getTextContent();
					String sum = element.getElementsByTagName("sum").item(0).getTextContent();
					String isDigit = element.getElementsByTagName("isDigit").item(0).getTextContent();
	
					/// giải mã
					Base64.Decoder dec = Base64.getDecoder();
					String decoded1 = new String(dec.decode(isDigit));
			        
					System.out.println("AGE: " + age);
					System.out.println("SUM HOC SINH: " + sum);
					System.out.println("IS DIGIT: " + isDigit);
					System.out.println("decoded: " + decoded1);
					System.out.println("--------------------------------------");
					
				}
			}
		} catch (Exception e) {
			System.out.println("Đọc file không thành công");
		}
	}


}