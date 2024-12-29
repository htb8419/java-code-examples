package org.examples;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSearchService {
    final EntityManager entityManager;

    public BookSearchService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> searchBooks(String keyword) {
        SearchSession searchSession = Search.session((Session) entityManager);
        return searchSession.search(Book.class)
                .where(f -> f.match()
                        .fields("title", "author") // Search in these fields
                        .matching(keyword))
                .fetchHits(20); // Limit results to 20
    }

    public void rebuildIndex() throws InterruptedException {
        SearchSession searchSession = Search.session((Session) entityManager);
        searchSession.massIndexer().startAndWait();
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }
}
