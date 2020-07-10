package com.example.apostamos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MPadaptador extends BaseAdapter {

    private Context context;
    private ArrayList<ListadePartidos> juegos;

    public MPadaptador(Context context, ArrayList<ListadePartidos> juegos) {
        this.context = context;
        this.juegos = juegos;
    }

    @Override
    public int getCount() {
        return juegos.size();
    }

    @Override
    public Object getItem(int position) {
        return juegos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        ListadePartidos lista = (ListadePartidos) getItem(i);

        View vista = LayoutInflater.from(context).inflate(R.layout.elementos_listview_menuprincipal,null);
        ImageView imagen = (ImageView) vista.findViewById(R.id.iv_club);
        TextView club = (TextView) vista.findViewById(R.id.tv_apuesta);
        TextView club2 = (TextView) vista.findViewById(R.id.tv_club2);
        ImageView imagen2 = (ImageView) vista.findViewById(R.id.iv_club2);

        imagen.setImageResource(lista.getImagen1());
        club.setText(lista.getClub1());
        club2.setText(lista.getClub2());
        imagen2.setImageResource(lista.getImgen1());

        return vista;
    }
}
