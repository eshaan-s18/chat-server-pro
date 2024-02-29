import static org.junit.Assert.*;
import org.junit.*;
import java.net.URI;

public class SemanticAnalysisHandlerTests {
  @Test
  public void handleRequest1() throws Exception {
    ChatHandler h = new ChatHandler();
    String url = "http://localhost:4000/chat?user=joe&message=hi";
    URI input = new URI(url);
    String expected = "joe: hi\n\n";
    assertEquals(expected, h.handleRequest(input));
  }

  @Test
  public void handleRequestMulti() throws Exception {
    ChatHandler h = new ChatHandler();
    String url1 = "http://localhost:4000/chat?user=onat&message=good%20luck";
    String url2 = "http://localhost:4000/chat?user=edwin&message=with%20your%20demo!";
    URI input1 = new URI(url1);
    URI input2 = new URI(url2);
    String expected = "onat: good luck\n\nedwin: with your demo!\n\n";
    h.handleRequest(input1);
    assertEquals(expected, h.handleRequest(input2));
  }

  @Test
  public void handleRequestSemanticAnalysis() throws Exception {
    ChatHandler h = new ChatHandler();
    String url1 = "http://localhost:4000/chat?user=onat&message=😂";
    String url2 = "http://localhost:4000/chat?user=onat&message=doggy🥹!!!";
    String url3 = "http://localhost:4000/chat?user=onat&message=TGIThanksgiving";
    String url4 = "http://localhost:4000/semantic-analysis?user=onat";
    String url5 = "http://localhost:4000/chat?user=eshaan&message=?~_~X~B";
    String url6 = "http://localhost:4000/semantic-analysis?user=eshaan";

    URI input1 = new URI(url1);
    URI input2 = new URI(url2);
    URI input3 = new URI(url3);
    URI input4 = new URI(url4);
    URI input5 = new URI(url5);
    URI input6 = new URI(url6);

    String expected = "onat: 😂 This message has a LOL vibe.\n\nonat: doggy🥹!!! This message has a awwww vibe. This message ends forcefully.\n\nonat: TGIThanksgiving\n\n";
    String expected2 = "eshaan: ?~_~X~B This message has a LOL vibe.";

    h.handleRequest(input1);
    h.handleRequest(input2);
    h.handleRequest(input3);
    h.handleRequest(input5);
    assertEquals(expected, h.handleRequest(input4));
    assertEquals(expected2, h.handleRequest(input6));
  }
}
