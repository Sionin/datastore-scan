package com.example.datastore;

import com.example.datastore.model.DTO;
import com.google.common.base.Function;
import com.google.common.collect.*;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DataServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fromParam = req.getParameter("from");
        String toParam = req.getParameter("to");
        int from = fromParam != null ? Integer.valueOf(fromParam) : 1;
        int to = toParam != null ? Math.min(Integer.valueOf(toParam), 25000) : 1000;

//        final String payload = read("payload.txt");

        Iterable<List<Long>> partitions = Iterables.partition(range(from, to), 100);

        OfyDataStorage storage = new OfyDataStorage();
        for (List<Long> part : partitions) {
            storage.doCreateBatch(
                    "test",
                    Lists.transform(part, new Function<Long, DTO>() {
                        @Override
                        public DTO apply(Long id) {
                            return new DTO(id, true);
                        }
                    }));
        }

        resp.setStatus(201);
        IOUtils.write(String.format("Created [%d : %d]", from, to), resp.getOutputStream());
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fromParam = req.getParameter("from");
        String toParam = req.getParameter("to");
        int from = fromParam != null ? Integer.valueOf(fromParam) : 1;
        int to = toParam != null ? Math.min(Integer.valueOf(toParam), 25000) : 1000;


        Iterable<List<Long>> partitions = Iterables.partition(range(from, to), 100);

        OfyDataStorage storage = new OfyDataStorage();
        for (List<Long> partition : partitions) {
            storage.doDeleteBatch("test", DTO.class, partition);
        }

        resp.setStatus(200);
        IOUtils.write(String.format("Deleted [%d : %d]", from, to), resp.getOutputStream());
    }

    private Iterable<Long> range(long from, long to) {
        return ContiguousSet.create(Range.closed(from, to), DiscreteDomain.longs());
    }

    public static String read(final String configurationPath) {
        final InputStream is = DataServlet.class.getClassLoader().getResourceAsStream(configurationPath);
        try {
            return IOUtils.toString(is, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            return "";
        }
    }
}
