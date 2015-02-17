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

import org.protempa.proposition.value.BooleanValue;
import org.protempa.proposition.value.DateValue;
import org.protempa.proposition.value.InequalityNumberValue;
import org.protempa.proposition.value.NominalValue;
import org.protempa.proposition.value.NumberValue;
import org.protempa.proposition.value.OrdinalValue;
import org.protempa.proposition.value.Value;
import org.protempa.proposition.value.ValueList;
import org.protempa.proposition.value.ValueVisitor;

/**
 * @author hrathod
 */
public class RawValueVisitor implements ValueVisitor {
	Object value;

	@Override
	public void visit(NominalValue nominalValue) {
		String v = nominalValue.getFormatted();
		Object result;
		try {
			result = Long.parseLong(v);
		} catch (NumberFormatException e) {
			result = v;
		}
		this.value = result;
	}

	@Override
	public void visit(OrdinalValue ordinalValue) {
		this.value = ordinalValue.getValue();
	}

	@Override
	public void visit(BooleanValue booleanValue) {
		this.value = booleanValue.booleanValue();
	}

	@Override
	public void visit(ValueList<? extends Value> listValue) {
		throw new UnsupportedOperationException("Not supported yet!");
	}

	@Override
	public void visit(NumberValue numberValue) {
		this.value = numberValue.doubleValue();
	}

	@Override
	public void visit(InequalityNumberValue inequalityNumberValue) {
		this.value = inequalityNumberValue.doubleValue();
	}

	@Override
	public void visit(DateValue dateValue) {
		this.value = dateValue.getDate().getTime();
	}

	public Object getValue() {
		return value;
	}
}
