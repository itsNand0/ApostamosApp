package com.example.apostamos;

public class ListadePartidos {
    private int imagen1;
    private String club1;
    private int imgen1;
    private String club2;

    public ListadePartidos(int imagen1, String club1, int imgen1, String club2) {
        this.imagen1 = imagen1;
        this.club1 = club1;
        this.imgen1 = imgen1;
        this.club2 = club2;
    }

    public int getImagen1() {
        return imagen1;
    }

    public String getClub1() {
        return club1;
    }

    public int getImgen1() {
        return imgen1;
    }

    public String getClub2() {
        return club2;
    }
}
