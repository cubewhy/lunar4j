package top.lunarclient.lunar4j;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import top.lunarclient.utils.OSEnum;
import top.lunarclient.utils.RequestUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

@SuppressWarnings("unused")
public class LauncherData {
    public final URI api;

    /**
     * Create a LauncherData instance with special API resource
     *
     * @param api Launcher API
     * */
    public LauncherData(URI api) {
        super();
        this.api = api;
    }

    /**
     * Create a LauncherData instance with the official Launcher API
     * */
    public LauncherData() {
        this(URI.create("https://api.lunarclientprod.com")); // official API
    }

    /**
     * Create a LauncherData instance with special API resource
     *
     * @param api Launcher API
     * */
    public LauncherData(String api) {
        this(URI.create(api));
    }

    /**
     * Create a LauncherData instance with special API resource
     *
     * @param api Launcher API
     * */
    public LauncherData(@NotNull URL api) throws URISyntaxException {
        this(api.toURI());
    }

    /**
     * Get metadata
     *
     * @return Launcher Metadata
     * */
    public String metadata() throws IOException {
        // do request with fake system info
        try (Response response = RequestUtils.get(api + "/launcher/metadata" +
                "?os=linux" +
                "&arch=x64" +
                "&launcher_version=2.15.2").execute()) {
            assert response.code() == 200 : "Code = " + response.code(); // check success
            assert response.body() != null : "ResponseBody was null";
            return response.body().string();
        }
    }

    public JsonElement getArtifacts(String version, String branch, String module) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("hwid", "HWID-PUBLIC");
        json.addProperty("installation_id", "INSTALL_ID");
        json.addProperty("os", Objects.requireNonNull(OSEnum.find(System.getProperty("os.name"))).jsName);
        json.addProperty("arch", "x64"); // example: x64
        json.addProperty("os_release", "19045.3086");
        json.addProperty("launcher_version", "2.15.1");
        json.addProperty("launch_type", "lunar");
        json.addProperty("version", version);
        json.addProperty("branch", branch);
        json.addProperty("module", module);

        try (Response response = RequestUtils.post(api + "/launcher/launch", new Gson().toJson(json)).execute()) {
            assert response.body() != null : "ResponseBody was null";
            return JsonParser.parseString(response.body().string());
        }
    }


}
