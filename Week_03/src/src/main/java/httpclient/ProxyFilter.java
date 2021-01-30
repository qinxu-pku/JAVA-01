package httpclient;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;


public class ProxyFilter implements HttpRequestFilter {

    public static ProxyFilter newInstance() {
        return new ProxyFilter();
    }

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String uri = fullRequest.uri();
        System.out.println(" filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx)接收到的请求,url: " + uri);

        HttpHeaders headers = fullRequest.headers();
        if (headers == null) {
            headers = new DefaultHttpHeaders();
        }
        headers.add("mao", this.getClass().getSimpleName());
    }
}