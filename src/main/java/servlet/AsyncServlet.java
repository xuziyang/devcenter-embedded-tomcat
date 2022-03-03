package servlet;


import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet(urlPatterns = {"/async"}, asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    //Web应用线程池，用来处理异步Servlet
    ExecutorService executor = Executors.newSingleThreadExecutor();

    public void service(HttpServletRequest req, HttpServletResponse resp) {
        //1. 调用startAsync或者异步上下文
        final AsyncContext ctx = req.startAsync();

        //用线程池来执行耗时操作
        executor.execute(() -> {

            //在这里做耗时的操作
            try {
                Thread.sleep(100);
                ctx.getResponse().getWriter().println("Handling Async Servlet");
            } catch (IOException | InterruptedException e) {
            }

            //3. 异步Servlet处理完了调用异步上下文的complete方法
            ctx.complete();
        });
    }
}
