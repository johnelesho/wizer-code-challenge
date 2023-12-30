package tech.elsoft.wizercodechallenge.mapper;

import java.util.List;
import java.util.Set;

public interface EntityMapper<D, R, E> {

    E toEntity(D dto);

    R toResponse(E entity);

    List<E> toEntity(List<D> dtoList);
    Set<E> toEntity(Set<D> dtoList);

    List<R> toResponse(List<E> entityList);


    Set<R> toResponse(Set<E> entityList);
}
