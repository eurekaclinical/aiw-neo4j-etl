package edu.emory.cci.aiw;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;
import org.protempa.dest.Statistics;

/**
 * @author hrathod
 */
public class Neo4jStatistics implements Statistics {

	public static final Label NODE_LABEL = DynamicLabel.label("eureka_statistics");
	public static final String TOTAL_PROP = "numKeys";
	private final String dbPath;

	public Neo4jStatistics(String inDbPath) {
		this.dbPath = inDbPath;
	}

	@Override
	public int getNumberOfKeys() {
		GraphDatabaseFactory factory = new GraphDatabaseFactory();
		GraphDatabaseService database = factory.newEmbeddedDatabase(this.dbPath);
		Transaction transaction = database.beginTx();
		ResourceIterator<Node> nodeIter = GlobalGraphOperations.at(database).getAllNodesWithLabel(NODE_LABEL).iterator();
		Node node = nodeIter.next();
		Object numKeys = node.getProperty(TOTAL_PROP);
		nodeIter.close();
		transaction.success();
		database.shutdown();
		return (Integer) numKeys;
	}
}
