package org.example.demo1.DBConnection;


import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {

    private String classForName;
    private String dbDriver;
    private String bdAddress;



    private String dbPort;
    private String dbName;
    private String username;
    private String password;

    public Connector(String classForName, String dbDriver, String bdAddress,String dbPort, String dbName, String username, String password) {
        this.classForName = classForName;
        this.dbDriver = dbDriver;
        this.bdAddress = bdAddress;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    public String getClassForName() {
        return classForName;
    }

    public void setClassForName(String classForName) {
        this.classForName = classForName;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public String getBdAddress() {
        return bdAddress;
    }

    public void setBdAddress(String bdAddress) {
        this.bdAddress = bdAddress;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }
    public String url(){
        return getDbDriver()+getBdAddress()+":"+getDbPort()+"/"+getDbName();
    }
    private String url;
    private Connection connection;
    public Connection connect(Circle circle, Button btn) throws InterruptedException {
//    public Connection connect() {
        Properties props = new Properties();
        props.setProperty("user", getUsername());
        props.setProperty("password", getPassword());
        props.setProperty("ssl", "true");
        try {
            Class.forName(getClassForName());

            url = getDbDriver()+getBdAddress()+":"+getDbPort()+"/"+getDbName();
            connection = DriverManager.getConnection(url, props);
            if(connection != null){
                return connection;
            }else{
                return null;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("something wrong 1");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("something wrong 2");
            circle.setFill(Color.RED);
            btn.setText("NO!");
            Thread.sleep(5000);
            circle.setFill(Paint.valueOf("#EEEEEE"));
            btn.setText("Test");
            throw new RuntimeException(e);
        }
    }
}
