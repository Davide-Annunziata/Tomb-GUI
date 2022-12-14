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
    final private File aAmbo= new File("audio/Ambo.wav");
    final private  File aTerno= new File("audio/Terno.wav");
    final private File aQuaderna= new File("audio/Quaderna.wav");
    final private  File aQuintina= new File("audio/Quintina.wav");
    final private File aTombola= new File("audio/Tombola.wav");


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
        //Creare nuova partita alla pressione del tansto N
        textLabel.getInputMap().put(KeyStroke.getKeyStroke("N"),"newGame");
        textLabel.getActionMap().put("newGame",new newGame());

        //Estrarre un nuovo numero alla pressione del tasto E
        textLabel.getInputMap().put(KeyStroke.getKeyStroke("E"),"fastExtraction");
        textLabel.getActionMap().put("fastExtraction",new fastEstraction());

        textPanel.setPreferredSize(new Dimension(1000, 100));
        textPanel.setLayout(new BorderLayout());

        //bottone estai
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
        //Inizializzatione label contenenti i numeri del tabellone
        for (int i = 0; i < 90; i++) {
            numbers[i] = new JLabel();
            numbers[i].setText("");
            numbers[i].setFont(new Font("JetBrains mono", Font.BOLD, 24));
            numbers[i].setSize(30, 30);
            numbers[i].setBorder(BorderFactory.createLineBorder(Color.black,2));
            numbers[i].setOpaque(true);
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

    //Estrai un nuovo numero fina a quando il numero non ?? gi?? uscito
    @Override
    public void actionPerformed(ActionEvent e) {
        textLabel.setForeground(new Color(0x123456));
        textLabel.setText("Tombola");
        if(e.getSource()==nextNum) {
            int n;
            do{
                n = rand.nextInt(90);
            } while (!numbers[n].getText().equals(""));

            numbers[n].setText(Integer.toString(n + 1));
            lastNumPanel.setText(Integer.toString(n + 1));
            try {
                finalScreen(check());
            } catch (Exception ignored){}
        }
    }

    //controlla se il giocatore ha fatto qualcose, poi contalla se il giocatore ha vinto e ritorna quale casella
    public int check() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        int c=0,y=0,z=5,v=0;  // c=contatore , v=tabella vincene
        Color colore = null;
        do{
            for(int i=y;i<z;i++){
                if(!numbers[i].getText().equals("")){
                    c++;
                }
            }
            //scrive se hai fatto qualcosa (solo la prima volta )
            if (c==2 && !ambo){
                System.out.println("hai fatto ambo");
                ambo=true;
                textLabel.setText("Hai fatto ambo");
                textLabel.setForeground(Color.red);
                AudioInputStream input = AudioSystem.getAudioInputStream(aAmbo);
                Clip clip= AudioSystem.getClip();
                clip.open(input);
                clip.start();
            }else if(c==3 && !terno){

                System.out.println("hai fatto terno");
                terno=true;
                textLabel.setText("Hai fatto terno");
                textLabel.setForeground(Color.red);
                AudioInputStream input = AudioSystem.getAudioInputStream(aTerno);
                Clip clip= AudioSystem.getClip();
                clip.open(input);
                clip.start();

            }else if(c==4 && !quaderna){
                System.out.println("hai fatto quaderna");
                quaderna=true;
                textLabel.setText("Hai fatto quaderna");
                textLabel.setForeground(Color.red);
                AudioInputStream input = AudioSystem.getAudioInputStream(aQuaderna);
                Clip clip= AudioSystem.getClip();
                clip.open(input);
                clip.start();
                textLabel.setText("Hai fatto quaderna");
                textLabel.setForeground(Color.red);

            }else if(c==5 && !quintina){
                System.out.println("hai fatto quintina");
                quintina=true;
                textLabel.setText("Hai fatto quintina");
                textLabel.setForeground(Color.red);
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
                colore=Color.red;
            }else if(j==3){
                c=0;
                colore=Color.blue;
                v++;
            }else if(j==6){
                c=0;
                colore=new Color(0x0BA000);
                v++;
            }

            for(int i=y;i<z;i++){
                numbers[i].setForeground(colore);
                if(!numbers[i].getText().equals("")){
                    c++;
                }
            }

            if(c==15 && !tombola){
                end();
                return v;
            }

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
                v++;
            }else if(j==3){
                colore=Color.pink;
                c=0;
                v++;
            }else if(j==6){
                colore=new Color(0xC105FF);
                c=0;
                v++;
            }

            for(int i=y;i<z;i++){
                numbers[i].setForeground(colore);
                if(!numbers[i].getText().equals("")){
                    c++;
                }
            }

            if(c==15 && !tombola){
                end();
                return v;
            }
            y+=10;
            z+=10;
        }
        return -1;
    }

    private void finalScreen(int x){
        int y=0,z=5;
        switch(x){
            case 0:
                break;
            case 1:
                y=30;
                z=35;
                break;
            case 2:
                y=60;
                z=65;
                break;
            case 3:
                y=5;
                z=10;
                break;
            case 4:
                y=35;
                z=40;
                break;
            case 5:
                y=65;
                z=70;
                break;
            case -1:
                return;
        }
        for(int j=0; j<3;j++){
            for(int i=y;i<z;i++){
                numbers[i].setBackground(Color.GREEN);
            }
            y+=10;
            z+=10;
        }
    }
   //die al giocatore se ha fatto tombola
    private void end() {
        System.out.println("hai fatto tombola");
        tombola=true;
        textLabel.setText("Hai fatto Tombola");
        textPanel.setForeground(Color.red);
        try {
            AudioInputStream input = AudioSystem.getAudioInputStream(aTombola);
            Clip clip = AudioSystem.getClip();
            clip.open(input);
            clip.start();
        } catch (Exception ignored){}


        nextNum.setEnabled(false);

    }

    class newGame extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            new Tomb();
        }
    }

    class fastEstraction extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!tombola){
                textLabel.setForeground(new Color(0x123456));
                textLabel.setText("Tombola");
                int n;
                do{
                    n = rand.nextInt(90);
                } while (!numbers[n].getText().equals(""));
                numbers[n].setText(Integer.toString(n + 1));
                lastNumPanel.setText(Integer.toString(n + 1));
                try {
                    finalScreen(check());
                } catch (Exception ignored){}
            }
        }
    }
}