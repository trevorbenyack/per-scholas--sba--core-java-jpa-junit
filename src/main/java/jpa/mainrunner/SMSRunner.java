package jpa.mainrunner;

import static java.lang.System.out;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;

/**
 * 
 * @author Harry
 * Modified by: Trevor Benyack
 * Per Scholas Java Developer
 * SBA: Core Java/JPA/JUnit
 *
 */
public class SMSRunner {

	private final Scanner sin;
	private final StringBuilder sb;

	private final CourseService courseService;
	private final StudentService studentService;
	private Student currentStudent;

	public SMSRunner() {
		sin = new Scanner(System.in);
		sb = new StringBuilder();
		courseService = new CourseService();
		studentService = new StudentService();

		// Uncomment line below to populate tables with seed data
		// new InitializerService().initializeData();
	}

	public static void main(String[] args) {
		SMSRunner sms = new SMSRunner();
		sms.run();
	}

	private void run() {
		// Login or quit
		switch (menu1()) {
		case 1:
			if (studentLogin()) {
				registerMenu();
			} break;
		case 2:
			out.println("Goodbye!");
			break;
		default:
		}
	} // end run()

	private int menu1() {
		sb.append("\n1. Student Login\n2. Quit Application\nPlease Enter Selection: ");
		out.print(sb);
		sb.delete(0, sb.length());
		return sin.nextInt();
	} // end menu1()

	private boolean studentLogin() {
		out.print("Enter your email address: ");
		String email = sin.next();
		out.print("Enter your password: ");
		String password = sin.next();
		out.println();


		if (studentService.validateStudent(email, password)) {
			currentStudent = studentService.getStudentByEmail(email);
			List<Course> courses = studentService.getStudentCourses(email);
			if (courses.size() != 0) {
				out.println("Your Classes:");
				printCourses(courses);
			} else {
				out.println("You're not registered in any classes.");
			}
			return true;
		} else {
			out.println("User Validation failed. GoodBye!");
			return false;
		}
	} // end studentLogin()

	private void registerMenu() {
		sb.append("\n1. Register a class\n2. Logout\nPlease Enter Selection: ");
		out.print(sb);
		sb.delete(0, sb.length());

		switch (sin.nextInt()) {
		case 1: // Register a class
			List<Course> allCourses = courseService.getAllCourses();
			List<Course> studentCourses = studentService.getStudentCourses(currentStudent.getSEmail());
			if(studentCourses != null) {
				allCourses.removeAll(studentCourses);
			}
			out.println("\nAvailable Courses:");
			printCourses(allCourses);
			out.println();
			out.print("Enter Course Number: ");
			int number;

			// This try/catch block will catch non-integer input, and the inner
			// conditional will catch integer values that do not correspond to
			// any course id
			try {
				number = sin.nextInt();
				Course newCourse = courseService.getCourseById(number);
				if (newCourse != null) {
					studentService.registerStudentToCourse(currentStudent.getSEmail(), newCourse);
					Student temp = studentService.getStudentByEmail(currentStudent.getSEmail());

					List<Course> sCourses = studentService.getStudentCourses(temp.getSEmail());

					out.println("\nYour Classes:");
					printCourses(sCourses);
				} else {
					out.println("That course does not exist");
				}
			} catch (InputMismatchException e) {
				out.println("Entry is not valid");
			} break;
		case 2: // exit
		default:
			out.println("Goodbye!");
		}
	} // end registerMenu()

	private void printCourses(List<Course> courses) {
		out.printf("%n%-5s%-30s%-15s", "ID", "Course", "Instructor");
		courses.forEach(c -> out.printf("%n%-5s%-30s%-15s", c.getCId(), c.getCName(), c.getCInstructorName()));
		out.println();
	}
}
