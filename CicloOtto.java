import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CicloOtto extends JPanel{

    private BufferedImage image, motor1, motor2, piston;
    private static int y = 570;
    private static boolean reverse = false;

    public CicloOtto() {
       try {                
          image = ImageIO.read(new File("bg.png"));
          motor1 = ImageIO.read(new File("motor1.png"));
          motor2 = ImageIO.read(new File("motor2.png"));
          piston = ImageIO.read(new File("piston.png"));
       } catch (IOException ex) {
            // handle exception...
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        g.drawImage(motor1, 20, -10, this);
        g.drawImage(piston, 415, y, this);
        g.drawImage(motor2, 20, -10, this);         
    }

    public static void main(String[] args) {
        CicloOtto co = new CicloOtto();
        JFrame frame = new JFrame("Ciclo de Otto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1037, 1382);
        frame.add(co);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        while(true){
            co.repaint();
            if(!reverse){
                y -= 10;
            }else{
                y += 10;
            }
            
            if(y < 470 || y > 620){
                reverse = !reverse;
            }
            try {
                Thread.sleep(100);
            }catch(Exception e){

            }
            
        }
        
    }
}