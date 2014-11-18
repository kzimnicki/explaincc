import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ServerBuilder;
import com.twitter.finagle.http.Http;
import com.twitter.util.Future;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.*;
import org.jboss.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class HttpServer {

    public static void main(String[] args) {

        final NER ner = new NER();

        Service<HttpRequest, HttpResponse> service = new Service<HttpRequest, HttpResponse>() {
            @Override
            public Future<HttpResponse> apply(HttpRequest request) {
                String content = request.getContent().toString(CharsetUtil.UTF_8);
                String json = ner.process(content);
                HttpResponse res = new DefaultHttpResponse(
                        HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                res.setContent(ChannelBuffers.copiedBuffer(json,
                        CharsetUtil.UTF_8));
                res.setHeader(HttpHeaders.Names.CONTENT_TYPE,
                        "text/plain; charset=UTF-8");
                return Future.<HttpResponse> value(res);
            }
        };

        ServerBuilder.safeBuild(service,
                ServerBuilder.get().codec(Http.get()).name("HttpServer")
                        .bindTo(new InetSocketAddress("localhost", 10000)));
    }

}
