package ru.mytest.rest.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import ru.mytest.rest.model.Issue;

import java.util.List;

public class RestHelper {

  private ApplicationManager app;

  public RestHelper(ApplicationManager app) {
    this.app = app;
  }

  public String getStatusIssue(int issueId) {
    String json = RestAssured.get("http://bugify.stqa.ru/api/issues/" + issueId + ".json")
            .asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    List<Issue> result = new Gson().fromJson(issues, new TypeToken<List<Issue>>(){}.getType());
    System.out.println(result.get(0).getState_name());
    if (result.get(0).getState_name().equals("Closed")) {
      return "fixed";
    } else {
      return "not fixed";
    }
  }

}
