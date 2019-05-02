package se.liu.ida.vikbl327.drakborgen;

/**
 * The three different attacks that a hero/monster can perform. Each attack beats exaclty one attack and loses to another. To
 * invoke this logic use the beats method.
 */

public enum Attack
{

    A {
	@Override public boolean beats(Attack other) {
	    return other == B;
	}
    },
    B {
	@Override public boolean beats(Attack other) {
		    return other == C;
		}
    },
    C {
	@Override public boolean beats(Attack other) {
		    return other == A;
		}
    };

    public abstract boolean beats(Attack other);
}
