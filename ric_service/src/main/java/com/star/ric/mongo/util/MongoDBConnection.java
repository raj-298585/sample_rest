package com.star.ric.mongo.util;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDBConnection {
	private static MongoClient dataSource;

	static {
		try {
			dataSource = new MongoClient("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static DB getConnection() {
		return dataSource.getDB("test");
	}

}
