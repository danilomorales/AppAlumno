package com.example.danil.appalumno.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.danil.appalumno.Modelo.Alumno;
import com.example.danil.appalumno.R;

import java.util.List;

public class AdapterAlumno extends BaseAdapter{

    private List<Alumno> list;
    private Activity activity;

    public AdapterAlumno(Activity activity, List<Alumno> list){
        this.activity = activity;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.detalle_alumno, null);
        }
        Alumno movimiento = list.get(position);
        TextView tvNombreV = (TextView) v.findViewById(R.id.tvNombreV);
        tvNombreV.setText(movimiento.getNombre());
        TextView etApellido = (TextView) v.findViewById(R.id.tvApellido);
        etApellido.setText(movimiento.getApellido());
        TextView tvNumeroV = (TextView) v.findViewById(R.id.tvCodigo);
        tvNumeroV.setText(movimiento.getDni());

        return v;

    }
}
