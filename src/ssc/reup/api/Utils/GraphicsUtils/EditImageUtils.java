/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Utils.GraphicsUtils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import static Utils.Constants.DATA.randomThumbBg;
import Utils.MyFileUtils;

/**
 *
 * @author magictool
 */
public class EditImageUtils {

    public static int[] findSubimage(String im1Path, String im2Path) {
        try {
            BufferedImage im1 = ImageIO.read(new File(im1Path));
            BufferedImage im2 = ImageIO.read(new File(im2Path));

            int w1 = im1.getWidth();
            int h1 = im1.getHeight();
            int w2 = im2.getWidth();
            int h2 = im2.getHeight();
            assert (w2 <= w1 && h2 <= h1);
            // will keep track of best position found
            int bestX = 0;
            int bestY = 0;
            double lowestDiff = Double.POSITIVE_INFINITY;
            // brute-force search through whole image (slow...)
            for (int x = 0; x < w1 - w2; x++) {
                for (int y = 0; y < h1 - h2; y++) {
                    double comp = compareImages(im1.getSubimage(x, y, w2, h2), im2);
                    if (comp < lowestDiff) {
                        bestX = x;
                        bestY = y;
                        lowestDiff = comp;
                    }
                }
            }
            // output similarity measure from 0 to 1, with 0 being identical
            System.out.println(lowestDiff);
            // return best location
            return new int[]{bestX, bestY};
        } catch (Exception e) {
        }
        return new int[]{0, 0};
    }

    /**
     * Determines how different two identically sized regions are.
     */
    public static double compareImages(BufferedImage im1, BufferedImage im2) {
        assert (im1.getHeight() == im2.getHeight() && im1.getWidth() == im2.getWidth());
        double variation = 0.0;
        for (int x = 0; x < im1.getWidth(); x++) {
            for (int y = 0; y < im1.getHeight(); y++) {
                variation += compareARGB(im1.getRGB(x, y), im2.getRGB(x, y)) / Math.sqrt(3);
            }
        }
        return variation / (im1.getWidth() * im1.getHeight());
    }

    /**
     * Calculates the difference between two ARGB colours
     * (BufferedImage.TYPE_INT_ARGB).
     */
    public static double compareARGB(int rgb1, int rgb2) {
        double r1 = ((rgb1 >> 16) & 0xFF) / 255.0;
        double r2 = ((rgb2 >> 16) & 0xFF) / 255.0;
        double g1 = ((rgb1 >> 8) & 0xFF) / 255.0;
        double g2 = ((rgb2 >> 8) & 0xFF) / 255.0;
        double b1 = (rgb1 & 0xFF) / 255.0;
        double b2 = (rgb2 & 0xFF) / 255.0;
        double a1 = ((rgb1 >> 24) & 0xFF) / 255.0;
        double a2 = ((rgb2 >> 24) & 0xFF) / 255.0;
        // if there is transparency, the alpha values will make difference smaller
        return a1 * a2 * Math.sqrt((r1 - r2) * (r1 - r2) + (g1 - g2) * (g1 - g2) + (b1 - b2) * (b1 - b2));
    }

    public static String convertTo32bImage(String path) {
        try {
            BufferedImage bf = ImageIO.read(new File(randomThumbBg + "\\2.jpg"));
            BufferedImage bf2 = ImageIO.read(new File(path));
            BufferedImage resizedImage = new BufferedImage(bf2.getWidth(), bf2.getHeight(), bf.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bf.getType());
            resizedImage.getGraphics().drawImage(bf2, 0, 0, null);
            ImageIO.write(resizedImage, MyFileUtils.getFileExtension(path), new File(path));
        } catch (Exception e) {
        }
        return "";
    }

    public static String resizeImage(String path, int width, int height, String toPath) {
        try {
            BufferedImage inputImage = ImageIO.read(new File(path));
            BufferedImage resizedImage = new BufferedImage(width, height, inputImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputImage.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(inputImage, 0, 0, width, height, null);
            g.dispose();
            ImageIO.write(resizedImage, MyFileUtils.getFileExtension(path), new File(toPath));
            return "";
        } catch (Exception e) {
            return "" + e.getMessage();
        }
    }

    public static String overlayImage(String path, String overPath, int width, int height, int x, int y, String toPath) {
        try {
            BufferedImage inputImage = ImageIO.read(new File(path));
            Graphics2D g = inputImage.createGraphics();
            g.drawImage(inputImage, 0, 0, inputImage.getWidth(), inputImage.getHeight(), null);
            g.drawImage(ImageIO.read(new File(overPath)), x, y, width, height, null);
            g.dispose();
            ImageIO.write(inputImage, MyFileUtils.getFileExtension(path), new File(toPath));
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String overlayImage(String path, String overPath, int x, int y, String toPath) {
        try {
            BufferedImage inputImage = ImageIO.read(new File(path));
            BufferedImage overlayImage = ImageIO.read(new File(overPath));
            Graphics2D g = inputImage.createGraphics();
            g.drawImage(inputImage, 0, 0, inputImage.getWidth(), inputImage.getHeight(), null);
            g.drawImage(overlayImage, x, y, overlayImage.getWidth(), overlayImage.getHeight(), null);
            g.dispose();
            ImageIO.write(inputImage, MyFileUtils.getFileExtension(path), new File(toPath));
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static Boolean cropCaptchaImage(String path, String toPath) {
        try {
            BufferedImage inputImage = ImageIO.read(new File(path));
            BufferedImage cropImg = inputImage.getSubimage(128, 223, 288, 108);
            ImageIO.write(cropImg, MyFileUtils.getFileExtension(path), new File(toPath));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
