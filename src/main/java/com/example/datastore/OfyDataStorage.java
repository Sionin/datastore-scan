package com.example.datastore;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;
import com.googlecode.objectify.cmd.Query;

import java.util.Collection;
import java.util.Map;

public class OfyDataStorage {

    protected <V> QueryResultIterator<V> doIter(final String namespace, final Class<V> aClass,final int limit, final String cursor) {
        return run(namespace, new Work<QueryResultIterator<V>>() {
            @Override
            public QueryResultIterator<V> run() {
                Query<V> query = ObjectifyService.ofy()
                        .load()
                        .type(aClass);
                if (limit > 0) query = query.limit(limit);
                if (cursor != null && !cursor.isEmpty()) query = query.startAt(Cursor.fromWebSafeString(cursor));
                return query.iterator();
            }
        });
    }

    protected <V> Collection<V> doCreateBatch(final String namespace, final Iterable<V> values) {
        return run(namespace, new Work<Collection<V>>() {
            @Override
            public Collection<V> run() {
                Map<Key<V>, V> keyMap = ObjectifyService.ofy().save().entities(values).now();
                return keyMap.values();
            }
        });
    }

    protected <V> void doDeleteBatch(final String namespace, final Class<V> aClass, final Iterable<?> ids) {
        run(namespace, new Work<Void>() {
            @Override
            public Void run() {
                return ObjectifyService.ofy()
                        .delete()
                        .type(aClass).ids(ids)
                        .now();
            }
        });
    }


    protected <V> V run(final String namespace, Work<V> work) {
        String original = NamespaceManager.get();
        try {
            NamespaceManager.set(namespace);
            return ObjectifyService.run(work);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            NamespaceManager.set(original);
        }
    }
}
