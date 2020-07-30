package com.example.apostamos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SRadaptador extends BaseAdapter {
    private Context context;
    private ArrayList<ListaSolicitudRetiro> solicitudRetiros;

    public SRadaptador(Context context, ArrayList<ListaSolicitudRetiro> solicitudRetiros) {
        this.context = context;
        this.solicitudRetiros = solicitudRetiros;
    }

    @Override
    public int getCount() {
        return solicitudRetiros.size();
    }

    @Override
    public Object getItem(int position) {
        return solicitudRetiros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListaSolicitudRetiro listaSolicitudRetiro = (ListaSolicitudRetiro) getItem(position);
        View view = LayoutInflater.from(context).inflate(R.layout.elementos_listview_solicitud_retiro,null);
        TextView usuario = view.findViewById(R.id.textView33);
        TextView retirar = view.findViewById(R.id.textView39);
        TextView celular = view.findViewById(R.id.textView37);

        usuario.setText(listaSolicitudRetiro.getUsuario());
        retirar.setText(listaSolicitudRetiro.getRetirar());
        celular.setText(listaSolicitudRetiro.getCelular());

        return view;
    }
}
