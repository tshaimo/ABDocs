package za.co.africanbank.datascience.abdocs.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.africanbank.datascience.abdocs.constant.*;
import za.co.africanbank.datascience.abdocs.dao.ABDocsDao;
import za.co.africanbank.datascience.abdocs.dto.Root;
import za.co.africanbank.datascience.abdocs.properties.properties;
import za.co.africanbank.datascience.abdocs.transposer.Transponser;

@Component
public class ABDocsService {

	@Autowired
	ABDocsDao dao;

	public void processData() throws IOException, ParseException {

		String[] pathnames;
		JSONParser jsonParser = new JSONParser();
		String path = new properties(constant.PROPERTYFILE.value()).read("path");
		String newPath = new properties(constant.PROPERTYFILE.value()).read("newPath");

		File f = new File(path);
		pathnames = f.list();

		for (String pathname : pathnames) {

			System.out.println(pathname);
			String fileName = pathname; 
			File file = new File(path + fileName);


			FileBody fileBody = new FileBody(file, ContentType.MULTIPART_FORM_DATA);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.addPart("eml_blob", fileBody);
			HttpEntity entity = builder.build();
			HttpPost request = new HttpPost(new properties(constant.PROPERTYFILE.value()).read("ABDocsURL"));
			request.setEntity(entity);
			HttpClient client = HttpClientBuilder.create().build();

			try {
				HttpResponse response =client.execute(request);
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				StringBuffer result = new StringBuffer();
				String line = "";
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}

				String JsonData= String.valueOf(result);
				JSONObject JsonObj = (JSONObject) jsonParser.parse(JsonData);
				Root root =  new Transponser().play(JsonObj.toString());
				UUID uuid = UUID.randomUUID();
					
					dao.saveDocuments(root,uuid);
					dao.saveEmails(root,uuid);
					dao.saveLinks(root,uuid);
				

			} catch (IOException e) {
				e.printStackTrace();
			}

			Files.move(Paths.get(path + fileName), Paths.get(newPath + fileName),StandardCopyOption.REPLACE_EXISTING);

		}

	}

}
