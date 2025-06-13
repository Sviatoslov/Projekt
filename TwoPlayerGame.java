import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TwoPlayerGame extends JPanel {
    // Размеры окна
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    // Позиции игроков
    private final Rectangle player1 = new Rectangle(100, HEIGHT / 2, 50, 50);
    private final Rectangle player2 = new Rectangle(WIDTH - 150, HEIGHT / 2, 50, 50);
    private int vel1X, vel1Y, vel2X, vel2Y;

    public TwoPlayerGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.DARK_GRAY);

        // Обработка клавиш
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    // WASD для первого игрока
                    case KeyEvent.VK_W -> vel1Y = -5;
                    case KeyEvent.VK_S -> vel1Y = 5;
                    case KeyEvent.VK_A -> vel1X = -5;
                    case KeyEvent.VK_D -> vel1X = 5;
                    // Стрелки для второго игрока
                    case KeyEvent.VK_UP -> vel2Y = -5;
                    case KeyEvent.VK_DOWN -> vel2Y = 5;
                    case KeyEvent.VK_LEFT -> vel2X = -5;
                    case KeyEvent.VK_RIGHT -> vel2X = 5;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W, KeyEvent.VK_S -> vel1Y = 0;
                    case KeyEvent.VK_A, KeyEvent.VK_D -> vel1X = 0;
                    case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> vel2Y = 0;
                    case KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> vel2X = 0;
                }
            }
        });
        setFocusable(true);
        new Timer(16, e -> updateGame()).start();
    }

    private void updateGame() {
        player1.translate(vel1X, vel1Y);
        player2.translate(vel2X, vel2Y);

        // Ограничиваем движение рамками окна
        player1.x = Math.max(0, Math.min(player1.x, WIDTH - player1.width));
        player1.y = Math.max(0, Math.min(player1.y, HEIGHT - player1.height));
        player2.x = Math.max(0, Math.min(player2.x, WIDTH - player2.width));
        player2.y = Math.max(0, Math.min(player2.y, HEIGHT - player2.height));

        // Простая проверка столкновения
        if (player1.intersects(player2)) {
            System.out.println("Произошло столкновение!");
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(player1.x, player1.y, player1.width, player1.height);
        g.setColor(Color.BLUE);
        g.fillRect(player2.x, player2.y, player2.width, player2.height);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Дуэль");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new TwoPlayerGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
