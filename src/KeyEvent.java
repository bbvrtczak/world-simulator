import java.awt.event.KeyListener;

public class KeyEvent implements KeyListener {
    private Swiat swiat;
    private Game game;

    private final int VK_LEFT = 37;
    private final int VK_UP = 38;
    private final int VK_RIGHT = 39;
    private final int VK_DOWN = 40;
    private final int VK_ENTER = 10;
    private final int VK_U = 85;
    private final int VK_I = 73;
    private final int VK_H = 72;
    private final int VK_K = 75;
    private final int VK_N = 78;
    private final int VK_M = 77;

    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;
    private static final int UP_LEFT = 5;
    private static final int UP_RIGHT = 6;
    private static final int DOWN_LEFT = 7;
    private static final int DOWN_RIGHT = 8;

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {

    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (swiat.getCzyCzlowiekZyje()) {
            if (!swiat.isHex){
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
                    case VK_ENTER:
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
            }
            else{
                switch (e.getKeyCode()) {
                    case VK_U:
                        swiat.kierunekCzlowieka = UP_LEFT;
                        break;
                    case VK_I:
                        swiat.kierunekCzlowieka = UP_RIGHT;
                        break;
                    case VK_H:
                        swiat.kierunekCzlowieka = LEFT;
                        break;
                    case VK_K:
                        swiat.kierunekCzlowieka = RIGHT;
                        break;
                    case VK_N:
                        swiat.kierunekCzlowieka = DOWN_LEFT;
                        break;
                    case VK_M:
                        swiat.kierunekCzlowieka = DOWN_RIGHT;
                        break;
                    case VK_ENTER:
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
