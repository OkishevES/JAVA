package ru.mytest.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("a1d18de2b3d8e7188bd20121f7bd6e4bd3fdeb86");
    RepoCommits commits = github.repos().get(new Coordinates
            .Simple("manyasha1975", "java_progfortester")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}