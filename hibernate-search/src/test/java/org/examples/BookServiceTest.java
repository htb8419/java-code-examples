package org.examples;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookSearchService bookService;

    @Test
    public void testSearchBooks() {
        // Given
        String keyword = "Spring";
        List<Book> mockBooks = List.of(
                new Book(1L, "Spring in Action", "Craig Walls"),
                new Book(2L, "Spring Boot", "Mark Heckler")
        );

        // When
        List<Book> result = bookService.searchBooks(keyword);

        // Then
        assertThat(result).isNotNull().hasSize(2);
        System.out.println(result);
        //verify(searchSession.search(Book.class), times(1));
    }
}
