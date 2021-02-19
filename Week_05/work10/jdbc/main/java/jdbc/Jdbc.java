package jdbc;

import java.sql.*;
import java.util.Random;

public class Jdbc {

    public static void main(String[] args) throws SQLException {
        JdbcUtil jdbcUtil = new JdbcUtil("jdbc:mysql://localhost:3306/geektime", "root", "123456");
        Connection connection = jdbcUtil.getHikariDataSource().getConnection();
        Statement statement = connection.createStatement();

        PreparedStatement preparedStatement = connection.prepareStatement(String.format("insert into student (student.id,student.username,student.age)values (%d,'%s',%d);", new Random().nextInt(1), "lisi", 20));
        Integer rs = preparedStatement.executeUpdate();
        System.out.println("插入");


        rs = statement.executeUpdate("update student set student.username = 'lisi' where student.username='zhangsan'");
        System.out.println("修改");

        rs = statement.executeUpdate("DELETE FROM `geektime`.`student`");
        System.out.println("删除");

        //事物
        connection.setAutoCommit(false);
        try {
            preparedStatement = connection.prepareStatement(String.format("insert into student (student.id,student.username,student.age)values (%d,'%s',%d);", new Random().nextInt(1), "lisi", 20));
            rs = preparedStatement.executeUpdate();
            System.out.println("插入");
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
            System.out.println("发生异常导致回滚插入失败");
        }
        JdbcUtil.closeJDBCResourceQuiet(connection, statement, null);
    }
}
