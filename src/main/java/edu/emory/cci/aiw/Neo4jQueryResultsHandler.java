package edu.emory.cci.aiw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.tooling.GlobalGraphOperations;
import org.protempa.KnowledgeSource;
import org.protempa.PropositionDefinition;
import org.protempa.ProtempaException;
import org.protempa.dest.QueryResultsHandler;
import org.protempa.dest.QueryResultsHandlerCloseException;
import org.protempa.dest.QueryResultsHandlerProcessingException;
import org.protempa.dest.QueryResultsHandlerValidationFailedException;
import org.protempa.proposition.Proposition;
import org.protempa.proposition.UniqueId;
import org.protempa.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hrathod
 */
public class Neo4jQueryResultsHandler implements QueryResultsHandler {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(Neo4jQueryResultsHandler.class);
	private final String dbPath;
	private final Map<UniqueId, Node> nodes;
	private final Map<String, RelationshipType> relations;
	private final Map<String, Derivations.Type> deriveType;
	private final KnowledgeSource knowledgeSource;
	private final Query query;
	private GraphDatabaseService db;
	private Index<Node> typeIndex;
	private Index<Relationship> relIndex;
	private int keysProcessed = 0;

	public Neo4jQueryResultsHandler(String inPath, KnowledgeSource inKnowledgeSource, Query inQuery) {
		this.dbPath = inPath;
		this.nodes = new HashMap<UniqueId, Node>();
		this.relations = new HashMap<String, RelationshipType>();
		this.deriveType = new HashMap<String, Derivations.Type>();
		this.knowledgeSource = inKnowledgeSource;
		this.query = inQuery;
	}

	@Override
	public void start() throws QueryResultsHandlerProcessingException {
		GraphDatabaseFactory factory = new GraphDatabaseFactory();
		this.db = factory.newEmbeddedDatabase(this.dbPath);
		for (Relationship r : GlobalGraphOperations.at(this.db).getAllRelationships()) {
			r.delete();
		}
		for (Node n : GlobalGraphOperations.at(this.db).getAllNodes()) {
			n.delete();
		}
		this.typeIndex = this.db.index().forNodes("__types");
		this.relIndex = this.db.index().forRelationships("__rels");
	}

	@Override
	public void handleQueryResult(String keyId,
			List<Proposition> propositions,
			Map<Proposition, List<Proposition>> forwardDerivations,
			Map<Proposition, List<Proposition>> backwardDerivations,
			Map<UniqueId, Proposition> references)
			throws QueryResultsHandlerProcessingException {

		LOGGER.info("Handling results for patient {}", keyId);

		// clear out the previous patient's data
		this.nodes.clear();

		// start a new transaction to load the patient's data
		Transaction transaction = this.db.beginTx();

		// now create relationships for the references
		handleReferences(propositions, references);

		try {
			// now create relationships for the forward derivations
			handleDerivations(forwardDerivations);

			// now create relationships for the backward derivations
			handleDerivations(backwardDerivations);
		} catch (ProtempaException e) {
			throw new QueryResultsHandlerProcessingException(e);
		}

		// now we commit the transaction
		transaction.success();
		transaction.close();

		// increment the number of patients we have added so far
		this.keysProcessed++;
	}

	private void createStatsNode () {
		Node node = this.db.createNode(Neo4jStatistics.NODE_LABEL);
		node.setProperty(Neo4jStatistics.TOTAL_PROP, this.keysProcessed);
	}

	@Override
	public void finish() throws QueryResultsHandlerProcessingException {
		// add/update a statistics node to save the number of patients added
		this.createStatsNode();
		this.db.shutdown();
	}

	@Override
	public void close() throws QueryResultsHandlerCloseException {
	}

	private Node node(Proposition inProposition) {
		MapPropositionVisitor visitor = new MapPropositionVisitor();
		Node node = this.db.createNode();
		node.setProperty("__type", inProposition.getId());
		this.typeIndex.add(node, "__type", node.getProperty("__type"));
		inProposition.accept(visitor);
		for (Map.Entry<String, Object> entry : visitor.getMap().entrySet()) {
			if (entry.getValue() != null) {
				node.setProperty(entry.getKey(), entry.getValue());
			}
		}
		return node;
	}

	private Node getOrCreateNode(Proposition inProposition) {
		if (!this.nodes.containsKey(inProposition.getUniqueId())) {
			this.nodes.put(
					inProposition.getUniqueId(), this.node(inProposition));
		}
		return this.nodes.get(inProposition.getUniqueId());
	}

	private void relate(Node source, Node target,
			RelationshipType inRelation) {
		Relationship relationship =
				source.createRelationshipTo(target, inRelation);
		this.relIndex.add(
				relationship, "name", relationship.getType().name());
	}

	private RelationshipType getOrCreateRelation(String name) {
		if (!this.relations.containsKey(name)) {
			DynamicRelationshipType relationshipType =
					DynamicRelationshipType.withName(name);
			this.relations.put(name, relationshipType);
		}
		return this.relations.get(name);
	}

	private void handleDerivations(
			Map<Proposition, List<Proposition>> forwardDerivations)
			throws ProtempaException {
		for (Map.Entry<Proposition, List<Proposition>> entry :
				forwardDerivations.entrySet()) {
			Proposition sourceProposition = entry.getKey();
			Node source = this.getOrCreateNode(sourceProposition);
			for (Proposition targetProposition : entry.getValue()) {
				Node target = this.getOrCreateNode(targetProposition);
				String derivationType = this.derivationType(
						sourceProposition, targetProposition);
				RelationshipType relation =
						this.getOrCreateRelation(derivationType);
				this.relate(source, target, relation);
			}
		}
	}

	private void handleReferences(List<Proposition> propositions,
			Map<UniqueId, Proposition> references)
			throws QueryResultsHandlerProcessingException {
		for (Proposition proposition : propositions) {
			Node source = this.getOrCreateNode(proposition);

			String[] names = proposition.getReferenceNames();
			for (String name : names) {
				List<UniqueId> ids = proposition.getReferences(name);
				RelationshipType relation = this.getOrCreateRelation(name);
				LOGGER.info(
						"Processing {} references with type {} for {}",
						ids.size(), name, proposition.getId());
				for (UniqueId id : ids) {
					Proposition targetProposition = references.get(id);
					if (targetProposition != null) {
						Node target = this.getOrCreateNode
								(targetProposition);
						this.relate(source, target, relation);
					} else {
						LOGGER.error("No proposition for {}", id);
						throw new QueryResultsHandlerProcessingException(
								"No proposition for id " + id);
					}
				}
			}
		}
	}

	private String derivationType(Proposition source, Proposition target)
			throws ProtempaException {
		Derivations.Type result;
		String key = source.getId() + "->" + target.getId();
		String inverseKey = target.getId() + "->" + source.getId();
		PropositionDefinition definition =
				this.knowledgeSource.readPropositionDefinition(
						source.getId());

		if (this.deriveType.containsKey(key)) {
			result = this.deriveType.get(key);
		} else if (this.deriveType.containsKey(inverseKey)) {
			result = Derivations.inverse(this.deriveType.get(inverseKey));
		} else {
			PropositionDefinitionRelationVisitor visitor =
					new PropositionDefinitionRelationVisitor(
							this.knowledgeSource, target.getId());
			definition.acceptChecked(visitor);
			result = visitor.getRelation();
			this.deriveType.put(key, result);
			this.deriveType.put(inverseKey, Derivations.inverse(result));
		}
		return result.name();
	}

	@Override
	public void validate()
			throws QueryResultsHandlerValidationFailedException {
	}

	@Override
	public String[] getPropositionIdsNeeded() {
		return new String[0];
	}
}
