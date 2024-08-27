package page.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class Report {
	
	
public static void generate(String scenario, WebDriver driver, Exception exception) throws IOException {
		
		String errorDetails = details(driver,exception);
		String reportName = takeScreenShot(driver, scenario);
		
        RestAssured.baseURI = "https://portfolio012.atlassian.net/";

        String user = "kurt.bradley.jocson@gmail.com";
        String token = "ATATT3xFfGF0qU0UrlEimW8ROPxY3_5pnmqXYNlVtkLE6Y7yQ91CsOtGkBmbKJEYzipnaeg5mi0YAnYjO41hoYa5AS1Z8krRGlseCg8RfwzM-j1-5Eu3CPUCjJ7XcPR4rIl25X5bcn6rVZnf0nK2FBzbpeAPh2jP0fNrNZ_vsAWTWJC-Xu2g8Lc=A53DF232";
        String encodedCredentials = Base64.getEncoder().encodeToString((user + ":" + token).getBytes());
        String key = "CUS";

        // Construct the JSON payload for issue creation using ADF for description
        Map<String, Object> fields = new HashMap<>();
        Map<String, Object> project = new HashMap<>();
        Map<String, Object> issuetype = new HashMap<>();

        project.put("key", key);
        issuetype.put("name", "Bug");

        fields.put("project", project);
        fields.put("summary", scenario + " - " + reportName);

        // ADF-compliant description
        Map<String, Object> description = new HashMap<>();
        description.put("type", "doc");
        description.put("version", 1);

        // Creating content for the description
        Map<String, Object> paragraph = new HashMap<>();
        paragraph.put("type", "paragraph");
        Map<String, Object> text = new HashMap<>();
        text.put("type", "text");
        text.put("text", "description");

        // Adding the text content to the paragraph
        paragraph.put("content", new Map[] { text });

        // Adding the paragraph to the description content
        description.put("content", new Map[] { paragraph });

        fields.put("description", description);
        fields.put("issuetype", issuetype);

        Map<String, Object> issueDetails = new HashMap<>();
        issueDetails.put("fields", fields);

        Gson gson = new Gson();
        String jsonData = gson.toJson(issueDetails);

        Response response = RestAssured.given().header("Authorization", "Basic " + encodedCredentials)
                .header("Content-Type", "application/json").body(jsonData).when().post("/rest/api/3/issue");

        // Error handling
        if (response.getStatusCode() != 201) {
            System.err.println("Failed to create issue: " + response.getStatusLine());
            return;
        }

        String issueKey = response.jsonPath().getString("key");

        File file = new File("C:/Users/kurtb/eclipse-workspace/SeleniumFramework/screenshots/" + reportName +".png");

        if (file.exists()) {
            RestAssured.given().header("Authorization", "Basic " + encodedCredentials)
                    .header("X-Atlassian-Token", "no-check").multiPart(file).when()
                    .post("/rest/api/2/issue/" + issueKey + "/attachments").then().log().ifError();
        } else {
            System.err.println("Screenshot file not found: " + file.getAbsolutePath());
        }

        // Construct JSON for the comment in Atlassian Document Format (ADF)
        Map<String, Object> adfComment = new HashMap<>();
        adfComment.put("type", "doc");
        adfComment.put("version", 1);

        Map<String, Object> commentParagraph = new HashMap<>();
        commentParagraph.put("type", "paragraph");

        Map<String, Object> commentText = new HashMap<>();
        commentText.put("type", "text");

        commentText.put("text", "Error details: " + errorDetails + " \n Please see attached image @" + reportName + ".png");

        commentParagraph.put("content", new Map[] { commentText });
        adfComment.put("content", new Map[] { commentParagraph });

        String commentJson = gson.toJson(adfComment);

        // Post the comment to the issue
        RestAssured.given().header("Authorization", "Basic " + encodedCredentials)
                .header("Content-Type", "application/json").body("{ \"body\": " + commentJson + " }").when()
                .post("/rest/api/3/issue/" + issueKey + "/comment").then().log().ifError();
	}

	private static String details(WebDriver driver, Exception e) {
		return e.getMessage().subSequence(80, 90).toString();
	}
	

	private static String takeScreenShot(WebDriver driver, String scenario) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

		String reportName = scenario + "_" + timestamp;

		FileUtils.copyFile(scrFile, new File(
				"C:/Users/kurtb/eclipse-workspace/SeleniumFramework/screenshots/" + date + "/" + reportName + ".png"));
		
		return date+"/"+reportName;
	}

}
