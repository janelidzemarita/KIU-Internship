package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Tetris {

    static final Color[] COLORS = {Color.BLACK.darker(), Color.BLUE.darker(), Color.RED.darker(), Color.GREEN.darker(), Color.CYAN.darker(), Color.MAGENTA.darker(),
            Color.ORANGE.darker(), Color.YELLOW.darker()};

    public static void main(String[] args) {

        JFrame frame = new JFrame("Tetris");

        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(400, 700));

        frame.add(panel);

        frame.pack();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setVisible(true);

        Graphics2D graphics = (Graphics2D) panel.getGraphics();

        TetrisModel model = new TetrisModel();

        View view = new View(new Graphics() {

            @Override
            public void drawBoxAt(int x, int y, int size, int colorIndex) {
                // Set the color based on the colorIndex
                graphics.setColor(Tetris.COLORS[colorIndex]);

                // Draw the filled rectangle (the box) at the given (x, y) position
                graphics.fillRect(x, y, size, size);

                // Optionally, you can draw the outline of the box to make it more visually distinct
                graphics.setColor(Color.BLACK); // Set the outline color (e.g., black)
                graphics.drawRect(x, y, size, size);
            }

            @Override
            public void drawBoxAt(int i, int j, int value) {
                graphics.setColor(COLORS[value]);
                graphics.fillRect(i, j, View.BOX_SIZE, View.BOX_SIZE);
            }

            @Override
            public void fillRect(int i, int i1, int i2, int i3) {
                graphics.fillRect(i, i1, i2, i3);
            }

            @Override
            public void drawString(String s, int i, int i1) {
                graphics.drawString(s, i, i1);
            }

            @Override
            public void setColor(Color color) {
                graphics.setColor(color);
            }

            @Override
            public void setFont(Font font) {
                graphics.setFont(font);
            }

        });

        Controller controller = new Controller(model, view);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (model.state.isGameOver()) {
                    model.gameOver();
                    if (e.getKeyCode() == KeyEvent.VK_R) {
                        controller.restart();
                    } else if (e.getKeyCode() == KeyEvent.VK_Q) {
                        controller.quitGame();
                    }
                } else {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                            controller.moveLeft();
                            break;
                        case KeyEvent.VK_RIGHT:
                            controller.moveRight();
                            break;
                        case KeyEvent.VK_UP:
                            controller.rotate();
                            break;
                        case KeyEvent.VK_DOWN:
                            controller.slideDown();
                            break;
                        case KeyEvent.VK_SPACE:
                            controller.drop();
                            break;
                        case KeyEvent.VK_P:
                            model.pause();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + e.getKeyCode());
                    }
                }
            }
        });

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(controller::slideDown, 0, 1, TimeUnit.SECONDS);
    }

}