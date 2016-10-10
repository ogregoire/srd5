/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ogr.srd5.data;

import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import java.io.IOException;
import static java.nio.charset.StandardCharsets.UTF_8;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Olivier Grégoire <ogregoire@users.noreply.github.com>
 */
public class JsonDataParserTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private JsonDataParser parser;

  @Before
  public void setUp() {
    parser = new JsonDataParser();
  }

  @Test
  public void testReadData_success() throws IOException {
    String data = "{\n"
            + "  \"version\": \"1\",\n"
            + "  \"data\": {\n"
            + "    \"foo\": \"bar\"\n"
            + "  }\n"
            + "}\n"
            + "";

    String bar = parser.readData(asSource(data), Foo.class).foo;

    assertThat(bar, is(equalTo("bar")));
  }

  @Test
  public void testReadData_unknownVersion() throws IOException {
    String data = "{\n"
            + "  \"version\": \"foo\",\n"
            + "  \"data\": {\n"
            + "    \"foo\": \"bar\"\n"
            + "  }\n"
            + "}\n"
            + "";
    thrown.expect(IOException.class);
    thrown.expectMessage(Matchers.containsString("foo"));

    parser.readData(asSource(data), Foo.class);
  }

  @Test
  public void testReadClasses() {
    ByteSource source = Resources.asByteSource(Resources.getResource("data/classes.json"));
    try {
      parser.readClasses(source);
    } catch (Exception e) {
      fail("data/classes.json isn't well formed: " + e.getMessage());
    }
  }

  private static ByteSource asSource(String data) {
    return CharSource.wrap(data).asByteSource(UTF_8);
  }

  static class Foo {

    String foo;
  }
}
