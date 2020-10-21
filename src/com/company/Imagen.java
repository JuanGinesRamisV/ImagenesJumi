package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class Imagen {
    private BufferedImage imagen;
    private int width;
    private int height;
    private int profundidad;
    private byte[] vector;

    public Imagen(String ruta) {
        try {
            this.setImagen(ImageIO.read(new File(ruta)));
            this.setWidth(this.getImagen().getWidth());
            this.setHeight(this.getImagen().getHeight());
            this.setProfundidad(3);
            this.setVector(((DataBufferByte) this.getImagen().getRaster().getDataBuffer()).getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getVector() {
        return vector;
    }

    public void setVector(byte[] vector) {
        this.vector = vector;
    }

    public void modificarImagen() {
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                int posicionVector = obtenerPosicionVector(i, j, 0);
                actualizarValorMedioBGR(posicionVector);
            }
        }
    }

    /*
    calcula el valor medio de los canales del pixel y lo actualiza en todos los canales
     */
    public void actualizarValorMedioBGR(int posicionVector) {
        int valor = 0;
        for (int i = 0; i < this.getProfundidad(); i++) {
            valor += Byte.toUnsignedInt(this.getVector()[posicionVector + i]);
        }
        valor = valor / 3;
        for (int i = 0; i < this.getProfundidad(); i++) {
            this.getVector()[posicionVector + i] = (byte) (valor);
        }
    }

    /*
    obtiene la posición del vector con las cordenadas pasadas por parametro y devuelve un int que es la posición
    del vector
     */
    public int obtenerPosicionVector(int fila, int columna, int posicion) {
        //cambiamos el valor dentro del vector
        //componentes de cada pixel
        int posicionVector = ((fila) * (this.getWidth()) * this.getProfundidad()) + ((columna) * this.getProfundidad()) + (posicion);
        return posicionVector;
    }



    public void suavizarImagen(Imagen origen, Matriz convolucion, int divisor) {
        //recorremos los pixeles de la matriz
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                for (int k = 0; k < this.getProfundidad(); k++) {
                    //si es borde no hacemos nada
                    if (i == 0 || j == 0 || i == this.getHeight() - 1 || j == this.getWidth() - 1) {
                    } else {
                        //se obtiene el valor del canal mediante la operación de convulcion y se aplica el valor
                        //nuevo al vector
                        int valor = calcularValor(origen, convolucion, i, j, k, divisor);
                        this.getVector()[obtenerPosicionVector(i, j, k)] = (byte) valor;
                    }
                }
            }
        }
    }

    public int calcularValor(Imagen origen, Matriz convolucion, int fila, int columna, int profundidad, int divisor) {
        int valor = 0;
        byte[] origenVector = origen.getVector();
        int posicionVector;

        //vamos una fila y columna para atras y luego lo corregimos en la matriz de convolucion
        for (int i = -1; i < convolucion.getFilas() - 1; i++) {
            for (int j = -1; j < convolucion.getColumnas() - 1; j++) {
                posicionVector = obtenerPosicionVector(fila + i, columna + j, profundidad);
                valor += Byte.toUnsignedInt(origenVector[posicionVector]) * convolucion.getValores()[i + 1][j + 1];
            }
        }
        //obtenemos el valor final si es mayor que 255 se hace 255 y si es menor que cero se hace 0
        valor = valor / divisor;
        if (valor > 255) {
            valor = 255;
        } else if (valor < 0) {
            valor = 0;
        }
        return valor;
    }


}
