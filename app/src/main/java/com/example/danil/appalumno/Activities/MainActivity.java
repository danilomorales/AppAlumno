package com.example.danil.appalumno.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.danil.appalumno.Adapters.AdapterAlumno;
import com.example.danil.appalumno.DAO.DaoAlumno;
import com.example.danil.appalumno.Modelo.Alumno;
import com.example.danil.appalumno.R;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etTelefono, etApellido;
    Button btnRegistrar;
    DaoAlumno daocontacto;
    List<Alumno> alumnos;
    ListView listViewContactos;
    MenuItem mSearchAction;
    boolean isSearchOpened = false;
    EditText edtSeach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        daocontacto = new DaoAlumno(this);
        if (daocontacto.getAllStudentsList().size() == 0) {


        }
        alumnos = new ArrayList<>();
        listViewContactos = (ListView) findViewById(R.id.listViewContactos);
        listViewContactos.setLongClickable(true);
        Listar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        listViewContactos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int pos, long arg3) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Eliminar")
                        .setMessage("¿Desea eliminar este contacto?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Alumno contacto = (Alumno) listViewContactos.getItemAtPosition(pos);
                                daocontacto.deleteEntry(contacto.id);
                                Listar();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(R.drawable.alert)
                        .show();
                return true;
            }
        });

        listViewContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alumno contacto = (Alumno) listViewContactos.getItemAtPosition(position);
                Intent i = new Intent(MainActivity.this, AlumnoActivity.class);
                i.putExtra("id", contacto.getId());
                i.putExtra("nombre", contacto.getNombre());
                i.putExtra("phone", contacto.getDni());
                i.putExtra("email", contacto.getApellido());
                startActivity(i);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.dialog, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialoglayout);
                etNombre = (EditText) dialoglayout.findViewById(R.id.etNombre);
                etTelefono = (EditText) dialoglayout.findViewById(R.id.etDni);
                etApellido = (EditText) dialoglayout.findViewById(R.id.etApellido);
                btnRegistrar = (Button) dialoglayout.findViewById(R.id.btnRegistrar);
                btnRegistrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!etNombre.getText().toString().equals("") &&
                                !etTelefono.getText().toString().equals("") &&
                                !etApellido.getText().toString().equals("")) {


                                Alumno contacto = new Alumno(etNombre.getText().toString(), etTelefono.getText().toString(), etApellido.getText().toString());
                                daocontacto.addContactoDetail(contacto);
                                Listar();
                                Toast.makeText(MainActivity.this, "Registrado Correctamente", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(MainActivity.this, "Ingreso los datos requeridos", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.show();
            }
        });
    }

    private void Listar() {
        alumnos = daocontacto.getAllStudentsList();
        AdapterAlumno adapterMovimiento = new AdapterAlumno(MainActivity.this, alumnos);
        listViewContactos.setAdapter(adapterMovimiento);
    }

    private void Listar(String name) {
        alumnos = daocontacto.searchContact(name);
        AdapterAlumno adapterMovimiento = new AdapterAlumno(MainActivity.this, alumnos);
        listViewContactos.setAdapter(adapterMovimiento);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
       // mSearchAction = menu.findItem(R.id.action_buscar);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
           // case R.id.action_buscar:
             //   handleMenuSearch();
               // return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
