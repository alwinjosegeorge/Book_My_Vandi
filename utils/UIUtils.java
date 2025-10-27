package util;

import model.Car;
import model.User;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.util.ArrayList;

// Custom Colors (Replicating from CarRentalFull)
public class UIUtils {
    public static final Color NAVY_HEADER = new Color(28, 40, 51); // Deep Navy/Steel
    public static final Color PRIMARY_BLUE = new Color(37, 150, 190); // Action Blue
    public static final Color ACCENT_GREEN = new Color(55, 160, 80); // Confirmed/Success
    public static final Color ACCENT_RED = new Color(200, 50, 50); // Rejected/Error
    public static final Color ACCENT_ORANGE = new Color(255, 140, 0); // Pending/Star Rating
    public static final Color BACKGROUND_LIGHT = new Color(245, 248, 250); // Light Gray Background
    public static final Color CARD_BORDER = new Color(210, 215, 220); // Soft Border
    
    public static final DecimalFormat MONEY = new DecimalFormat("#,###.##");
    public static final String PLACEHOLDER_IMAGE = "images/placeholder.png";

    public static void ensureImagesFolder(){try{Files.createDirectories(Paths.get("images"));}catch(IOException e){System.err.println(e);}}

    public static String copyImageToStore(File sourceFile,String targetFileName)throws IOException{
        ensureImagesFolder();
        Path target = Paths.get("images",targetFileName);
        Files.copy(sourceFile.toPath(),target,StandardCopyOption.REPLACE_EXISTING);
        return target.toString().replace("\\","/");
    }

    public static ImageIcon loadScaledIcon(String imagePath,int w, int h){
        if(imagePath==null || !new File(imagePath).exists()) imagePath=PLACEHOLDER_IMAGE;
        try{
            BufferedImage img = ImageIO.read(new File(imagePath));
            return new ImageIcon(img.getScaledInstance(w,h,Image.SCALE_SMOOTH));
        }catch(IOException e){return null;}
    }

    public static JLabel makeHeader(String text){
        JLabel l = new JLabel(text,SwingConstants.CENTER);
        l.setFont(new Font("Segoe UI",Font.BOLD,20));
        l.setOpaque(true);
        l.setBackground(NAVY_HEADER);
        l.setForeground(Color.WHITE);
        l.setBorder(new EmptyBorder(12,12,12,12));
        return l;
    }
}