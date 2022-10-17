package tech.mathieu.collection;

import tech.mathieu.book.BookDto;

import java.util.List;

public record CollectionDto(Long id, String title, List<BookDto> books) {
}
