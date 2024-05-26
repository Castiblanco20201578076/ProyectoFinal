package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import java.util.Hashtable;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CuentaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CuentaFragment extends Fragment {

    String url = "http://192.168.20.4/ProyectoFinal/login.php", correo, contrasena;

    EditText txtCorreo, txtContrasena;

    Button btnIngresar, btnRegistro;

    TextView txtOlvido;

    Intent intent;
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

        txtCorreo = txtCorreo.findViewById(R.id.txtCorreo);
        txtContrasena = txtContrasena.findViewById(R.id.txtContrasena);
        txtOlvido = txtOlvido.findViewById(R.id.txtOlvido);
        btnIngresar = btnIngresar.findViewById(R.id.btnIngresar);
        btnRegistro = btnRegistro.findViewById(R.id.btnRegistro);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = txtCorreo.getText().toString();
                contrasena = txtContrasena.getText().toString();

                if (correo.equals("") || contrasena.equals("")) {
                    Toast.makeText(getContext().getApplicationContext(), "Los datos son obligatorios", Toast.LENGTH_SHORT).show();
                    txtCorreo.requestFocus();
                } else {
                    validarUsuario(url + "login.php");
                }
            }
        });
    }

    private void validarUsuario(String URL) {

        StringRequest respuesta = new StringRequest(Request.Method.POST, url + "login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("ERROR1")) {
                    Toast.makeText(getContext().getApplicationContext(), "Completa los datos para continuar", Toast.LENGTH_SHORT).show();
                    txtCorreo.requestFocus();
                } else if (response.equalsIgnoreCase("ERROR2")) {
                    Toast.makeText(getContext().getApplicationContext(), "El correo o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                    txtCorreo.setText("");
                    txtContrasena.setText("");
                    txtCorreo.requestFocus();
                } else {
                    intent = new Intent(getContext().getApplicationContext(), AdministradorFragment.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext().getApplicationContext().getApplicationContext(), "Erro de Conexión", Toast.LENGTH_SHORT).show();
                txtCorreo.setText("");
                txtContrasena.setText("");
                txtCorreo.requestFocus();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> parametros = new Hashtable<>();
                parametros.put("email", txtCorreo.getText().toString());
                parametros.put("contrasena", txtContrasena.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(respuesta);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cuenta, container, false);
    }
}