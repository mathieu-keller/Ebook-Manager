package tech.mathieu.book;

import java.util.List;
import tech.mathieu.collection.CollectionDto;
import tech.mathieu.creator.CreatorDto;
import tech.mathieu.language.LanguageDto;
import tech.mathieu.publisher.PublisherDto;
import tech.mathieu.subject.SubjectDto;

public record BookDto(
    Long id,
    String title,
    List<String> published,
    List<LanguageDto> language,
    List<SubjectDto> subjects,
    List<PublisherDto> publisher,
    List<CreatorDto> authors,
    CollectionDto collection,
    Long collectionIndex) {}
