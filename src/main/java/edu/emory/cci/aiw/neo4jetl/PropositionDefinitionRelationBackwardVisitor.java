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

import java.util.Collection;
import org.arp.javautil.arrays.Arrays;

import org.protempa.CompoundLowLevelAbstractionDefinition;
import org.protempa.ConstantDefinition;
import org.protempa.ContextDefinition;
import org.protempa.EventDefinition;
import org.protempa.HighLevelAbstractionDefinition;
import org.protempa.KnowledgeSourceReadException;
import org.protempa.LowLevelAbstractionDefinition;
import org.protempa.PrimitiveParameterDefinition;
import org.protempa.PropositionDefinition;
import org.protempa.ProtempaException;
import org.protempa.SequentialTemporalPatternDefinition;
import org.protempa.SliceDefinition;
import org.protempa.TemporalExtendedPropositionDefinition;

/**
 * @author hrathod
 */
public class PropositionDefinitionRelationBackwardVisitor extends
		PropositionDefinitionRelationVisitor {

	PropositionDefinitionRelationBackwardVisitor() throws KnowledgeSourceReadException {
	}
	
	@Override
	public void visit(ContextDefinition def) throws ProtempaException {
		if (getTarget() instanceof ContextDefinition) {
			ContextDefinition cd = (ContextDefinition) getTarget();
			TemporalExtendedPropositionDefinition[] inducedBy = cd.getInducedBy();
			for (TemporalExtendedPropositionDefinition tepd : inducedBy) {
				if (tepd.getPropositionId().equals(def.getId())) {
					setRelation(Derivations.Type.subContextOf);
					return;
				}
			}
			if (Arrays.contains(cd.getInducedBy(), def.getId())) {
				setRelation(Derivations.Type.inducedBy);
			}
		} else {
			throw new UnsupportedOperationException(
				"Visiting a context definition is not supported by this visitor.");
		}
	}
	
	@Override
	public void visit(LowLevelAbstractionDefinition def)
			throws ProtempaException {
		setRelation(Derivations.Type.abstractedFrom);
	}

	@Override
	public void visit(SequentialTemporalPatternDefinition def)
			throws ProtempaException {
		setRelation(Derivations.Type.abstractedFrom);
	}

	@Override
	public void visit(ConstantDefinition def) throws ProtempaException {
		throw new UnsupportedOperationException(
				"Visiting a constant definition is not supported by this visitor.");
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
		setRelation(Derivations.Type.abstractedFrom);
	}

	@Override
	public void visit(HighLevelAbstractionDefinition def)
			throws ProtempaException {
		setRelation(Derivations.Type.abstractedFrom);
	}

	@Override
	public void visit(SliceDefinition def) throws ProtempaException {
		setRelation(Derivations.Type.abstractedFrom);
	}

	@Override
	public void visit(EventDefinition def) throws ProtempaException {
		throw new UnsupportedOperationException(
				"Visiting an event definition is not supported by this visitor.");
	}

	@Override
	public void visit(PrimitiveParameterDefinition def)
			throws ProtempaException {
		throw new UnsupportedOperationException(
				"Visiting a primitive parameter definition is not supported by this visitor.");
	}
	
}