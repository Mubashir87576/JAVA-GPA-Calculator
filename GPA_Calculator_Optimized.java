import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.*; // <-- Added for file writing

class GPACGPAcalculator {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            clearScreen();
            printHeader("GPA & CGPA Calculator");

            System.out.println("1. Calculate GPA");
            System.out.println("2. Calculate CGPA");
            System.out.println("3. How GPA & CGPA are calculated");
            System.out.println("4. View Grade Scale"); // <-- Added new option
            System.out.println("5. View Saved Results");
            System.out.println("6. Exit");
            System.out.print("\nEnter your choice: ");
            int choice = inputIntValidation("");  // Changed from inputValidation to inputIntValidation

            switch (choice) {
                case 1:
                    calculateGPA();
                    break;
                case 2:
                    calculateCGPA();
                    break;
                case 3:
                    displayMethod();
                    break;
                case 4:
                    showGradeScale(); // <-- Added call
                    break;
                 case 5:
                    viewSavedResults(); // NEW CASE
                    break;
                case 6:
                    exitApplication();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine(); // Wait for user to press enter
        }
    }

//    private static void clearScreen() {
//        // Clear the screen - simulated by printing new lines
//        for (int i = 0; i < 50; ++i) System.out.println();
//    }
    //  1 Reason: Uses ANSI escape codes to actually clear the console in most modern terminals
    private static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
}


    private static void printHeader(String title) {
        System.out.println(String.format("%50s", "").replace(' ', '-'));
        System.out.printf("%-25s%s%n", "", title);
        System.out.println(String.format("%50s", "").replace(' ', '-'));
        System.out.println();
    }

    private static void calculateGPA() {
        clearScreen();
        printHeader("GPA Calculation");

        System.out.print("Enter the number of subjects: ");
        int numSubjects = inputIntValidation("");  // Changed from inputValidation

        List<Float> credits = new ArrayList<>();
        List<Float> gradePoints = new ArrayList<>();
                
//        List<Float> credits = new ArrayList<>();
//            List<Float> grades = new ArrayList<>();
//            for (int i = 0; i < numSubjects; ++i) {
//                ...
//                credits.add(credit);
//                grades.add(grade);
//            }

// 3 Reason: Removed unnecessary ArrayList, memory-efficient, real-time processing.
        float totalCredits = 0, weightedSum = 0;

        for (int i = 0; i < numSubjects; ++i) {
            System.out.print("      credit hours for subject " + (i + 1) + ": ");
            float credit = inputFloatValidation("");  // Changed from inputValidation
            credits.add(credit);

            System.out.print("      marks (out of 100) for subject " + (i + 1) + ": ");
            float marks = inputFloatValidation("");  // Changed from inputValidation

            float gradePoint = convertMarksToGradePoint(marks);  // Added conversion logic
            gradePoints.add(gradePoint);

            weightedSum += credit * gradePoint;
            totalCredits += credit;
        }

//        System.out.printf("Your GPA is: %.2f%n", weightedSum / totalCredits);
        
        float gpa = weightedSum / totalCredits;
        System.out.printf("Your GPA is: %.2f%n", gpa);
        System.out.print("Do you want to save this result to a file? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            saveToFile("GPA: " + gpa, "results.txt");
        }
    }

    private static void calculateCGPA() {
        clearScreen();
        printHeader("CGPA Calculation");

        System.out.print("Enter the number of semesters: ");
        int numSemesters = inputIntValidation("");  // Changed from inputValidation
        float sumGPA = 0;
//            for (int i = 0; i < numSemesters; ++i) {
//                System.out.print("Enter GPA for semester " + (i + 1) + ": ");
//                sumGPA += inputValidation("");
//            }
//            System.out.printf("Your CGPA is: %.2f%n", sumGPA / numSemesters);
// 5  Reason: Correct formula: CGPA = (Σ GPA × Credits) / Total Credits.

        float totalCredits = 0, weightedSum = 0;

        for (int i = 0; i < numSemesters; ++i) {
            System.out.print("Enter GPA for semester " + (i + 1) + ": ");
            float gpa = inputFloatValidation("");  // Changed from inputValidation

            System.out.print("Enter credit hours for semester " + (i + 1) + ": ");
            float credit = inputFloatValidation("");  // Added for CGPA correction

            weightedSum += gpa * credit;
            totalCredits += credit;
        }

//        System.out.printf("Your CGPA is: %.2f%n", weightedSum / totalCredits);
        
        float cgpa = weightedSum / totalCredits;
        System.out.printf("Your CGPA is: %.2f%n", cgpa);
        System.out.print("Do you want to save this result to a file? (yes/no): ");
        String response = scanner.nextLine().trim().toLowerCase();
        if (response.equals("yes")) {
            saveToFile("CGPA: " + cgpa, "results.txt");
        }
    }

    private static void displayMethod() {
        clearScreen();
        printHeader("Calculation Method");

        // Fixed method description
        System.out.println("GPA is calculated using the formula:");
        System.out.println("   GPA = Sum of (Grade Points * Credit Hours) / Total Credit Hours");
        System.out.println();
        System.out.println("CGPA is calculated using the formula:");
        System.out.println("   CGPA = Sum of (Semester GPA * Credit Hours) / Total Credit Hours across all semesters.\n");
    }
    
    // Add the showGradeScale() method
    private static void showGradeScale() {
    clearScreen();
    printHeader("Grade Table");
    System.out.println("Marks >= 85: 4.0");
    System.out.println("80 - 84   : 3.7");
    System.out.println("75 - 79   : 3.3");
    System.out.println("70 - 74   : 3.0");
    System.out.println("65 - 69   : 2.7");
    System.out.println("60 - 64   : 2.3");
    System.out.println("50 - 59   : 2.0");
    System.out.println("Below 50  : 0.0 (Fail)");
}

    private static void exitApplication() {
        clearScreen();
        System.out.println("Thank you for using the GPA & CGPA Calculator. Goodbye!");
    }

    // Added to accept positive integer input (used in menu, subject/semester count)
    private static int inputIntValidation(String prompt) {
        int input = -1;
        boolean valid = false;
        while (!valid) {
            try {
                if (!prompt.isEmpty()) System.out.print(prompt);
                input = scanner.nextInt();
                if (input >= 0) {
                    valid = true;
                } else {
                    System.out.print("Invalid input. Please enter a positive number: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a positive number: ");
                scanner.next(); // consume invalid input
            }
        }
        scanner.nextLine(); // consume the newline
        return input;
    }
    
    // 2 Added to accept float/decimal input (used for marks, GPA, credit hours)
    private static float inputFloatValidation(String prompt) {
        float input = -1;
        boolean valid = false;
        while (!valid) {
            try {
                if (!prompt.isEmpty()) System.out.print(prompt);
                input = scanner.nextFloat();
                if (input >= 0) {
                    valid = true;
                } else {
                    System.out.print("Invalid input. Please enter a positive number: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a positive number: ");
                scanner.next(); // consume invalid input
            }
        }
        scanner.nextLine(); // consume the newline
        return input;
    }

    // 4 Added method to convert marks to grade point scale (0.0 to 4.0)
    private static float convertMarksToGradePoint(float marks) {
        if (marks >= 85) return 4.0f;
        else if (marks >= 80) return 3.7f;
        else if (marks >= 75) return 3.3f;
        else if (marks >= 70) return 3.0f;
        else if (marks >= 65) return 2.7f;
        else if (marks >= 60) return 2.3f;
        else if (marks >= 50) return 2.0f;
        else return 0.0f;
    }

    // 🔽 NEW METHOD: saveToFile()
    private static void saveToFile(String content, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(content);
            System.out.println("Result saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
    //  Add Method to View File Contents:
    private static void viewSavedResults() {
    clearScreen();
    printHeader("Saved GPA/CGPA Results");

    try (BufferedReader reader = new BufferedReader(new FileReader("results.txt"))) {
        String line;
        boolean empty = true;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            empty = false;
        }
        if (empty) {
            System.out.println("No results saved yet.");
        }
    } catch (IOException e) {
        System.out.println("Could not read saved results: " + e.getMessage());
    }
}

}
