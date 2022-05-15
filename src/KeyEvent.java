import java.awt.event.KeyListener;

public class KeyEvent implements KeyListener {
    private Swiat swiat;
    private Game game;

    private final int VK_LEFT = 37;
    private final int VK_UP = 38;
    private final int VK_RIGHT = 39;
    private final int VK_DOWN = 40;
    private final int VK_U = 85;

    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {

    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        if (swiat.getCzyCzlowiekZyje()) {
            switch (e.getKeyCode()) {
                case VK_LEFT:
                    swiat.kierunekCzlowieka = LEFT;
                    break;
                case VK_RIGHT:
                    swiat.kierunekCzlowieka = RIGHT;
                    break;
                case VK_UP:
                    swiat.kierunekCzlowieka = UP;
                    break;
                case VK_DOWN:
                    swiat.kierunekCzlowieka = DOWN;
                    break;
                case VK_U:
                    if (swiat.czyCzlowiekZyje) {
                        Czlowiek czl = (Czlowiek) swiat.getOrganizmNaPozycji(swiat.getPozycjaCzlowieka());
                        if (czl.getUmiejetnoscCooldown() == 0) {
                            czl.uzyjUmiejetnosc();
                        }
                    }
                    break;
                default:
                    return;
            }
            game.loadNextTurn();
        }
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {

    }

    KeyEvent(Swiat s, Game g){
       swiat = s;
       game = g;
    }
}
