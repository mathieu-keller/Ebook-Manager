package tech.mathieu.collection;

import java.util.List;
import tech.mathieu.book.BookDto;

public record CollectionDto(Long id, String title, List<BookDto> books) {}
