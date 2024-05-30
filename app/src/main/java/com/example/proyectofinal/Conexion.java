package com.example.proyectofinal;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.security.Provider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion extends Service{

    private final IBinder iBinder = new LocalBinder();
    private Connection connection;

    public void onCreate() {
        super.onCreate();
        initializeConnection();
    }

    private void initializeConnection() {
        String url = "jdbc:mysql://localhost:3306/final";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public class LocalBinder extends Binder {
        public Conexion getService() {
            return Conexion.this;
        }
    }

    public boolean validarLogin(String correo, String contrasena) {
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Administrador WHERE email = ? AND contrasena = ?");
            statement.setString(1, correo);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
