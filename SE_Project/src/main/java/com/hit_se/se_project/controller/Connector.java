package com.hit_se.se_project.controller;

import java.sql.*;

public abstract class Connector {
    String url = "jdbc:mysql://localhost:3306/exam" ;
    String username = "root" ;
    String password = "123456" ;
    Connection conn = null;
    public Statement stmt = null;
    ResultSet rs = null;

    public Connector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");  //加载数据库驱动
            conn = DriverManager.getConnection(url, username, password);  //连接数据库
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet findUser(String name) throws SQLException {
        String sql = "SELECT * FROM user WHERE name = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    public ResultSet findUsers() throws SQLException {
        String sql = "SELECT * FROM user WHERE type <> admin";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    public ResultSet findQuestion(int num) throws SQLException {
        String sql = "SELECT * FROM question WHERE num = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(num));
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    public ResultSet findQuestions() throws SQLException {
        String sql = "SELECT * FROM question";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    public ResultSet findExam(String name) throws SQLException {
        String sql = "SELECT * FROM " + name + " ORDER BY type, question ASC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        return rs;
    }

    public ResultSet findExams() throws SQLException {
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = databaseMetaData.getTables("exam", null, "%", types);
        return rs;
    }

    public void quit() throws SQLException {
        stmt.close();
        conn.close();
        rs.close();
    }
}
