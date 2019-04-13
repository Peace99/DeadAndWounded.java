/**
 * Created by Eirene on 30-Mar-19.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class DeadAndWounded {
    public static final Random RANDOM = new Random();
    private int num; //number to find
    private int numGuess;
    private boolean guessed;
// using Swing API for the UI
    private JTextPane textPane;
    private JTextField textField;

    // created a method to generate the number to find
    private void genetatenum() {
       // 4 digit number, between 1000 and 9000
        do {
          num = RANDOM.nextInt(9000) + 1000;
    }
    // we need to have 4-digits number with no duplicate
    while(hasDuplicate(num));
System.out.println("[CHEAT]" + num);
    }
private boolean hasDuplicate(int num){
        boolean[] digits = new boolean[10];
       while (num > 0){
           int last = num %10;
           if (digits[last])
               return true;
           digits[last] = true;
           num = num / 10;
       }
       return false;
}
    //we create a method returning buuls and cows for number comparing to the number to guess
    // buuls: correct digits well placed
    // cows: correct digits wrongly placed
    private int[] DeadAndWounded(int entered){
    int bulls = 0, cow =0;
    String enteredstr = String.valueOf(entered);
    String numstr = String.valueOf(num);

    for ( int i =0; i< numstr.length(); i++){
        char c = enteredstr.charAt(i);
        if (c == numstr.charAt(i)){
            bulls++;
        }
        else if (numstr.contains(String.valueOf(c)))
        {
            cow++;
        }
    }
    return new int[] {bulls, cow};
    }
    // A method to manage the game with swing UI
    private void play(){
        JFrame frame = new JFrame("Dead and Wounded");
        frame.setBackground(Color.RED);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.BLUE);

        // Add buttons
        JPanel buttonsPanel = new JPanel();
        JLabel inputLabel = new JLabel("input: ");

        buttonsPanel.add(inputLabel, BorderLayout.LINE_START);
        textField = new JTextField(15) ;
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               okClick();
            }
        });
        buttonsPanel.add(textField, BorderLayout.LINE_START);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                okClick();
            }
        });
         buttonsPanel.add(okButton, BorderLayout.LINE_START);
         JButton newGameButton = new JButton("New Game");
         newGameButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 init();
             }
         });
         buttonsPanel.add(newGameButton, BorderLayout.LINE_END);
         contentPane.add(buttonsPanel, BorderLayout.PAGE_END);

         // add a text to display tries and other message to the user
         textPane = new JTextPane();
         textPane.setEditable(false);
         JScrollPane scrollPane = new JScrollPane(textPane);

         // we customize rendering in the in the JTextPane
        SimpleAttributeSet bSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(bSet, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(bSet, 20);
        StyledDocument doc = textPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), bSet, false);

        contentPane.add(scrollPane,BorderLayout.CENTER );



        frame.setMinimumSize(new Dimension(600, 500));

        // center JFrame on the screen
Dimension objDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
int coordX = (objDimension.width - frame.getWidth())/ 2;
int coordY = ( objDimension.height - frame.getHeight()) / 2;
frame.setLocation(coordX, coordY);


// display the window
frame.pack();
frame.setVisible(true);

init();


         }

         //manage the user click on ok
         private void okClick() {
        // we get user input
        String userInput = textField.getText();
        int entered = -1;

        try{
            entered = Integer.parseInt(userInput);
        }
        catch (Exception e){
            textField.setText("");
            JOptionPane.showMessageDialog(null, "You must enter an integer", "Error", JOptionPane.ERROR_MESSAGE);

        }
        if(hasDuplicate(entered) || entered >1000 || entered < 9999 ){
            textField.setText("");
            JOptionPane.showMessageDialog(null, "You must enter an integer with no duplicates", "Error", JOptionPane.ERROR_MESSAGE);

        }
        numGuess++;

        // we count bulls and cows
        int[] DeadAndWounded = DeadAndWounded(entered);
        if (DeadAndWounded[0] == 4){
            guessed = true;}
            else {
            updateText(entered + "\t\t\t\t" + DeadAndWounded[0] + "Dead and" + DeadAndWounded[1] + "Wounded");
        }
        if (guessed) {
            updateText("\n" + entered + "\n\nYou won after" +  numGuess + "guesses");

        }
        textField.setText("");
        }
        private void updateText(String msg){
        textPane.setText(textPane.getText() + "\n" + msg);
        }
        private void init(){
            genetatenum();
            numGuess = 0;
            guessed = false;
            textPane.setText("You must guess a 4 digit number with no duplicate");
            textField.setText("");
        }
        public static void main(String[] args){
            DeadAndWounded DeadAndWounded = new DeadAndWounded();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    DeadAndWounded.play();
                }
            });
        }
    }

