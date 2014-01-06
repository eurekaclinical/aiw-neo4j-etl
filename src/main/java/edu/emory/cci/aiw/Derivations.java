package edu.emory.cci.aiw;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hrathod
 */
public class Derivations {

	public enum Type {
		abstractedFrom,
		abstractedInto,
		inverseIsA,
		isA,
		inducedBy,
		induces,
		subContext,
		superContext,
		child,
		subContextOf, parent
	}

	private static final Map<Type, Type> inverseMap = new HashMap<Type,Type>();
	static {
		inverseMap.put(Type.abstractedFrom, Type.abstractedInto);
		inverseMap.put(Type.abstractedInto, Type.abstractedFrom);

		inverseMap.put(Type.inverseIsA, Type.isA);
		inverseMap.put(Type.isA, Type.inverseIsA);

		inverseMap.put(Type.inducedBy, Type.induces);
		inverseMap.put(Type.induces, Type.inducedBy);

		inverseMap.put(Type.subContext, Type.superContext);
		inverseMap.put(Type.superContext, Type.subContext);

		inverseMap.put(Type.child, Type.parent);
		inverseMap.put(Type.parent, Type.child);
	}

	public static Type inverse(Type inType) {
		return inverseMap.get(inType);
	}
}
