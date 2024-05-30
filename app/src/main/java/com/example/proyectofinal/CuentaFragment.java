package com.example.proyectofinal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CuentaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CuentaFragment extends Fragment {

    String url = "http://localhost/ProyectoFinal/login.php";

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
                    validarLogin(url);
                }
            }
        });

        return view;
    }

   /*private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String correo = "criscast2602@gmail.com";
            String contrasena = "1234";
            try {
                URL url = new URL("http://localhost/ProyectoFinal/login.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "aplication/json, charset=UTF-8");
                conn.setRequestProperty("Accept", "aplication/json");
                conn.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("email", correo);
                json.put("contrasena", contrasena);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.toString().getBytes("UTF-8");
                    os.write(input, 0, input.length);
                }

                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    boolean success = jsonResponse.getBoolean("success");
                    String message = jsonResponse.getString("message");
                    if (success) {
                        Toast.makeText(getActivity(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Ah ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "No se pudo conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    private void validarLogin(String link) {

        final String correo = txtCorreo.getText().toString().trim();
        final String contrasena = txtContrasena.getText().toString().trim();

        StringRequest respuesta;
        respuesta = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email", correo);
                parametros.put("contrasena", contrasena);
                return parametros;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(respuesta);
    }


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