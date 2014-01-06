package edu.emory.cci.aiw;

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
