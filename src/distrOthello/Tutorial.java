package distrOthello;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*This class is used to show the rules of the game.*/
public class Tutorial extends JFrame {

	private static final long serialVersionUID = 1L;
	private CardLayout cards;
	int currentCard = 1;

	public Tutorial() {
		
        setTitle("How to play Othello"); 
        setSize(500, 400); 
        final JPanel cardPanel = new JPanel(); 
        cards = new CardLayout();  
        cardPanel.setLayout(cards); 
        
        //This creates the "cards" of the layout and adds the labels
        JPanel[] cardpanels = new JPanel[5];
        JLabel[] cardtexts = new JLabel[5];
        ImageIcon[] images = new ImageIcon[5];
        for(int i=0;i<=4;i++) {
        	cardpanels[i]= new JPanel();
        	cardtexts[i] = new JLabel();
        	images[i]= new ImageIcon("images/Othello"+(i+1)+".png"); 
        	
        	//Each label will have a portion of the text of the tutorial
        	switch(i) {
        	case 0:
	        	cardtexts[i].setText("<html>Othello is a 2 player strategy game played in an 8x8 board."
	        			+ " At the beggining of the game there will be 2 black pieces"
	        			+ " and 2 white pieces at the center.Black moves first.</html>");
        		break;
        	case 1:
	        	cardtexts[i].setText("<html>Black must place a piece on the board arranged in a way that"
	        			+ " at least there is an horizontal, vertical or diagonal line occupied between"
	        			+ " an old black piece and the new black piece with at least one white piece between them."
	        			+ " At the beginning black has the following 4 possible moves:</html>");
        		break;
        	case 2:
	        	cardtexts[i].setText("<html>After placing the piece, the white pieces between the new piece"
	        			+ " and an old black piece will become black.</html>");
        		break;
        	case 3:
	        	cardtexts[i].setText("<html>White must play the same rules, trying to convert as many black pieces"
	        			+ " as possible.</html>");
        		break;
        	case 4:
	        	cardtexts[i].setText("<html>Players can only place 1 piece every turn."
	        			+ " If there are no possible legal moves, the turn is skipped."
	        			+ " The match can end before the board is full."
	        			+ " The winner is the one with most pieces on the board."
	        			+ "If both players have the same number of pieces then it is a draw.</html>");
        		break;
        	}
        	cardpanels[i].add(cardtexts[i]);
        	cardtexts[i].setIcon(images[i]);
        	cardPanel.add(cardtexts[i],i);
        }
        //I will store the buttons to browse the tutorial in a panel
        JPanel buttonPanel = new JPanel(); 
  
        // Initialization of buttons
        JButton firstBtn = new JButton("First"); 
        JButton nextBtn = new JButton("Next"); 
        JButton previousBtn = new JButton("Previous"); 
        JButton lastBtn = new JButton("Last"); 
  
        // Adding buttons on JFrame. 
        buttonPanel.add(firstBtn); 
        buttonPanel.add(previousBtn); 
        buttonPanel.add(nextBtn); 
        buttonPanel.add(lastBtn); 
  
        //Here I'm adding the listeners to the browsing buttons
        firstBtn.addActionListener(new ActionListener()  
        { 
            public void actionPerformed(ActionEvent e) 
            { 
                cards.first(cardPanel); 
                currentCard = 1; 
            } 
        }); 

        lastBtn.addActionListener(new ActionListener()  
        { 
            public void actionPerformed(ActionEvent e) 
            { 
                cards.last(cardPanel);  
                currentCard = 4; 
            } 
        }); 
  
        nextBtn.addActionListener(new ActionListener()  
        { 
            public void actionPerformed(ActionEvent e) 
            { 
                if (currentCard <= 4)  
                {
                    cards.next(cardPanel);
                    currentCard += 1; 
                } 
            } 
        }); 
  
        previousBtn.addActionListener(new ActionListener()  
        { 
            public void actionPerformed(ActionEvent arg0) 
            { 
				if (currentCard > 1) {  
                    cards.previous(cardPanel);
                    currentCard -= 1;
                } 
            }


        }); 
  
        // used to get content pane 
        getContentPane().add(cardPanel, BorderLayout.NORTH); 
        getContentPane().add(buttonPanel, BorderLayout.SOUTH); 
    }			
}

