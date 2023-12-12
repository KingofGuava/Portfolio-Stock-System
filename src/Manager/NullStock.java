package Manager;

public class NullStock extends Stock{

    public NullStock(){
        super("",0,0,0);
    }

    public boolean isNull(){
        return true;
    }

}
