import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tp_jdbc";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            displayStudents(connection);
            insertStudent(connection, "kawtar", "samih", 25);

            displayStudents(connection);

            updateStudent(connection, 1, "laila", "khalil", 30);
            displayStudents(connection);
            deleteStudent(connection, 3);
            displayStudents(connection);

            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayStudents(Connection connection) throws SQLException {
        String query = "SELECT * FROM etudiants";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Liste des étudiants :");
            while (resultSet.next()) {
                System.out.println(
                        "ID: " + resultSet.getInt("id") +
                        ", Nom: " + resultSet.getString("nom") +
                        ", Prénom: " + resultSet.getString("prenom") +
                        ", Age: " + resultSet.getInt("age")
                );
            }
            System.out.println();
        }
    }

    private static void insertStudent(Connection connection, String nom, String prenom, int age) throws SQLException {
        String query = "INSERT INTO etudiants (nom, prenom, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setInt(3, age);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Nouvel étudiant inséré avec succès.");
            } else {
                System.out.println("Échec de l'insertion de l'étudiant.");
            }
        }
    }

    private static void updateStudent(Connection connection, int id, String nom, String prenom, int age) throws SQLException {
        String query = "UPDATE etudiants SET nom = ?, prenom = ?, age = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Données de l'étudiant mises à jour avec succès.");
            } else {
                System.out.println("Échec de la mise à jour des données de l'étudiant.");
            }
        }
    }

    private static void deleteStudent(Connection connection, int id) throws SQLException {
        String query = "DELETE FROM etudiants WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Étudiant supprimé avec succès. Nombre de lignes affectées : " + rowsAffected);
            } else {
                System.out.println("Échec de la suppression de l'étudiant!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur  : " + e.getMessage());
        }
    }

}
