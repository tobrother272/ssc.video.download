
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Utils;
import Utils.MyFileUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import static Utils.Constants.FONT_ARIALUNI;

/**
 * @author inet
 */
public class Graphics {

    static double initialX;
    static double initialY;

    public static void addDragListeners(final Node n) {
        n.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    initialX = me.getSceneX();
                    initialY = me.getSceneY();
                } else {
                    n.getScene().getWindow().centerOnScreen();
                    initialX = n.getScene().getWindow().getX();
                    initialY = n.getScene().getWindow().getY();
                }
            }
        });
        n.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (me.getButton() != MouseButton.MIDDLE) {
                    n.getScene().getWindow().setX(me.getScreenX() - initialX);
                    n.getScene().getWindow().setY(me.getScreenY() - initialY);
                }
            }
        });
    }



    public static String makeThumbImage(String input, String output, String title, int mode) {
        try {
            BufferedImage resizedImage = new BufferedImage(1280, 720, 1);
            Graphics2D g = resizedImage.createGraphics();
            BufferedImage inThumb = ImageIO.read(new File(input));
            File thumbbg[] = new File("C:\\Users\\magictool\\Desktop\\images").listFiles();
            g.drawImage(ImageIO.read(thumbbg[new Random().nextInt(thumbbg.length)]), 0, 0, 1280, 720, null);
            int width = inThumb.getWidth();
            int height = inThumb.getHeight();
            if (inThumb.getHeight() > inThumb.getWidth() * 2 / 3) {
                height = 680;
                width = (int) (inThumb.getWidth() * 680 / inThumb.getHeight());
                inThumb = resizeImage(inThumb, 1, width, height);
                if (width > 853) {
                    width = 853;
                    inThumb = crop(inThumb, width, height);
                }
                g.drawImage(inThumb, 20, ((720 - height) / 2), width, height, null);
                g.setPaint(new Color(0, 0, 0, 127));
                g.fillRect(30 + width, 20, 1280 - 40 - width, 680);
                drawCenter1(g, title, 1280 - 40 - width, 680, 30, 30 + width + 10);
            } else {
                width = 640;
                height = (int) (inThumb.getHeight() * 640 / inThumb.getWidth());
                inThumb = resizeImage(inThumb, 1, width, height);
                g.setPaint(new Color(0, 0, 0, 127));
                g.fillRect(20, 20 + height + 10, 1240, 720 - 20 - 10 - height - 10);
                g.drawImage(inThumb, ((1280 - inThumb.getWidth()) / 2), 20, inThumb.getWidth(), inThumb.getHeight(), null);
                drawCenter1(g, title, 1240, 720 - 20 - 10 - height - 10, 20 + height, 30);
            }
            ImageIO.write(resizedImage, MyFileUtils.getFileExtension(output), new File(output));
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String makeAvatarImage(String output, String title) {
        try {
            BufferedImage resizedImage = new BufferedImage(1280, 720, 1);
            Graphics2D g = resizedImage.createGraphics();
            // BufferedImage inThumb = ImageIO.read(new File(input));
            g.setPaint(new Color(new Random().nextInt(224), new Random().nextInt(224), new Random().nextInt(224)));
            g.fillRect(0, 0, 1280, 360);
            g.setPaint(new Color(new Random().nextInt(224), new Random().nextInt(224), new Random().nextInt(224)));
            g.fillRect(0, 360, 1280, 360);
            g.setPaint(new Color(new Random().nextInt(224), new Random().nextInt(224), new Random().nextInt(224)));
            g.fillRoundRect(40, 310, 1200, 100, 15, 15);
            drawCenter1(g, title, 1280, 360, 300, 30);
            ImageIO.write(resizedImage, MyFileUtils.getFileExtension(output), new File(output));
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static void showMenu(AnchorPane ap, Double duration, int from) {
        AnchorPane parent = ((AnchorPane) ap.getParent());
        AnchorPane a = new AnchorPane();
        a.setId("blur-pane");
        if (parent.lookup("#blur-pane") == null) {
            a.setPrefSize(parent.getWidth(), parent.getHeight());
            a.getStyleClass().setAll("dialog-bg");
            parent.getChildren().add(parent.getChildren().indexOf(ap), a);
        }
        a.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Graphics.hideCustomDialog(ap, 0.5, 172);
                    }
                }
            }
        });
        ap.setLayoutX(-50);
        ap.setLayoutY(0);
        TranslateTransition translateTransition
                = new TranslateTransition(Duration.seconds(duration), ap);
        translateTransition.setFromX(-50);
        translateTransition.setToX(50);
        translateTransition.setFromY(0);
        translateTransition.setToY(0);

        translateTransition.setCycleCount(1);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(duration), ap);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.play();
        translateTransition.play();
        ap.setVisible(true);
    }

    public static void showCustomDialog(AnchorPane ap, Double duration, int to) {
        AnchorPane parent = ((AnchorPane) ap.getParent());
        AnchorPane a = new AnchorPane();
        a.setId("blur-pane");
        if (parent.lookup("#blur-pane") == null) {
            a.setPrefSize(parent.getWidth(), parent.getHeight());
            a.getStyleClass().setAll("dialog-bg");
            parent.getChildren().add(parent.getChildren().indexOf(ap), a);
        }
        a.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Graphics.hideCustomDialog(ap, 0.5, 172);
                    }
                }
            }
        });
        ap.setLayoutY(0);
        TranslateTransition translateTransition
                = new TranslateTransition(Duration.seconds(duration), ap);
        translateTransition.setFromY(0);
        translateTransition.setToY(to);
        translateTransition.setCycleCount(1);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(duration), ap);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.play();
        translateTransition.play();
        ap.setVisible(true);
    }

    public static void hideCustomDialog(AnchorPane ap, Double duration, int to) {
        AnchorPane parent = ((AnchorPane) ap.getParent());
        parent.getChildren().remove(parent.getChildren().indexOf(ap) - 1);
        TranslateTransition translateTransition
                = new TranslateTransition(Duration.seconds(duration), ap);
        translateTransition.setFromY(to);
        translateTransition.setToY(0);
        translateTransition.setCycleCount(1);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(duration), ap);
        fadeIn.setFromValue(1);
        fadeIn.setToValue(0);
        fadeIn.setCycleCount(1);
        fadeIn.play();
        translateTransition.play();
        ap.setVisible(false);
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(1280, 720, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 1280, 720, null);
        g.dispose();
        return resizedImage;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static void drawCenter1(java.awt.Graphics g, String m, int width, int height, int top, int left) throws UnsupportedEncodingException, FontFormatException, IOException {
        int sw = 0;
        int sh = 0;
        int line = 1;
        Font font = null;
        String currentLine = "";
        font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(FONT_ARIALUNI)).deriveFont(90f);
        FontMetrics fm = g.getFontMetrics(font);
        sw = fm.stringWidth(m);
        sh = fm.getHeight();
        if (sw > width) {
            line = (int) sw / width + (sw % width == 0 ? 0 : 1);
        }
        if (top != 30) {
            line = line > 2 ? 2 : line;
        }
        for (int i = 1; i <= line; i++) {
            try {
                currentLine = "";
                int end = 0;
                while (fm.stringWidth(currentLine) < width - 100 && end < m.length()) {
                    currentLine = m.substring(0, end++);
                }
                if (i < line) {
                    m = m.substring(end, m.length());
                }
                g.setFont(font);
                //g.setColor(RandomUtils.getRandomColor2());
                sw = fm.stringWidth(currentLine);
                int x = left + ((width - sw) / 2);
                int y = (top + sh - 20) + 0 + (i - 1) * (sh - 30);
                g.setColor(Color.BLACK);
                g.drawString(currentLine, ShiftWest(x, 2), ShiftWest(y, 2));
                g.setColor(new Color(255, 255, 255));
                g.drawString(currentLine, x, y);
            } catch (Exception e) {
            }

        }
    }

    static int ShiftNorth(int p, int distance) {
        return (p - distance);
    }

    static int ShiftSouth(int p, int distance) {
        return (p + distance);
    }

    static int ShiftEast(int p, int distance) {
        return (p + distance);
    }

    static int ShiftWest(int p, int distance) {
        return (p - distance);
    }

    public static void drawTextBanner(java.awt.Graphics g, String m, String font_string, int width, int left, int top, int maxSize) {
        int font_size = maxSize;
        boolean sizeok = false;
        int sw = 0;
        Font font = null;
        while (sizeok == false) {
            font = new Font(font_string, Font.PLAIN, font_size);
            FontMetrics fm = g.getFontMetrics(font);
            if (fm.stringWidth(m) < width) {
                sw = fm.stringWidth(m);
                sizeok = true;
            } else {
                font_size = font_size - 10;
            }
        }
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(m, left, top);
    }

    public static void drawCenterRotated(java.awt.Graphics g, String m, String font_string, int width, int height, int top, int maxSize) {
        int font_size = maxSize;
        boolean sizeok = false;
        int sw = 0;
        Font font = null;
        while (sizeok == false) {
            font = new Font(font_string, Font.PLAIN, font_size - 20);
            FontMetrics fm = g.getFontMetrics(font);
            if (fm.stringWidth(m) < width) {
                sw = fm.stringWidth(m);
                sizeok = true;
            } else {
                font_size = font_size - 10;
            }
        }
        g.setFont(font);
        g.setColor(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        //g2d.drawString("TES",( width + sw ) / 2 - sw , height);
        AffineTransform at = new AffineTransform();
        at.setToRotation(Math.PI / 4.0);
        g2d.setTransform(at);
        g2d.drawString(m, 50, 50);
    }

//    public static String renderImage(String inputImageURL, String imageImport, int position) {
//        try {
//            String imageName = inputImageURL.split("\\\\")[inputImageURL.split("\\\\").length - 1];
//            String renderImageURL = inputImageURL.substring(0, inputImageURL.lastIndexOf("\\")) + "\\" + imageName.substring(0, imageName.lastIndexOf("."));
//            BufferedImage image = ImageIO.read(new File(inputImageURL));
//            int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
//            BufferedImage imagecover = null;
//            imagecover = ImageIO.read(new File(imageImport));
//            int width = image.getWidth();
//            int height = image.getHeight();
//            int top = 0;
//            int left = 0;
//            int imageWidth = width / 5;
//            int imageHeight = height / 6;
//            //1280,720
//            switch (position) {
//                case 0:// trên phải
//                    top = 20 + height / 8;
//                    left = width - (imageWidth + 20);
//                    break;
//                case 1:// trên trái
//                    top = 20 + height / 8;
//                    left = 10;
//                    break;
//                case 2:// dưới phải 
//                    top = height - (imageHeight + 20) - height / 8;
//                    left = width - (imageWidth + 20);
//                    break;
//                case 3:// dưới trái
//                    top = height - (imageHeight + 20) - height / 8;
//                    left = 20;
//                    break;
//            }
//            java.awt.Graphics g = image.getGraphics();
//            g.drawImage(imagecover, left, top, imageWidth, imageHeight, null);
//            g.dispose();
//            BufferedImage imageCroped = resizeImage(crop(image), type);
//            ImageIO.write(imageCroped, MyFileUtils.getFileExtension(inputImageURL), new File(renderImageURL + "." + MyFileUtils.getFileExtension(inputImageURL)));
//            return renderImageURL + "." + MyFileUtils.getFileExtension(inputImageURL);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return "";
//        }
//    }
    public static BufferedImage crop(BufferedImage image, int width, int height) {
        BufferedImage img = image.getSubimage(0, 0, width, height); //fill in the corners of the desired crop location here
        BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        return copyOfImage;
    }

    public static boolean createThumbVideo(String inputImage, String text, String filename) {
        try {
            BufferedImage imageResize = ImageIO.read(new File(inputImage));
            int type = imageResize.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : imageResize.getType();
            BufferedImage resizeImageJpg = resizeImage(imageResize, type, 1280, 720);
            ImageIO.write(resizeImageJpg, MyFileUtils.getFileExtension(filename), new File(filename));
            BufferedImage image = ImageIO.read(new File(filename));
            java.awt.Graphics g = image.getGraphics();
            drawCenter1(g, text, 1280, 720 / 5 * 3, 0, 0);
            g.dispose();
            ImageIO.write(image, MyFileUtils.getFileExtension(filename), new File(filename));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                //File fileDelete = new File(inputImage.substring(0, inputImage.lastIndexOf("\\"))+"\\thumb\\thumb.jpg");
                //if(fileDelete.exists()){
                //     fileDelete.delete();
                //}               
            } catch (Exception e) {
            }
        }
    }

    public static boolean resizeThumbVideo(String inputImage, String filename) {
        try {
            BufferedImage imageResize = ImageIO.read(new File(inputImage));
            int type = imageResize.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : imageResize.getType();
            BufferedImage resizeImageJpg = resizeImage(imageResize, type, 1280, 720);
            ImageIO.write(resizeImageJpg, MyFileUtils.getFileExtension(filename), new File(filename));
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                //File fileDelete = new File(inputImage.substring(0, inputImage.lastIndexOf("\\"))+"\\thumb\\thumb.jpg");
                //if(fileDelete.exists()){
                //     fileDelete.delete();
                //}               
            } catch (Exception e) {
            }
        }
    }

    /* draw rectangle  */
}
