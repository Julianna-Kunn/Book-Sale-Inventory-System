import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static Connection connection;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("1. Admin sign-up, 2. for log-in, 3. for update, 4. for delete: 5. Costumer sign-up");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                AdminSign_up();
                break;
            case 2:
                log_in();
                break;
            case 3:
                update();
                break;
            case 4:
                delete_user();
                break;
            case 5:
                CostumerSign_up();
                break;
            default:
                System.out.print("Error Invalid Input");
                break;
        }
    }

    public static int userId() {
        int maxId = getMaxId();
        maxId = maxId + 1;

        while (isUserIdExists(maxId)) {
            maxId++;
        }
        return maxId;
    }

    public static int getMaxId() {
        int maxId = 0;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/signup_schema",
                    "root",
                    "119904100297");

            String sql = "SELECT MAX(userid) FROM users";

            PreparedStatement selectionStatement = connection.prepareStatement(sql);
            ResultSet result = selectionStatement.executeQuery();

            if (result.next()) {
                maxId = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    private static boolean isUserIdExists(int maxId) {
        try {
            // Establish database connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/signup_schema",
                    "root",
                    "119904100297");

            String sql = "SELECT COUNT(userid) FROM users WHERE userid = ?";

            PreparedStatement selectionStatement = connection.prepareStatement(sql);
            selectionStatement.setInt(1, maxId);
            ResultSet result = selectionStatement.executeQuery();

            if (result.next()) {
                int count = result.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void AdminSign_up() {
        Scanner scanner = new Scanner(System.in);
        try {
            // Establishing the database connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/signup_schema",
                    "root",
                    "119904100297");

            System.out.print("Enter User Type: ");
            String userType = scanner.nextLine();
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your contact number: ");
            int contactNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            while (true) {
                System.out.print("Confirm your password: ");
                String confirmPassword = scanner.nextLine();
                if (!password.equals(confirmPassword)) {
                    System.out.println("Passwords do not match. Please try again.");
                } else {
                    break;
                }
            }

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter currentdatetime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = currentDateTime.format(currentdatetime);
            int userid = userId();

            // Syntax of MySQL in inserting data into the database
            String insertSQL = "INSERT INTO signup_schema.users (userid, user_type, name, email, contact_number, password, date_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);

            // Parameters of preparedStatement
            insertStatement.setInt(1, userid);
            insertStatement.setString(2, userType);
            insertStatement.setString(3, name);
            insertStatement.setString(4, email);
            insertStatement.setInt(5, contactNumber);
            insertStatement.setString(6, password);
            insertStatement.setString(7, formattedDate);

            // Executing the insert query para mahhibal an pila kabuok rows ang na insert adtu sa database gamit na siya for Insert, Delete ug update
            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {//if rowsAffected is greater than 0 indicates that insertion of data to the database table is sucessfull
                System.out.println("User registered successfully!");
                System.out.printf("Your UserID is: %d%n", userid);
                System.out.printf("Your Full name: %s%n", name);
                System.out.printf("Your Email: %s%n", email);
                System.out.printf("Your Contact Number: %d%n", contactNumber);
                System.out.printf("Your Password: %s%n", password);
            } else {//if it is 0 meaning is that no rows were inserted
                System.out.println("Failed to register user.");
            }

            insertStatement.close();//closing references
            connection.close();
            scanner.close();

        } catch (SQLException e) {//catch the exception if naay something nahitabo sa pag connect to the mySQL
            e.printStackTrace();//kani kay para ma trace asa nahitabo ang errors
        }
    }

    public static void CostumerSign_up() {
        Scanner scanner = new Scanner(System.in);
        try {
            // Establishing the database connection
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/signup_schema",
                    "root",
                    "119904100297");

            String userType = "Costumer";
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();
            System.out.print("Enter your contact number: ");
            int contactNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            while (true) {
                System.out.print("Confirm your password: ");
                String confirmPassword = scanner.nextLine();
                if (!password.equals(confirmPassword)) {
                    System.out.println("Passwords do not match. Please try again.");
                } else {
                    break;
                }
            }

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter currentdatetime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = currentDateTime.format(currentdatetime);
            int userid = userId();

            // Syntax of MySQL in inserting data into the database
            String insertSQL = "INSERT INTO signup_schema.users (userid, user_type, name, email, contact_number, password, date_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertSQL);

            // Parameters of preparedStatement
            insertStatement.setInt(1, userid);
            insertStatement.setString(2, userType);
            insertStatement.setString(3, name);
            insertStatement.setString(4, email);
            insertStatement.setInt(5, contactNumber);
            insertStatement.setString(6, password);
            insertStatement.setString(7, formattedDate);

            // Executing the insert query para mahhibal an pila kabuok rows ang na insert adtu sa database gamit na siya for Insert, Delete ug update
            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {//if rowsAffected is greater than 0 indicates that insertion of data to the database table is sucessfull
                System.out.println("User registered successfully!");
                System.out.printf("Your UserID is: %d%n", userid);
                System.out.printf("Your Full name: %s%n", name);
                System.out.printf("Your Email: %s%n", email);
                System.out.printf("Your Contact Number: %d%n", contactNumber);
                System.out.printf("Your Password: %s%n", password);
            } else {//if it is 0 meaning is that no rows were inserted
                System.out.println("Failed to register user.");
            }

            insertStatement.close();//closing references
            connection.close();
            scanner.close();

        } catch (SQLException e) {//catch the exception if naay something nahitabo sa pag connect to the mySQL
            e.printStackTrace();//kani kay para ma trace asa nahitabo ang errors
        }
    }

    public static void log_in() {
        Scanner scan = new Scanner(System.in);

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/signup_schema",
                    "root",
                    "119904100297");

            System.out.print("Enter your ID:");
            int id = 0;
            if (scan.hasNextInt()) {
                id = scan.nextInt();
            } else {
                System.out.println("Please enter a valid ID.");
                return;
            }
            scan.nextLine();
            System.out.print("Enter your password:");
            String pass = scan.nextLine();

            String userid = "SELECT * FROM users WHERE userid = ?";

            PreparedStatement selectionStatement = connection.prepareStatement(userid);

            selectionStatement.setInt(1, id);
            ResultSet result = selectionStatement.executeQuery();

            int valid = 0;

            while (result.next()) {
                String storedPassword = result.getString("password");
                if (pass.equals(storedPassword)) {
                    System.out.println("Login successful!");
                    int storedUserId = result.getInt("userid");
                    String storedType = result.getString("user_type");
                    String storedName = result.getString("name");

                    if ("Admin".equals(storedType)) {
                        adminPage(storedUserId, storedType, storedName);
                    } else if ("Staff".equals(storedType)) {
                        staffPage(storedUserId, storedType, storedName);
                    } else if ("Costumer".equals(storedType)) {
                        costumerPage(storedUserId, storedType, storedName);
                    }
                    valid = 1;
                    break;
                }
            }

            if (valid != 1) {
                System.out.println("Invalid ID or password. Please try again.");
            }

            result.close();
            selectionStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update() {
        Scanner scan = new Scanner(System.in);

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/signup_schema",
                    "root",
                    "119904100297");

            System.out.print("Enter ID number:");
            int id = scan.nextInt();
            scan.nextLine();

            String userid = "SELECT * FROM users WHERE userid = ?";
            PreparedStatement selectionStatement = connection.prepareStatement(userid);
            selectionStatement.setInt(1, id);
            ResultSet result = selectionStatement.executeQuery();

            if (result.next()) {
                System.out.print("Enter new user type:");
                String type = scan.nextLine();
                System.out.print("Enter new name:");
                String name = scan.nextLine();
                System.out.print("Enter new email:");
                String email = scan.nextLine();
                System.out.print("Enter new contact number:");
                int contactNumber = scan.nextInt();
                scan.nextLine();
                System.out.print("Enter new password: ");
                String password = scan.nextLine();

                while (true) {
                    System.out.print("Confirm your password: ");
                    String confirmPassword = scan.nextLine();
                    if (!password.equals(confirmPassword)) {
                        System.out.println("Passwords do not match. Please try again.");
                    } else {
                        break;
                    }
                }
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter currentdatetime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = currentDateTime.format(currentdatetime);

                String updateSQL = "UPDATE users SET user_type = ?, name = ?, email = ?, contact_number = ?, password = ?, date_time = ? WHERE userid = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSQL);

                updateStatement.setString(1, type);
                updateStatement.setString(2, name);
                updateStatement.setString(3, email);
                updateStatement.setInt(4, contactNumber);
                updateStatement.setString(5, password);
                updateStatement.setString(6, formattedDate);
                updateStatement.setInt(7, id);

                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Sucessfully Updated");
                } else {
                    System.out.println("Unable to update");
                }

            } else {
                System.out.println("User ID not found.");
            }
            result.close();
            selectionStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete_user() {
        Scanner scan = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/signup_schema",
                "root",
                "119904100297")) {

            System.out.print("Enter ID to be deleted: ");
            int id = scan.nextInt();

            // Check if the user exists before deletion
            String selectSql = "SELECT * FROM users WHERE userid = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setInt(1, id);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                int userId = result.getInt("userid");
                String userType = result.getString("user_type");
                String name = result.getString("name");
                String email = result.getString("email");
                int contactNumber = result.getInt("contact_number");
                String password = result.getString("password");

                String deleteSql = "DELETE FROM users WHERE userid = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                deleteStatement.setInt(1, id);
                int rowsAffected = deleteStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Successfully deleted");

                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = currentDateTime.format(formatter);

                    String insertSql = "INSERT INTO deleted_users.deleteusers (userid, user_type, name, email, contact_number, password, date_time) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertSql);

                    insertStatement.setInt(1, userId);
                    insertStatement.setString(2, userType);
                    insertStatement.setString(3, name);
                    insertStatement.setString(4, email);
                    insertStatement.setInt(5, contactNumber);
                    insertStatement.setString(6, password);
                    insertStatement.setString(7, formattedDate);
                    insertStatement.executeUpdate();
                } else {
                    System.out.println("Unable to delete: no userid found");
                }
            } else {
                System.out.println("Unable to delete: no userid found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void adminPage(int userId, String userType, String name) {
        System.out.printf("User ID: %d\n", userId);
        System.out.printf("User Type: %s\n", userType);
        System.out.printf("Hello: %s\n", name);
    }

    public static void staffPage(int userId, String userType, String name) {
        System.out.printf("User ID: %d\n", userId);
        System.out.printf("User Type: %s\n", userType);
        System.out.printf("Hello: %s\n", name);
    }

    public static void costumerPage(int userId, String userType, String name) {
        System.out.printf("User ID: %d\n", userId);
        System.out.printf("User Type: %s\n", userType);
        System.out.printf("Hello: %s\n", name);
    }
}
