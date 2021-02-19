package jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JdbcUtil {

    private String url;
    private String userName;
    private String password;


    /**
     * 获取DataSource使用Hikari
     * @return
     */
    public DataSource getHikariDataSource() {
        HikariConfig configuration = new HikariConfig();
        configuration.setJdbcUrl(this.url);
        configuration.setUsername(this.userName);
        configuration.setPassword(this.password);
        return new HikariDataSource(configuration);
    }

    /**
     * 建立连接
     * @return
     * @throws SQLException
     */
    private Connection connect() throws SQLException {
        return getHikariDataSource().getConnection();
    }

    /**
     * 回滚
     * @param connection
     */
    public void rollback(Connection connection) {
        if (!Objects.isNull(connection)) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void closeJDBCResourceQuiet(Connection connection, Statement preparedStatement, ResultSet resultSet) {
        if (Objects.nonNull(resultSet)) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (Objects.nonNull(preparedStatement)) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (Objects.nonNull(connection)) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)throws SQLException {
        JdbcUtil jdbcUtil = new JdbcUtil("jdbc:mysql://localhost:3306/geektime", "root", "123456");
        Connection connection = jdbcUtil.connect();
        String sql = "select * from student";
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            System.out.println(resultSet.getMetaData().getColumnName(1));
        }
    }
}
