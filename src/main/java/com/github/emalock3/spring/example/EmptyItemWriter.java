package com.github.emalock3.spring.example;

import java.util.List;
import org.springframework.batch.item.ItemWriter;

class EmptyItemWriter<T> implements ItemWriter<T> {
  @Override
  public void write(List<? extends T> items) throws Exception {
  }
}
