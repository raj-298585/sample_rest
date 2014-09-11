package com.star.ric.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.star.ric.mongo.util.MongoDBConnection;

@Path("/hello")
public class HelloWorldService {
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		String output = "Welcome : " + msg;
		return Response.status(200).entity(output).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String sayPlainTextHello(InputStream incomingData)
			throws IOException, JSONException {
		StringBuilder crunchifyBuilder = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				incomingData));
		String line = null;
		while ((line = in.readLine()) != null) {
			crunchifyBuilder.append(line);
		}
		JSONObject json = new JSONObject(crunchifyBuilder.toString());
		String[] inputs = JSONObject.getNames(json);
		Iterator keyItr = json.keys();
		BasicDBObject whereQuery = new BasicDBObject();
		while (keyItr.hasNext()) {
			String key = (String) keyItr.next();
			whereQuery.put(key, json.get(key));

		}
		DBCursor cur = MongoDBConnection.getConnection().getCollection("user")
				.find(whereQuery);
		while (cur.hasNext()) {
			DBObject row = (DBObject) cur.next();
			cur.close();
			return row.toString();
		}
		return "{ \"NILL\" : \"No More Records\"}";
	}

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html> " + "<title>" + "Hello Jersey" + "</title>"
				+ "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
	}

}
