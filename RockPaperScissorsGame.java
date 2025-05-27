import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockPaperScissorsGame extends JFrame implements ActionListener {
    
    // Game components
    private JButton rockButton, paperButton, scissorsButton, resetButton;
    private JLabel playerChoiceLabel, computerChoiceLabel, resultLabel;
    private JLabel playerScoreLabel, computerScoreLabel;
    private JTextArea gameHistoryArea;
    private JScrollPane scrollPane;
    
    // Game variables
    private int playerScore = 0;
    private int computerScore = 0;
    private Random random = new Random();
    private String[] choices = {"Rock", "Paper", "Scissors"};
    
    public RockPaperScissorsGame() {
        initializeComponents();
        setupLayout();
        setFrameProperties();
    }
    
    private void initializeComponents() {
        // Create buttons
        rockButton = new JButton("🪨 Rock");
        paperButton = new JButton("📄 Paper");
        scissorsButton = new JButton("✂️ Scissors");
        resetButton = new JButton("Reset Game");
        
        // Style buttons
        styleButton(rockButton, new Color(102, 51, 0));
        styleButton(paperButton, new Color(70, 130, 180));
        styleButton(scissorsButton, new Color(220, 20, 60));
        styleButton(resetButton, new Color(34, 139, 34));
        
        // Add action listeners
        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorsButton.addActionListener(this);
        resetButton.addActionListener(this);
        
        // Create labels
        playerChoiceLabel = new JLabel("Your choice: -");
        computerChoiceLabel = new JLabel("Computer choice: -");
        resultLabel = new JLabel("Make your choice to start!");
        playerScoreLabel = new JLabel("Your Score: 0");
        computerScoreLabel = new JLabel("Computer Score: 0");
        
        // Style labels
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        playerChoiceLabel.setFont(labelFont);
        computerChoiceLabel.setFont(labelFont);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(new Color(25, 25, 112));
        
        Font scoreFont = new Font("Arial", Font.BOLD, 18);
        playerScoreLabel.setFont(scoreFont);
        computerScoreLabel.setFont(scoreFont);
        playerScoreLabel.setForeground(new Color(0, 100, 0));
        computerScoreLabel.setForeground(new Color(139, 0, 0));
        
        // Create game history area
        gameHistoryArea = new JTextArea(8, 30);
        gameHistoryArea.setEditable(false);
        gameHistoryArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        gameHistoryArea.setBackground(new Color(245, 245, 245));
        scrollPane = new JScrollPane(gameHistoryArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Game History"));
    }
    
    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(120, 50));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("🎮 Rock Paper Scissors Game 🎮");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112));
        titlePanel.add(titleLabel);
        titlePanel.setBackground(new Color(230, 230, 250));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        // Info panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        infoPanel.add(playerChoiceLabel);
        infoPanel.add(computerChoiceLabel);
        infoPanel.add(resultLabel);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setBackground(new Color(248, 248, 255));
        
        // Score panel
        JPanel scorePanel = new JPanel(new FlowLayout());
        scorePanel.add(playerScoreLabel);
        scorePanel.add(Box.createHorizontalStrut(50));
        scorePanel.add(computerScoreLabel);
        scorePanel.setBackground(new Color(255, 250, 240));
        scorePanel.setBorder(BorderFactory.createTitledBorder("Score Board"));
        
        // Center panel combining info and score
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(scorePanel, BorderLayout.CENTER);
        
        // Bottom panel with reset button and history
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel resetPanel = new JPanel();
        resetPanel.add(resetButton);
        resetPanel.setBackground(new Color(245, 255, 250));
        
        bottomPanel.add(resetPanel, BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add all panels to frame
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setFrameProperties() {
        setTitle("Rock Paper Scissors Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 245, 245));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            resetGame();
            return;
        }
        
        String playerChoice = "";
        if (e.getSource() == rockButton) {
            playerChoice = "Rock";
        } else if (e.getSource() == paperButton) {
            playerChoice = "Paper";
        } else if (e.getSource() == scissorsButton) {
            playerChoice = "Scissors";
        }
        
        playGame(playerChoice);
    }
    
    private void playGame(String playerChoice) {
        // Computer makes random choice
        String computerChoice = choices[random.nextInt(3)];
        
        // Update choice labels
        playerChoiceLabel.setText("Your choice: " + getEmoji(playerChoice) + " " + playerChoice);
        computerChoiceLabel.setText("Computer choice: " + getEmoji(computerChoice) + " " + computerChoice);
        
        // Determine winner
        String result = determineWinner(playerChoice, computerChoice);
        resultLabel.setText(result);
        
        // Update scores
        if (result.contains("You win!")) {
            playerScore++;
            resultLabel.setForeground(new Color(0, 128, 0));
        } else if (result.contains("Computer wins!")) {
            computerScore++;
            resultLabel.setForeground(new Color(178, 34, 34));
        } else {
            resultLabel.setForeground(new Color(255, 140, 0));
        }
        
        // Update score labels
        playerScoreLabel.setText("Your Score: " + playerScore);
        computerScoreLabel.setText("Computer Score: " + computerScore);
        
        // Add to history
        String historyEntry = String.format("You: %-8s Computer: %-8s Result: %s\n", 
                                           playerChoice, computerChoice, result);
        gameHistoryArea.append(historyEntry);
        gameHistoryArea.setCaretPosition(gameHistoryArea.getDocument().getLength());
    }
    
    private String determineWinner(String player, String computer) {
        if (player.equals(computer)) {
            return "It's a tie! 🤝";
        }
        
        if ((player.equals("Rock") && computer.equals("Scissors")) ||
            (player.equals("Paper") && computer.equals("Rock")) ||
            (player.equals("Scissors") && computer.equals("Paper"))) {
            return "You win! 🎉";
        } else {
            return "Computer wins! 🤖";
        }
    }
    
    private String getEmoji(String choice) {
        switch (choice) {
            case "Rock": return "🪨";
            case "Paper": return "📄";
            case "Scissors": return "✂️";
            default: return "";
        }
    }
    
    private void resetGame() {
        playerScore = 0;
        computerScore = 0;
        playerScoreLabel.setText("Your Score: 0");
        computerScoreLabel.setText("Computer Score: 0");
        playerChoiceLabel.setText("Your choice: -");
        computerChoiceLabel.setText("Computer choice: -");
        resultLabel.setText("Make your choice to start!");
        resultLabel.setForeground(new Color(25, 25, 112));
        gameHistoryArea.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RockPaperScissorsGame().setVisible(true);
        });
    }
}