import java.sql.*;

public class Database {
//    private static Database instance;

    Connection con;
    PreparedStatement stmt;
    ResultSet res;
    String connectionUrl = "jdbc:postgresql://localhost:5432/postgres";

    public Database() {////
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionUrl, "postgres", "postgres");
            System.out.println("Database connected!");
        } catch (Exception e) {
            System.out.println("Database connection failed!");
        }
    }

//    public static Database getInstance() {
//        if (Database.instance == null) {
//            Database.instance = new Database();
//        }
//        return Database.instance;
//    }

    public ResultSet query(String sql) {
        try {

            stmt = con.prepareStatement(sql);
            res = stmt.executeQuery();

//            while (res.next()) {
//                System.out.println(res);
//            }

        } catch (Exception e) {
            System.out.println("Result is null!");
        }
        return res;
    }
}
