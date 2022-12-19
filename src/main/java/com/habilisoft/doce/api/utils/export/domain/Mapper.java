package com.habilisoft.doce.api.utils.export.domain;

public interface Mapper<I, O> {
    O map(I input);
}
