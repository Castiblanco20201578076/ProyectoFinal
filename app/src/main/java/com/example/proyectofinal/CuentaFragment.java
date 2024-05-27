package com.example.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;

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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CuentaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CuentaFragment extends Fragment {

    String URL = "http://192.168.20.4/ProyectoFinal/";

    EditText txtCorreo, txtContrasena;

    Button btnIngresar, btnRegistro;

    TextView txtOlvido;

    Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cuenta, container, false);

        txtCorreo = view.findViewById(R.id.txtCorreo);
        txtContrasena = view.findViewById(R.id.txtContrasena);
        btnIngresar = view.findViewById(R.id.btnIngresar);
        btnRegistro = view.findViewById(R.id.btnRegistro);
        txtOlvido = view.findViewById(R.id.txtOlvido);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = txtCorreo.getText().toString();
                String contrasena = txtContrasena.getText().toString();

                if (correo.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(getActivity(), "Completa los datos", Toast.LENGTH_SHORT).show();
                    txtCorreo.requestFocus();
                } else {
                    new LoginTask().execute(correo, contrasena);
                }
            }
        });

        return view;


    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String correo = params[0];
            String contrasena = params[1];
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/final", "root", "")) {
                String query = "SELECT * FROM Administrador WHERE correo = ? AND contraseña = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, correo);
                    statement.setString(2, contrasena);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        return resultSet.next();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                intent = new Intent(getActivity(), AdministradorFragment.class);
                startActivity(intent);
            }else {
                Toast.makeText(getActivity(), "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private boolean validarLogin(String correo, String contrasena) {

        try (Connection connection = Conexion.getConnection()) {
            String query = "SELECT * FROM Administrador WHERE email = ? AND contrasena = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, correo);
                statement.setString(2, contrasena);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Devuelve true si se encuentra un resultado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        /*StringRequest respuesta;
        respuesta = new StringRequest(Request.Method.GET, URL + "login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("ERROR1")) {
                    Toast.makeText(getActivity(), "Completa los datos", Toast.LENGTH_SHORT).show();
                    txtCorreo.requestFocus();
                } else if (response.equalsIgnoreCase("ERROR2")) {
                    Toast.makeText(getActivity(), "El correo o contraseña no son correctas", Toast.LENGTH_SHORT).show();
                    txtCorreo.setText("");
                    txtContrasena.setText("");
                    txtCorreo.requestFocus();
                } else {
                    //Inicar la siguiente activity
                    intent = new Intent(getActivity(), AdministradorFragment.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR AL INICIAR SESION", Toast.LENGTH_SHORT).show();
                txtCorreo.setText("");
                txtContrasena.setText("");
                txtCorreo.requestFocus();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> parametros = new Hashtable<>();
                parametros.put("email", txtCorreo.getText().toString());
                parametros.put("contrasena", txtContrasena.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(respuesta);*/


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CuentaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CuentaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CuentaFragment newInstance(String param1, String param2) {
        CuentaFragment fragment = new CuentaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}