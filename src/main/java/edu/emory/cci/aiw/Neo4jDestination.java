package edu.emory.cci.aiw;

import org.protempa.DataSource;
import org.protempa.KnowledgeSource;
import org.protempa.dest.Destination;
import org.protempa.dest.QueryResultsHandler;
import org.protempa.dest.QueryResultsHandlerInitException;
import org.protempa.dest.Statistics;
import org.protempa.dest.StatisticsException;
import org.protempa.query.Query;

/**
 * @author hrathod
 */
public class Neo4jDestination implements Destination {

	private final String dbPath;

	public Neo4jDestination (String inDbPath) {
		this.dbPath = inDbPath;
	}

	@Override
	public QueryResultsHandler getQueryResultsHandler(Query query, DataSource dataSource, KnowledgeSource knowledgeSource) throws QueryResultsHandlerInitException {
		return new Neo4jQueryResultsHandler(this.dbPath, knowledgeSource, query);
	}

	@Override
	public Statistics getStatistics() throws StatisticsException {
		return new Neo4jStatistics(this.dbPath);
	}
}
