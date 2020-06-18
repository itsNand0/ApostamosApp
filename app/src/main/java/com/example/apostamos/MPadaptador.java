package com.example.apostamos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MPadaptador extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    String[] datos;
    int[] img_club;

    public MPadaptador (Context context, String[] datos, int[] imagenes ){
        this.context = context;
        this.datos = datos;
        this.img_club = imagenes;

        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img_club.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elementos_listview_menuprincipal,null );
        TextView club = (TextView) vista.findViewById(R.id.tv_club);
        TextView club2 = (TextView) vista.findViewById(R.id.tv_club2);
        ImageView imagen = (ImageView) vista.findViewById(R.id.iv_club);
        ImageView imagen2 = (ImageView) vista.findViewById(R.id.iv_club2);
        club.setText(datos[7]);
        club2.setText(datos[1]);
        imagen.setImageResource(img_club[7]);
        imagen2.setImageResource(img_club[1]);

        return vista;
    }
}
