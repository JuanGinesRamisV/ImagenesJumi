package com.company;

public class Matriz {
    int filas;
    int columnas;
    int[][] valores;

    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.setValores(new int[filas][columnas]);
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public int[][] getValores() {
        return valores;
    }

    public void setValores(int[][] valores) {
        this.valores = valores;
    }

    public void rellenarMatriz(){
        int valor = -1;
        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                this.getValores()[i][j]=valor;
            }
            valor++;
        }
    }

    public void rellenarMatrizBlur(){
        this.getValores()[0][0]=1;
        this.getValores()[0][1]=1;
        this.getValores()[0][2]=1;
        this.getValores()[1][0]=1;
        this.getValores()[1][1]=1;
        this.getValores()[1][2]=1;
        this.getValores()[2][0]=1;
        this.getValores()[2][1]=1;
        this.getValores()[1][2]=1;
    }

    public void rellenarMatrizSharp(){
        this.getValores()[0][0]=0;
        this.getValores()[0][1]=-1;
        this.getValores()[0][2]=0;
        this.getValores()[1][0]=-1;
        this.getValores()[1][1]=5;
        this.getValores()[1][2]=-1;
        this.getValores()[2][0]=0;
        this.getValores()[2][1]=-1;
        this.getValores()[1][2]=0;
    }

    public void imprimirMatriz(){
        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                System.out.print(this.getValores()[i][j]);
            }
            System.out.println();
        }
    }

    public static int calcularDivisor(Matriz convolucion){
        int aux=0;
        for (int i = 0; i < convolucion.getFilas(); i++) {
            for (int j = 0; j < convolucion.getColumnas(); j++) {
                aux+=convolucion.getValores()[i][j];
            }
        }
        if(aux==0){
            aux=1;
        }
        System.out.println(aux);
        return aux;
    }
}
