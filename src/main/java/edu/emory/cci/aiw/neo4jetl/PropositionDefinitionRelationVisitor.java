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

import org.protempa.PropositionDefinition;
import org.protempa.PropositionDefinitionCheckedVisitor;

/**
 *
 * @author Andrew Post
 */
abstract class PropositionDefinitionRelationVisitor implements PropositionDefinitionCheckedVisitor {
	private PropositionDefinition target;
	private Derivations.Type relation;

	Derivations.Type getRelation() {
		return this.relation;
	}
	
	void setRelation(Derivations.Type relation) {
		this.relation = relation;
	}
	
	void setTarget(PropositionDefinition target) {
		this.target = target;
	}
	
	PropositionDefinition getTarget() {
		return this.target;
	}
	
	void clear() {
		this.relation = null;
	}
    
}
