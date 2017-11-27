package com.example.datastore;

import com.example.datastore.model.DTO;
import com.google.appengine.api.datastore.QueryResultIterator;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ScanServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String scrollId = req.getParameter("scrollId");
        final String limitParam = req.getParameter("limit");
        final int limit = limitParam != null ? Math.min(Integer.valueOf(limitParam), 25000) : 1000;

        int counter = 0;
        StringBuffer sb = new StringBuffer(limit * 8);

        long iterTime = System.currentTimeMillis();

        QueryResultIterator<DTO> data = new OfyDataStorage().doIter(
                "test", DTO.class,
                limit,
                scrollId);

        while (data.hasNext()) {
            DTO next = data.next();
            counter++;
        }

        iterTime = System.currentTimeMillis() - iterTime;

        sb.append("{\n");
        sb.append(" \"scrollId\" : \"").append(data.getCursor().toWebSafeString()).append("\",\n");
        sb.append(" \"values\" : ").append(counter).append(",\n");
        sb.append(" \"time\" : ").append(iterTime).append("\n");
        sb.append("}");

        resp.setStatus(200);
        IOUtils.write(sb, resp.getOutputStream());

    }
}
