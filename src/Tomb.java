import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Tomb implements ActionListener {
    private final Random rand = new Random();

    JFrame frame = new JFrame();

    JLabel[] numbers = new JLabel[90];

    JPanel textPanel = new JPanel();

    JPanel numbersPanel = new JPanel();
    JLabel lastNumPanel = new JLabel();

    JLabel textLabel = new JLabel();

    JButton nextNum =new JButton();

    boolean ambo=false,terno=false,quaderna=false,quintina=false,tombola=false;
    final private File aAmbo= new File("src/audio/Ambo.wav");
    final private  File aTerno= new File("src/audio/Terno.wav");
    final private File aQuaderna= new File("src/audio/Quaderna.wav");
    final private  File aQuintina= new File("src/audio/Quintina.wav");
    final private File aTombola= new File("src/audio/Tombola.wav");


    Tomb() {
        frame.setTitle("Tombola GUI");
        frame.setMinimumSize(new Dimension(900,400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);


        textLabel.setText("Tombola");
        textLabel.setBackground(Color.BLACK);
        textLabel.setForeground(new Color(0x123456));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setFont(new Font("JetBrains mono", Font.PLAIN, 62));
        textLabel.setOpaque(true);

        textPanel.setPreferredSize(new Dimension(1000, 100));
        textPanel.setLayout(new BorderLayout());


        nextNum.setBounds(0,0,10,10);
        nextNum.setText("Estrai");
        nextNum.setFocusable(false);
        nextNum.addActionListener(this);


        lastNumPanel.setText("");
        lastNumPanel.setBackground(Color.BLACK);
        lastNumPanel.setForeground(new Color(0x123456));
        lastNumPanel.setPreferredSize(new Dimension(100,100));

        lastNumPanel.setFont(new Font("JetBrains mono", Font.PLAIN, 62));
        lastNumPanel.setOpaque(true);

        for (int i = 0; i < 90; i++) {
            numbers[i] = new JLabel();
            numbers[i].setText("");
            numbers[i].setFont(new Font("JetBrains mono", Font.BOLD, 24));
            numbers[i].setBackground(Color.BLUE);
            numbers[i].setSize(30, 30);
            numbers[i].setBorder(BorderFactory.createLineBorder(Color.black,2));
            numbersPanel.add(numbers[i]);
        }


        numbersPanel.setLayout(new GridLayout(9, 10));
        numbersPanel.setOpaque(true);

        textPanel.add(textLabel);
        textPanel.add(nextNum,BorderLayout.WEST);
        textPanel.add(lastNumPanel,BorderLayout.EAST);
        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(numbersPanel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textLabel.setForeground(new Color(0x123456));
        textLabel.setText("Tombola");
        if(e.getSource()==nextNum) {
            int n=0;
            do{
                n = rand.nextInt(90);
            } while (numbers[n].getText() !="");

            numbers[n].setText(Integer.toString(n + 1));
            lastNumPanel.setText(Integer.toString(n + 1));
            try {
                check();
            } catch (Exception ignored){}
        }
    }

    public void check() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        int c=0,y=0,z=5;  // c=contatore
        Color colore = null;
        do{
            for(int i=y;i<z;i++){
                if( numbers[i].getText()!=""){
                    c++;
                }
            }
            //scrive se hai fatto qualcosa (solo la prima volta )
            if (c==2 && !ambo){
                System.out.println("hai fatto ambo");
                ambo=true;
                AudioInputStream input = AudioSystem.getAudioInputStream(aAmbo);
                Clip clip= AudioSystem.getClip();
                clip.open(input);
                clip.start();
                textLabel.setText("Hai fatto ambo");
                textLabel.setForeground(Color.red);
            }else if(c==3 && !terno){
                System.out.println("hai fatto terno");
                terno=true;
                AudioInputStream input = AudioSystem.getAudioInputStream(aTerno);
                Clip clip= AudioSystem.getClip();
                clip.open(input);
                clip.start();
                textLabel.setText("Hai fatto terno");
                textLabel.setForeground(Color.red);
            }else if(c==4 && !quaderna){
                System.out.println("hai fatto quaderna");
                quaderna=true;
                AudioInputStream input = AudioSystem.getAudioInputStream(aQuaderna);
                Clip clip= AudioSystem.getClip();
                clip.open(input);
                clip.start();
                textLabel.setText("Hai fatto quaderna");
                textLabel.setForeground(Color.red);

            }else if(c==5 && !quintina){
                System.out.println("hai fatto quintina");
                quintina=true;
                AudioInputStream input = AudioSystem.getAudioInputStream(aQuintina);
                Clip clip= AudioSystem.getClip();
                clip.open(input);
                clip.start();
                textLabel.setText("Hai fatto quintina");
                textLabel.setForeground(Color.red);

            }
            c=0;
            y+=5;
            z+=5;
        }while(z<90);
        y=0;
        z=5;
        //controlla le prime 5 colonne
        for(int j=0; j<9;j++){
            if(j==0){
                c=0;
                colore=Color.red;
            }else if(j==3){
                c=0;
                colore=Color.blue;
            }else if(j==6){
                c=0;
                colore=new Color(0x0BA000);
            }

            for(int i=y;i<z;i++){
                numbers[i].setForeground(colore);
                if( numbers[i].getText()!=""){
                    c++;
                }
            }

            if(c==15 && !tombola){end();}

            y+=10;
            z+=10;
        }
        //controlla le altre 5 colonne
        y=5;
        z=10;
        for(int j=0; j<9;j++){
            if(j==0){
                colore=Color.ORANGE;
                c=0;
            }else if(j==3){
                colore=Color.pink;
                c=0;
            }else if(j==6){
                colore=new Color(0xC105FF);
                c=0;
            }

            for(int i=y;i<z;i++){
                numbers[i].setBackground(colore);
                numbers[i].setForeground(colore);
                if( numbers[i].getText()!=""){
                    c++;
                }
            }

            if(c==15 && !tombola){end();}
            y+=10;
            z+=10;
        }
    }

    private void end() {
        System.out.println("hai fatto tombola");
        tombola=true;
        try {
            AudioInputStream input = AudioSystem.getAudioInputStream(aTombola);
            Clip clip = AudioSystem.getClip();
            clip.open(input);
            clip.start();
        } catch (Exception ignored){}


        nextNum.setEnabled(false);
        textLabel.setText("Hai fatto Tombola");
        textPanel.setForeground(Color.red);
    }
}