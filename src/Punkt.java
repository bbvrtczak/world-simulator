public class Punkt {

    private int x,y;

    public Punkt(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Punkt(){
        x = 0;
        y = 0;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public String toString(){
        return "Punkt(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Punkt){
            Punkt other = (Punkt)obj;
            return x == other.x && y == other.y;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return 59 * Double.hashCode(x + y) + 19;
    }


    public boolean compare(Punkt p2){
        return this.x == p2.getX() && this.y == p2.getY();
    }

}