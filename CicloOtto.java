import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.LinkedList;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.net.URL;

public class CicloOtto extends JPanel{

    private BufferedImage background1, background2, motor1, motor2, piston, base, valve1, valve2, demoGraph;
    private static int y = 570;
    private static int contPiston = 0;
    private static int arraySize = 5;
    private static boolean reverse = false;
    private static boolean valve1Active = false;
    private static boolean valve2Active = true;
    private static boolean reverseMovement = false;
    private static Double numVal, sum, backup, extra;
    private static Double[] list = new Double[arraySize];
    private static Image fire, stop;
    private static int actualMovement, actualMovement2;
    


    public CicloOtto() {
        try {

            background1 = ImageIO.read(new File("bg.png"));
            background2 = ImageIO.read(new File("bg2.png"));
            demoGraph = ImageIO.read(new File("cycleDemo.png"));
            valve1 = ImageIO.read(new File("valve1.png"));
            valve2 = ImageIO.read(new File("valve2.png"));
            base = ImageIO.read(new File("base.png"));
            motor1 = ImageIO.read(new File("motor1.png"));
            motor2 = ImageIO.read(new File("motor2.png"));
            piston = ImageIO.read(new File("piston.png"));
            fire = new ImageIcon("fire2.gif").getImage();
            stop = new ImageIcon("stopSign.gif").getImage();

        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background1, 0, 0, this);
        g.drawImage(background2, 1037, 0, this);
        g.drawImage(base, 1037, 0, this);
        if(valve1Active){
            g.drawImage(valve1, 1476, 325, this);
        }
        if(valve2Active){
            g.drawImage(valve2, 1330, 319, this);
        }
        //g.drawImage(demoGraph, 1037, 700, 933, 683, this);
        g.drawImage(motor1, 20, -10, this);
        g.drawImage(piston, 415, y, this);
        g.drawImage(motor2, 20, -10, this);  

        //HUD   
        g.drawImage(fire, 800, 50, this);
        if(reverseMovement){
            g.drawImage(stop, 570, 600, 630, 765, this);  
        }  
         
    }

    public static void main(String[] args) {
        SerialTest main = new SerialTest();
        SerialTest2 main2 = new SerialTest2();
        main.initialize();
        main2.initialize();
        System.out.println("Started");
        CicloOtto co = new CicloOtto();
        JFrame frame = new JFrame("Ciclo de Otto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1970,1382);
        frame.add(co);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        int cont = 0;
        backup = numVal = extra = 0.0;
        actualMovement = actualMovement2 = 0;
        while(true){
            co.repaint();
            /*if(!reverse){
                y -= 1;
            }else{
                y += 1;
            }*/
            
            /*if(y < 430 || y > 605){
                reverse = !reverse;
                cont++;
                if(cont > 3){
                    cont = 0;
                }
            }*/
            extra = SerialTest2.piston;
        
            actualMovement = SerialTest.encoder;
            if(actualMovement > actualMovement2){
                reverseMovement = false;
            }else if(actualMovement < actualMovement2){
                reverseMovement = true;
            }
            actualMovement2 = actualMovement;

            //extra = 0.0;
            numVal = extra * 30.0;
            if(((backup - extra) > 0.001 || (backup - extra) < -0.001)){
                backup = extra;
                //System.out.println(backup - numVal);
                if(list[arraySize - 1] == null){
                    if(contPiston <= arraySize - 1){
                        list[contPiston] = numVal;
                        contPiston++;
                    }
                }else{
                    if(!reverse){
                        contPiston = arraySize - 1;
                        reverse = true;
                    }
                    
                    sum = 0.0;
                    list[contPiston] = numVal;
                    if(contPiston < (arraySize - 1)){
                        contPiston++;
                    }else{
                        contPiston = 0;
                    }
                    for(int i = 0; i < arraySize; i++){
                        sum += list[i];
                    }
                    sum = (sum / new Double(arraySize)) + 285;
                    
                    //System.out.print(contPiston + " \n");
                    
                    y = sum.intValue();


                    
                    
                }
            }
            
            
            
            //System.out.println("hola");

            if(cont == 0){
                valve1Active = false;
                valve2Active = false;
            }else if(cont == 1){
                valve1Active = false;
                valve2Active = true;
            }else if(cont == 2){
                valve1Active = true;
                valve2Active = true;
            }else if(cont == 3){
                valve1Active = true;
                valve2Active = false;
            }


            try {
                Thread.sleep(5);
            }catch(Exception e){

            }
            
        }
        
    }
}
