package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    static BufferedImage image;

    public static void main(String[] args) {

// The image URL - change to where your image file is located!

// This call returns immediately and pixels are loaded in the background

        try {
            image = ImageIO.read(new File("subaru.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

// Create a frame

        Frame frame = new Frame();

// Add a component with a custom paint method

        frame.add(new CustomPaintComponent());

// Display the frame

        int frameWidth = 1920;

        int frameHeight = 1080;

        frame.setSize(frameWidth, frameHeight);

        frame.setVisible(true);

        //creo la matriz nueva
        Matriz convulcion = new Matriz(3,3);
        convulcion.rellenarMatriz();
        convulcion.imprimirMatriz();
    }

    /**
     * To draw on the screen, it is first necessary to subclass a Component
     * and override its paint() method. The paint() method is automatically called
     * by the windowing system whenever component's area needs to be repainted.
     */
    static class CustomPaintComponent extends Component {

        String imageURL = "subaru.jpg";
        Imagen imagenSuave = new Imagen(imageURL);
        Imagen imagenBlur = new Imagen(imageURL);
        Imagen imagenSharp = new Imagen(imageURL);

        Imagen imagenOriginal = new Imagen(imageURL);

        public void paint(Graphics g) {

            // Retrieve the graphics context; this object is used to paint shapes
            Matriz convulcion = new Matriz(3,3);
            convulcion.rellenarMatriz();
            Matriz blur = new Matriz(3,3);
            blur.rellenarMatrizBlur();
            Matriz sharp = new Matriz(3,3);
            sharp.rellenarMatrizSharp();
            Graphics2D g2d = (Graphics2D) g;

            /**
             * Draw an Image object
             * The coordinate system of a graphics context is such that the origin is at the
             * northwest corner and x-axis increases toward the right while the y-axis increases
             * toward the bottom.
             */
            int x = 0;

            int y = 0;

            g2d.drawImage(image, x, y, this);
            imagenSuave.suavizarImagen(imagenOriginal,convulcion,Matriz.calcularDivisor(convulcion));
            imagenBlur.suavizarImagen(imagenOriginal,blur,Matriz.calcularDivisor(blur));
            imagenSharp.suavizarImagen(imagenOriginal,sharp,Matriz.calcularDivisor(sharp));
            //dibujamos
            g2d.drawImage(imagenSuave.getImagen(), image.getWidth(), y, this);
            g2d.drawImage(imagenBlur.getImagen(), image.getWidth()*2, y, this);
            g2d.drawImage(imagenSharp.getImagen(), image.getWidth()*3,y,this);
        }

    }
}
