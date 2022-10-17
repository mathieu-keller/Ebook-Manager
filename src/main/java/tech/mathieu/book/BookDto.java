package tech.mathieu.book;

import tech.mathieu.creator.CreatorDto;
import tech.mathieu.language.LanguageDto;
import tech.mathieu.publisher.PublisherDto;
import tech.mathieu.subject.SubjectDto;

import java.util.List;

public record BookDto(
    Long id,
    String title,
    List<String> published,
    List<LanguageDto> language,
    List<SubjectDto> subjects,
    List<PublisherDto> publisher,
    List<CreatorDto> authors,
    Long collectionId,
    Long collectionIndex
) {
}
