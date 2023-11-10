package top.lunarclient.tests;

import org.junit.jupiter.api.Test;
import top.lunarclient.lunar4j.LauncherData;

import java.io.IOException;

public class TestMeta {
    LauncherData official = new LauncherData();
    LauncherData myServer = new LauncherData("https://api.lunarclient.top");

    @Test
    public void testMetadata() throws IOException {
        System.out.println("official.metadata() = " + official.metadata());

        System.out.println("myServer.metadata() = " + myServer.metadata());
    }

    @Test
    public void test1_8() throws Exception {
        System.out.println("official.getArtifacts(\"1.8.9\", \"master\", \"lunar\") = " + official.getArtifacts("1.8.9", "master", "lunar"));
    }
}
