package org.blackbird.db;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoUtil {

	private static final int port = 27017;
	private static final String host = "localhost";
	private static Mongo mongo = null;
	private static Logger log = Logger.getLogger(MongoUtil.class);

	public static Mongo getMongo() {
		if (mongo == null) {
			try {
				mongo = new Mongo(host, port);
			} catch (UnknownHostException e) {
				log.error(e.getMessage(), e);
			} catch (MongoException e) {
				log.error(e.getMessage(), e);
			}
		}
		return mongo;
	}
}
