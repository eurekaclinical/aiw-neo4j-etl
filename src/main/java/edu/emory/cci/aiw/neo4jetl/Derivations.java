package edu.emory.cci.aiw.neo4jetl;

/*
 * #%L
 * AIW Neo4j ETL
 * %%
 * Copyright (C) 2015 Emory University
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
		subContextOf
	}

	private static final Map<Type, Type> inverseMap = new HashMap<>();
	static {
		inverseMap.put(Type.abstractedFrom, Type.abstractedInto);
		inverseMap.put(Type.abstractedInto, Type.abstractedFrom);

		inverseMap.put(Type.inverseIsA, Type.isA);
		inverseMap.put(Type.isA, Type.inverseIsA);

		inverseMap.put(Type.inducedBy, Type.induces);
		inverseMap.put(Type.induces, Type.inducedBy);

		inverseMap.put(Type.subContext, Type.subContextOf);
		inverseMap.put(Type.subContextOf, Type.subContext);

	}

	public static Type inverse(Type inType) {
		return inverseMap.get(inType);
	}
}
