package com.example.apostamos;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NAadaptador extends BaseAdapter {
    private Context context;
    private ArrayList<ListaApuestas> apuestas;

    public NAadaptador(Context context, ArrayList<ListaApuestas> apuestas) {
        this.context = context;
        this.apuestas = apuestas;
    }

    @Override
    public int getCount() {
        return apuestas.size();
    }

    @Override
    public Object getItem(int position) {
        return apuestas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListaApuestas listaApuestas = (ListaApuestas) getItem(position);

        View vista = LayoutInflater.from(context).inflate(R.layout.elementos_listview_nuevaapuesta,null);
        TextView apostador = vista.findViewById(R.id.tvUsuario);
        TextView apuesta = vista.findViewById(R.id.tv_apuesta);
        TextView club = vista.findViewById(R.id.tv_club);

        apostador.setText(listaApuestas.getApostador());
        club.setText(listaApuestas.getClub());
        apuesta.setText(listaApuestas.getApuesta());

        return vista;
    }
}
