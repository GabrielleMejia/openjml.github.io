# Visibility modifiers and specifications

Java distinguishes four categories of visibility of declarations: public, protected, package, and private.
Foir example, within a class, all names, even private ones, declared in that class are visible.
But names private to one class are not visible outside that class.

Specifications have a visibility also. Typically a method specification has the same
visibility as the method declaration. That way a client seeing the method declaration
can see its specification also. But if that client is not permitted to see names
with more restricted visibility, then those names should not appear in the specifications either.

For example, this code
```
// openjml --esc T_Visibility1.java
public class T_Visibility1 {
    private int value;

    //@ ensures \result == value;
    public int value() {
        return value;
    }
}
```
voilates JML's visibility rules because I client that can see the public declaration of the 
method `value()` does not necessarily have visibility to the private declaration of `value`.
This error results:
```
T_Visibility1.java:5: error: An identifier with private visibility may not be used in a ensures clause with public visibility
    //@ ensures \result == value;
                           ^
1 error
```

So how is one to specify this simple getter method. The simple solution is simply to declare that
the private field is public _for specification purposes_.
The `spec_public` declaration does this:
```
// openjml --esc T_Visibility2.java
public class T_Visibility2 {
    //@ spec_public
    private int value;

    //@ ensures \result == value;
    public int value() {
        return value;
    }
}
```

There is a similar modifier, `spec_protected`, that declares that a declared name has
protected visibility _for specification purposes_.

But this solution leads easily to simply declaring all names as `spec_public`, which 
obviates the goal of having hiding an implementation in the first place. If an 
implementation is hidden in private declarations, exposed to a client only through
public methods, then we need a specificiation idiom that respect that.

That is one purpose of model fields, which are preented in the [next lesson](ModelFields).
But here we'll repeat out example using a model field.

```
// openjml --esc T_Visibility3.java
public class T_Visibility3 {
    private int value; //@ in _value;


    //@ public model int _value;
    //@ private represents _value = value;


    //@ ensures \result == _value;
    public int value() {
        return value;
    }
}
```