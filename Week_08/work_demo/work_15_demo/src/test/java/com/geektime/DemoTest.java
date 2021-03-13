package com.geektime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootTest(classes = DemoApplication.class)
class DemoTest {

    @Test
    void contextLoads() {
    }

    @Autowired
    DataSource dataSource;

    @Test
    public void getDatasource() throws SQLException {
        Assert.notNull(dataSource.getConnection(), "don't connect Master Datasource");
    }


    @Test
    public void insertSql() throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "insert into t_order (user_id,order_id)values(%d,%d);";
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                connection.prepareStatement(String.format(sql, i, j)).execute();
            }
        }
        connection.close();
    }

    @Test
    public void selectSql() throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "select * from t_order where user_id = 66 order by order_id";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        int id = 0;
        while (resultSet.next()) {
            id++;
            System.out.println("id" + id + "userid:" + resultSet.getInt(1) + " - order_id:" + resultSet.getInt(2) + " - note:" + resultSet.getString(3));
        }
        connection.close();
    }

    @Test
    public void updateSql() throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "update t_order set note = 'Hello' where user_id = 66";
        Assert.isTrue(connection.prepareStatement(sql).executeUpdate() > 0, "修改Order失败!");
        connection.close();
    }

    @Test
    public void deleteSql() throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "delete from t_order where user_id = 66 ";
        Assert.isTrue(connection.prepareStatement(sql).executeUpdate() > 0, "修改Order失败!");
        connection.close();
    }

}
