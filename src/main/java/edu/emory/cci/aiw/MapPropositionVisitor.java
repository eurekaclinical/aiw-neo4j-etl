package edu.emory.cci.aiw;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.protempa.proposition.AbstractParameter;
import org.protempa.proposition.Constant;
import org.protempa.proposition.Context;
import org.protempa.proposition.Event;
import org.protempa.proposition.PrimitiveParameter;
import org.protempa.proposition.Proposition;
import org.protempa.proposition.interval.Interval;
import org.protempa.proposition.value.Value;
import org.protempa.proposition.visitor.PropositionVisitor;

/**
 * @author hrathod
 */
public class MapPropositionVisitor implements PropositionVisitor {
	private final Map<String, Object> map = new HashMap<String, Object>();

	@Override
	public void visit(Map<String, List<Proposition>> finderResult) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public void visit(Collection<? extends Proposition> propositions) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public void visit(PrimitiveParameter primitiveParameter) {

		this.handleCommon(primitiveParameter);

		Interval interval = primitiveParameter.getInterval();
		this.map.put("length", interval.getMaxLength());
		this.map.put("lengthUnit", interval.getLengthUnit().getName());
		this.map.put("start", interval.getMinStart());
		this.map.put("finish", interval.getMaxFinish());
		this.map.put("granularity",
				primitiveParameter.getGranularity().getName());
		this.map.put("position", primitiveParameter.getPosition());

		Value value = primitiveParameter.getValue();
		if (value != null) {
			RawValueVisitor visitor = new RawValueVisitor();
			value.accept(visitor);
			this.map.put("value", visitor.getValue());
			this.map.put("valueType", value.getType().name());
		}
	}

	private void handleCommon(Proposition inProposition) {
		for (String s : inProposition.getPropertyNames()) {
			Value v = inProposition.getProperty(s);
			if (v != null) {
				RawValueVisitor visitor = new RawValueVisitor();
				v.accept(visitor);
				this.map.put(s, visitor.getValue());
			}
		}
	}

	@Override
	public void visit(Event event) {
		this.handleCommon(event);
		Interval interval = event.getInterval();
		this.map.put("length", interval.getMaxLength());
		this.map.put("lengthUnit", interval.getLengthUnit().getName());
		this.map.put("start", interval.getMinStart());
		this.map.put("finish", interval.getMaxFinish());
	}

	@Override
	public void visit(AbstractParameter abstractParameter) {
		this.handleCommon(abstractParameter);
		Interval interval = abstractParameter.getInterval();
		this.map.put("length", interval.getMaxLength());
		this.map.put("lengthUnit", interval.getLengthUnit().getName());
		this.map.put("start", interval.getMinStart());
		this.map.put("finish", interval.getMaxFinish());

		Value value = abstractParameter.getValue();
		if (value != null) {
			RawValueVisitor visitor = new RawValueVisitor();
			value.accept(visitor);
			this.map.put("value", visitor.getValue());
			this.map.put("valueType", value.getType().name());
		}
	}

	@Override
	public void visit(Constant constant) {
		this.handleCommon(constant);
	}

	@Override
	public void visit(Context context) {
		//To change body of implemented methods use File | Settings | File
		// Templates.
	}

	public Map<String, Object> getMap() {
		return map;
	}
}
