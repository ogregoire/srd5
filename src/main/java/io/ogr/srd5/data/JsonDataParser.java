/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ogr.srd5.data;

import com.google.common.collect.ImmutableList;
import com.google.common.io.ByteSource;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.Reader;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.List;

/**
 *
 * @author Olivier Grégoire <ogregoire@users.noreply.github.com>
 */
public class JsonDataParser {

  private static final String CURRENT_VERSION = "1";
  private static final ImmutableList<String> UNSUPPORTED_VERSIONS = ImmutableList.of();

  private final Gson gson;
  private final JsonParser parser;

  public JsonDataParser() {
    gson = new Gson();
    parser = new JsonParser();
  }

  public List<SrdClass> readClasses(ByteSource source) throws IOException {
    return readData(source, SrdClass.Holder.class).getClasses();
  }

  private <T> T readData(ByteSource source, Class<T> classOfT) throws IOException {
    JsonObject root;
    try (Reader reader = source.asCharSource(UTF_8).openStream()) {
      root = parser.parse(reader).getAsJsonObject();
    }
    String version = root.get("version").getAsString();
    if (CURRENT_VERSION.equals(version)) {
      JsonObject data = root.getAsJsonObject("data");
      return gson.fromJson(data, classOfT);
    } else if (UNSUPPORTED_VERSIONS.contains(version)) {
      throw new IOException("Unsupported data format version: " + version);
    } else {
      throw new IOException("Unknown data format version: " + version);
    }
  }
}
