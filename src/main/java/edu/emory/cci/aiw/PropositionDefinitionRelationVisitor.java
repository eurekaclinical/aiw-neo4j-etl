package edu.emory.cci.aiw;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.protempa.CompoundLowLevelAbstractionDefinition;
import org.protempa.ConstantDefinition;
import org.protempa.ContextDefinition;
import org.protempa.EventDefinition;
import org.protempa.HighLevelAbstractionDefinition;
import org.protempa.KnowledgeSource;
import org.protempa.KnowledgeSourceReadException;
import org.protempa.LowLevelAbstractionDefinition;
import org.protempa.PrimitiveParameterDefinition;
import org.protempa.PropositionDefinition;
import org.protempa.PropositionDefinitionCheckedVisitor;
import org.protempa.ProtempaException;
import org.protempa.SequentialTemporalPatternDefinition;
import org.protempa.SliceDefinition;

/**
 * @author hrathod
 */
public class PropositionDefinitionRelationVisitor implements
		PropositionDefinitionCheckedVisitor {


	private final KnowledgeSource knowledgeSource;
	private final PropositionDefinition target;
	private Derivations.Type relation;

	PropositionDefinitionRelationVisitor(KnowledgeSource inKnowledgeSource,
			String inTargetId) throws KnowledgeSourceReadException {
		this.knowledgeSource = inKnowledgeSource;
		this.target =
				this.knowledgeSource.readPropositionDefinition(inTargetId);
	}

	@Override
	public void visit(LowLevelAbstractionDefinition def)
			throws ProtempaException {
		if (this.contains(
				this.knowledgeSource.readAbstractedFrom(def), this.target)) {
			this.relation = Derivations.Type.abstractedFrom;
		} else if (this.contains(
				this.knowledgeSource.readAbstractedInto(def), this.target)) {
			this.relation = Derivations.Type.abstractedInto;
		} else if (this.contains(
				this.knowledgeSource.readInverseIsA(def), this.target)) {
			this.relation = Derivations.Type.inverseIsA;
		} else if (this.contains(
				this.knowledgeSource.readIsA(def), this.target)) {
			this.relation = Derivations.Type.isA;
		} else if (this.contains(
				this.knowledgeSource.readParents(def), this.target)) {
			this.relation = Derivations.Type.parent;
		}
	}

	@Override
	public void visit(ContextDefinition def) throws ProtempaException {
		if (this.contains(
				this.knowledgeSource.readInducedBy(def), this.target)) {
			this.relation = Derivations.Type.inducedBy;
		} else if (this.contains(
				this.knowledgeSource.readSubContextOfs(def), this.target)) {
			this.relation = Derivations.Type.subContextOf;
		} else if (this.contains(
				this.knowledgeSource.readSubContexts(def), this.target)) {
			this.relation = Derivations.Type.subContext;
		} else if (this.contains(
				this.knowledgeSource.readAbstractedInto(def), this.target)) {
			this.relation = Derivations.Type.abstractedInto;
		} else if (this.contains(
				this.knowledgeSource.readInverseIsA(def), this.target)) {
			this.relation = Derivations.Type.inverseIsA;
		} else if (this.contains(
				this.knowledgeSource.readIsA(def), this.target)) {
			this.relation = Derivations.Type.isA;
		} else if (this.contains(
				this.knowledgeSource.readParents(def), this.target)) {
			this.relation = Derivations.Type.parent;
		}
	}

	@Override
	public void visit(SequentialTemporalPatternDefinition def)
			throws ProtempaException {
		if (this.contains(
				this.knowledgeSource.readAbstractedFrom(def), this.target)) {
			this.relation = Derivations.Type.abstractedFrom;
		} else if (this.contains(
				this.knowledgeSource.readAbstractedInto(def), this.target)) {
			this.relation = Derivations.Type.abstractedInto;
		} else if (this.contains(
				this.knowledgeSource.readInverseIsA(def), this.target)) {
			this.relation = Derivations.Type.inverseIsA;
		} else if (this.contains(
				this.knowledgeSource.readIsA(def), this.target)) {
			this.relation = Derivations.Type.isA;
		} else if (this.contains(
				this.knowledgeSource.readParents(def), this.target)) {
			this.relation = Derivations.Type.parent;
		} else if (this.contains(
				this.knowledgeSource.readInduces(def), this.target)) {
			this.relation = Derivations.Type.induces;
		}
	}

	@Override
	public void visit(ConstantDefinition def) throws ProtempaException {
		if (this.contains(
				this.knowledgeSource.readAbstractedInto(def), this.target)) {
			this.relation = Derivations.Type.abstractedInto;
		} else if (this.contains(
				this.knowledgeSource.readInverseIsA(def), this.target)) {
			this.relation = Derivations.Type.inverseIsA;
		} else if (this.contains(
				this.knowledgeSource.readIsA(def), this.target)) {
			this.relation = Derivations.Type.isA;
		} else if (this.contains(
				this.knowledgeSource.readParents(def), this.target)) {
			this.relation = Derivations.Type.parent;
		}
	}

	@Override
	public void visit(
			Collection<PropositionDefinition> propositionDefinitions)
			throws ProtempaException {
		throw new UnsupportedOperationException(
				"Visiting collections is not supported by this visitor.");
	}

	@Override
	public void visit(CompoundLowLevelAbstractionDefinition def)
			throws ProtempaException {
		if (this.contains(
				this.knowledgeSource.readAbstractedFrom(def), this.target)) {
			this.relation = Derivations.Type.abstractedFrom;
		} else if (this.contains(
				this.knowledgeSource.readAbstractedInto(def), this.target)) {
			this.relation = Derivations.Type.abstractedInto;
		} else if (this.contains(
				this.knowledgeSource.readInverseIsA(def), this.target)) {
			this.relation = Derivations.Type.inverseIsA;
		} else if (this.contains(
				this.knowledgeSource.readIsA(def), this.target)) {
			this.relation = Derivations.Type.isA;
		} else if (this.contains(
				this.knowledgeSource.readParents(def), this.target)) {
			this.relation = Derivations.Type.parent;
		} else if (this.contains(
				this.knowledgeSource.readInduces(def), this.target)) {
			this.relation = Derivations.Type.induces;
		}
	}

	@Override
	public void visit(HighLevelAbstractionDefinition def)
			throws ProtempaException {
		if (this.contains(
				this.knowledgeSource.readAbstractedFrom(def), this.target)) {
			this.relation = Derivations.Type.abstractedFrom;
		} else if (this.contains(
				this.knowledgeSource.readAbstractedInto(def), this.target)) {
			this.relation = Derivations.Type.abstractedInto;
		} else if (this.contains(
				this.knowledgeSource.readInverseIsA(def), this.target)) {
			this.relation = Derivations.Type.inverseIsA;
		} else if (this.contains(
				this.knowledgeSource.readIsA(def), this.target)) {
			this.relation = Derivations.Type.isA;
		} else if (this.contains(
				this.knowledgeSource.readParents(def), this.target)) {
			this.relation = Derivations.Type.parent;
		} else if (this.contains(
				this.knowledgeSource.readInduces(def), this.target)) {
			this.relation = Derivations.Type.induces;
		}
	}

	@Override
	public void visit(SliceDefinition def) throws ProtempaException {
		if (this.contains(
				this.knowledgeSource.readAbstractedFrom(def), this.target)) {
			this.relation = Derivations.Type.abstractedFrom;
		} else if (this.contains(
				this.knowledgeSource.readAbstractedInto(def), this.target)) {
			this.relation = Derivations.Type.abstractedInto;
		} else if (this.contains(
				this.knowledgeSource.readInverseIsA(def), this.target)) {
			this.relation = Derivations.Type.inverseIsA;
		} else if (this.contains(
				this.knowledgeSource.readIsA(def), this.target)) {
			this.relation = Derivations.Type.isA;
		} else if (this.contains(
				this.knowledgeSource.readParents(def), this.target)) {
			this.relation = Derivations.Type.parent;
		} else if (this.contains(
				this.knowledgeSource.readInduces(def), this.target)) {
			this.relation = Derivations.Type.induces;
		}
	}

	@Override
	public void visit(EventDefinition def) throws ProtempaException {
		if (this.contains(
				this.knowledgeSource.readAbstractedInto(def), this.target)) {
			this.relation = Derivations.Type.abstractedInto;
		} else if (this.contains(
				this.knowledgeSource.readInverseIsA(def), this.target)) {
			this.relation = Derivations.Type.inverseIsA;
		} else if (this.contains(
				this.knowledgeSource.readIsA(def), this.target)) {
			this.relation = Derivations.Type.isA;
		} else if (this.contains(
				this.knowledgeSource.readParents(def), this.target)) {
			this.relation = Derivations.Type.parent;
		} else if (this.contains(
				this.knowledgeSource.readInduces(def), this.target)) {
			this.relation = Derivations.Type.induces;
		}
	}

	@Override
	public void visit(PrimitiveParameterDefinition def)
			throws ProtempaException {
		if (this.contains(
				this.knowledgeSource.readAbstractedInto(def), this.target)) {
			this.relation = Derivations.Type.abstractedInto;
		} else if (this.contains(
				this.knowledgeSource.readInverseIsA(def), this.target)) {
			this.relation = Derivations.Type.inverseIsA;
		} else if (this.contains(
				this.knowledgeSource.readIsA(def), this.target)) {
			this.relation = Derivations.Type.isA;
		} else if (this.contains(
				this.knowledgeSource.readParents(def), this.target)) {
			this.relation = Derivations.Type.parent;
		} else if (this.contains(
				this.knowledgeSource.readInduces(def), this.target)) {
			this.relation = Derivations.Type.induces;
		}
	}

	private boolean contains(
			List<? extends PropositionDefinition> inDefinitions,
			PropositionDefinition inTarget) {
		Set<PropositionDefinition> defSet =
				new HashSet<PropositionDefinition>();
		defSet.addAll(inDefinitions);
		return defSet.contains(inTarget);
	}

	public Derivations.Type getRelation() {
		return this.relation;
	}
}
