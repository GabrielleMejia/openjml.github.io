// openjml --esc $@
public class T_Visibility2 {
    //@ spec_public
    private int _value;

    //@ ensures \result == _value;
    public int value() {
        return _value;
    }
}
