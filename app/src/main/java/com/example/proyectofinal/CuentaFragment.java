package com.example.proyectofinal;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.AsyncTaskLoader;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinal.databinding.ActivityMainBinding;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;


public class CuentaFragment extends Fragment {

    String url = "http://192.168.20.4:80/ProyectoFinal/login.php";

    Context context;

    EditText txtCorreo, txtContrasena;

    Button btnIngresar, btnRegistro;

    TextView txtOlvido;

    NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cuenta, container, false);
        txtCorreo = view.findViewById(R.id.txtCorreo);
        txtContrasena = view.findViewById(R.id.txtContrasena);
        btnIngresar = view.findViewById(R.id.btnIngresar);
        btnRegistro = view.findViewById(R.id.btnRegistro);
        txtOlvido = view.findViewById(R.id.txtOlvido);
        context = view.getContext();
        navController = Navigation.findNavController(requireActivity(), R.id.fragmentHost);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = txtCorreo.getText().toString();
                String contrasena = txtContrasena.getText().toString();

                if (correo.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(context, "Completa los datos", Toast.LENGTH_SHORT).show();
                    txtCorreo.requestFocus();
                } else {
                    validarLogin(correo, contrasena, url, view, context);
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_cuentaFragment_to_registroFragment);
            }
        });

        return view;
    }

    private void validarLogin(String correo, String contrasena, String link, View view, Context con) {
        // Crear una solicitud de cadena POST usando Volley
        StringRequest solicitud = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Manejar la respuesta del servidor
                        switch (response) {
                            case "ERROR1":
                                Toast.makeText(con, "Completa los datos", Toast.LENGTH_SHORT).show();
                                txtCorreo.requestFocus();
                                break;
                            case "ERROR2":
                                Toast.makeText(con, "El correo o contraseña no son correctas", Toast.LENGTH_SHORT).show();
                                txtCorreo.setText("");
                                txtContrasena.setText("");
                                txtCorreo.requestFocus();
                                break;
                            default:
                                navController.navigate(R.id.action_cuentaFragment_to_navAdminFragment);
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de conexión
                        Toast.makeText(con, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                        txtCorreo.setText("");
                        txtContrasena.setText("");
                        txtCorreo.requestFocus();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Agregar parámetros a la solicitud POST
                Map<String, String> parametros = new HashMap<>();
                parametros.put("email", correo);
                parametros.put("contrasena", contrasena);
                return parametros;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue colaSolicitudes = Volley.newRequestQueue(con);
        colaSolicitudes.add(solicitud);
    }
}